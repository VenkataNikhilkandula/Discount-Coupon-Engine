package com.coupon_engine.strategy;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class FlatDiscountStrategy implements DiscountStrategy {

    @Override
    public CouponType getSupportedType() {
        return CouponType.FLAT;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal currentOrderAmount, Coupon coupon, String category) {
        BigDecimal discount = coupon.getDiscountValue();
        // The discount cannot exceed the remaining order amount
        if (discount.compareTo(currentOrderAmount) > 0) {
            return currentOrderAmount;
        }
        return discount;
    }
}