package com.coupon_engine.dto;

import java.math.BigDecimal;

public class AppliedDiscountDto {
    
    private String couponCode;
    private BigDecimal discountAmount;

    public AppliedDiscountDto() {}

    public AppliedDiscountDto(String couponCode, BigDecimal discountAmount) {
        this.couponCode = couponCode;
        this.discountAmount = discountAmount;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public void setDiscountAmount(BigDecimal discountAmount) {
        this.discountAmount = discountAmount;
    }
}