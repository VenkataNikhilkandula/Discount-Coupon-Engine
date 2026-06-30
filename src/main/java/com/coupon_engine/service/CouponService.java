package com.coupon_engine.service;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.UserCoupon;
import com.coupon_engine.exception.InvalidCouponException;
import com.coupon_engine.repository.CouponRepository;
import com.coupon_engine.repository.UserCouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponService(CouponRepository couponRepository, UserCouponRepository userCouponRepository) {
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
    }

    @Transactional
    public Coupon createCoupon(Coupon coupon) {
        return couponRepository.save(coupon);
    }

    @Transactional
    public UserCoupon assignCouponToUser(Long userId, String couponCode) {
        Coupon coupon = couponRepository.findByCode(couponCode)
                .orElseThrow(() -> new InvalidCouponException("Coupon not found: " + couponCode));
        
        // Check if already assigned
        return userCouponRepository.findByUserIdAndCouponCode(userId, couponCode)
                .orElseGet(() -> userCouponRepository.save(new UserCoupon(userId, coupon, false)));
    }
}