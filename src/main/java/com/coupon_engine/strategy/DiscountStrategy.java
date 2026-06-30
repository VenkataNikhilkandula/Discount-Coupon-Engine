package com.coupon_engine.strategy;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import java.math.BigDecimal;

public interface DiscountStrategy {
    
    CouponType getSupportedType();
    
    BigDecimal calculateDiscount(BigDecimal currentOrderAmount, Coupon coupon, String category);
}