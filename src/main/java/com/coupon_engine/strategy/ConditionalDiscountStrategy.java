package com.coupon_engine.strategy;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class ConditionalDiscountStrategy implements DiscountStrategy {

    @Override
    public CouponType getSupportedType() {
        return CouponType.CONDITIONAL;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal currentOrderAmount, Coupon coupon, String category) {
        // If order amount is less than min order value, conditional discount does not apply (discount = 0)
        if (coupon.getMinOrderValue() != null && currentOrderAmount.compareTo(coupon.getMinOrderValue()) < 0) {
            return BigDecimal.ZERO;
        }

        // If category is specified and does not match the input category, conditional discount does not apply
        if (coupon.getApplicableCategory() != null && 
            (category == null || !coupon.getApplicableCategory().equalsIgnoreCase(category.trim()))) {
            return BigDecimal.ZERO;
        }

        BigDecimal discount = coupon.getDiscountValue();
        if (discount.compareTo(currentOrderAmount) > 0) {
            return currentOrderAmount;
        }
        return discount;
    }
}