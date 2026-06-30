package com.coupon_engine.service;

import com.coupon_engine.dto.AppliedDiscountDto;
import com.coupon_engine.dto.CheckoutRequest;
import com.coupon_engine.dto.OrderCheckoutResponse;
import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import com.coupon_engine.model.Order;
import com.coupon_engine.model.OrderDiscount;
import com.coupon_engine.model.UserCoupon;
import com.coupon_engine.exception.CouponCombinationException;
import com.coupon_engine.exception.InvalidCouponException;
import com.coupon_engine.repository.CouponRepository;
import com.coupon_engine.repository.OrderDiscountRepository;
import com.coupon_engine.repository.OrderRepository;
import com.coupon_engine.repository.UserCouponRepository;
import com.coupon_engine.strategy.DiscountStrategy;
import com.coupon_engine.validator.CouponValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class CheckoutService {

    private static final Logger log = LoggerFactory.getLogger(CheckoutService.class);

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;
    private final OrderRepository orderRepository;
    private final OrderDiscountRepository orderDiscountRepository;
    private final CouponValidator couponValidator;
    private final Map<CouponType, DiscountStrategy> strategies;

    public CheckoutService(CouponRepository couponRepository,
                           UserCouponRepository userCouponRepository,
                           OrderRepository orderRepository,
                           OrderDiscountRepository orderDiscountRepository,
                           CouponValidator couponValidator,
                           List<DiscountStrategy> strategyList) {
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
        this.orderRepository = orderRepository;
        this.orderDiscountRepository = orderDiscountRepository;
        this.couponValidator = couponValidator;
        this.strategies = strategyList.stream()
                .collect(Collectors.toMap(DiscountStrategy::getSupportedType, s -> s));
    }

    @Transactional
    public OrderCheckoutResponse checkout(CheckoutRequest request) {
        log.info("Processing checkout for user ID: {} with coupons: {}", request.getUserId(), request.getCouponCodes());

        List<String> codes = request.getCouponCodes();
        if (codes == null || codes.isEmpty()) {
            // No coupons to apply
            Order order = new Order(request.getUserId(), request.getOriginalAmount());
            order = orderRepository.save(order);
            return new OrderCheckoutResponse(
                    order.getId(),
                    request.getOriginalAmount(),
                    BigDecimal.ZERO,
                    request.getOriginalAmount(),
                    Collections.emptyList()
            );
        }

        // Check for duplicate coupons in the same request
        Set<String> uniqueCodes = new HashSet<>(codes);
        if (uniqueCodes.size() < codes.size()) {
            throw new CouponCombinationException("Cannot apply the same coupon multiple times in the same order.");
        }

        // Lock and fetch Coupon and UserCoupon records to prevent race conditions
        List<Coupon> lockedCoupons = new ArrayList<>();
        List<UserCoupon> lockedUserCoupons = new ArrayList<>();

        for (String code : codes) {
            // Pessimistic Write Lock on Coupon
            Coupon coupon = couponRepository.findByCodeForUpdate(code)
                    .orElseThrow(() -> new InvalidCouponException("Coupon not found: " + code));

            // Pessimistic Write Lock on UserCoupon (if exists)
            UserCoupon userCoupon = userCouponRepository.findByUserIdAndCouponCodeForUpdate(request.getUserId(), code)
                    .orElse(null);

            lockedCoupons.add(coupon);
            lockedUserCoupons.add(userCoupon);
        }

        // Validate coupon combination compatibility
        if (lockedCoupons.size() > 1) {
            for (Coupon c : lockedCoupons) {
                if (Boolean.FALSE.equals(c.getCombinable())) {
                    throw new CouponCombinationException("Coupon '" + c.getCode() + "' is not combinable with other coupons.");
                }
            }
        }

        BigDecimal currentAmount = request.getOriginalAmount();
        BigDecimal totalDiscount = BigDecimal.ZERO;
        List<AppliedDiscountDto> appliedDiscounts = new ArrayList<>();

        // Sequentially validate and apply discounts
        for (int i = 0; i < lockedCoupons.size(); i++) {
            Coupon coupon = lockedCoupons.get(i);
            UserCoupon userCoupon = lockedUserCoupons.get(i);

            // Validate eligibility
            couponValidator.validate(coupon, userCoupon, currentAmount, request.getCategory());

            // Apply Strategy
            DiscountStrategy strategy = strategies.get(coupon.getType());
            if (strategy == null) {
                throw new IllegalStateException("No strategy implementation found for coupon type: " + coupon.getType());
            }

            BigDecimal discount = strategy.calculateDiscount(currentAmount, coupon, request.getCategory());
            if (discount.compareTo(BigDecimal.ZERO) > 0) {
                currentAmount = currentAmount.subtract(discount);
                totalDiscount = totalDiscount.add(discount);
                appliedDiscounts.add(new AppliedDiscountDto(coupon.getCode(), discount));
            }
        }

        // Save Order
        Order order = new Order(request.getUserId(), currentAmount);
        order = orderRepository.save(order);

        // Save OrderDiscounts and Update Usage Stats
        for (int i = 0; i < lockedCoupons.size(); i++) {
            Coupon coupon = lockedCoupons.get(i);
            UserCoupon userCoupon = lockedUserCoupons.get(i);

            final String code = coupon.getCode();
            BigDecimal discountAmount = appliedDiscounts.stream()
                    .filter(ad -> ad.getCouponCode().equals(code))
                    .map(AppliedDiscountDto::getDiscountAmount)
                    .findFirst()
                    .orElse(BigDecimal.ZERO);

            if (discountAmount.compareTo(BigDecimal.ZERO) > 0) {
                OrderDiscount orderDiscount = new OrderDiscount(order, coupon, discountAmount);
                orderDiscountRepository.save(orderDiscount);
            }

            // Increment Coupon Usage
            coupon.setUsageCount(coupon.getUsageCount() + 1);
            couponRepository.save(coupon);

            // Record or Update User Coupon consumption
            if (userCoupon != null) {
                userCoupon.setUsedFlag(true);
                userCouponRepository.save(userCoupon);
            } else {
                UserCoupon newUserCoupon = new UserCoupon(request.getUserId(), coupon, true);
                userCouponRepository.save(newUserCoupon);
            }
        }

        log.info("Order ID {} completed. Original: {}, Discount: {}, Final: {}", 
                order.getId(), request.getOriginalAmount(), totalDiscount, currentAmount);

        return new OrderCheckoutResponse(
                order.getId(),
                request.getOriginalAmount(),
                totalDiscount,
                currentAmount,
                appliedDiscounts
        );
    }
}