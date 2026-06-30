package com.coupon_engine.strategy;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Component
public class PercentageDiscountStrategy implements DiscountStrategy {

    @Override
    public CouponType getSupportedType() {
        return CouponType.PERCENTAGE;
    }

    @Override
    public BigDecimal calculateDiscount(BigDecimal currentOrderAmount, Coupon coupon, String category) {
        BigDecimal discount = currentOrderAmount
                .multiply(coupon.getDiscountValue())
                .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
        
        if (discount.compareTo(currentOrderAmount) > 0) {
            return currentOrderAmount;
        }
        return discount;
    }
}