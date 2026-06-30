package com.coupon_engine.dto;

import java.math.BigDecimal;
import java.util.List;

public class OrderCheckoutResponse {
    
    private Long orderId;
    private BigDecimal originalAmount;
    private BigDecimal totalDiscount;
    private BigDecimal finalAmount;
    private List<AppliedDiscountDto> appliedDiscounts;

    public OrderCheckoutResponse() {}

    public OrderCheckoutResponse(Long orderId, BigDecimal originalAmount, BigDecimal totalDiscount, 
                                 BigDecimal finalAmount, List<AppliedDiscountDto> appliedDiscounts) {
        this.orderId = orderId;
        this.originalAmount = originalAmount;
        this.totalDiscount = totalDiscount;
        this.finalAmount = finalAmount;
        this.appliedDiscounts = appliedDiscounts;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public BigDecimal getTotalDiscount() {
        return totalDiscount;
    }

    public void setTotalDiscount(BigDecimal totalDiscount) {
        this.totalDiscount = totalDiscount;
    }

    public BigDecimal getFinalAmount() {
        return finalAmount;
    }

    public void setFinalAmount(BigDecimal finalAmount) {
        this.finalAmount = finalAmount;
    }

    public List<AppliedDiscountDto> getAppliedDiscounts() {
        return appliedDiscounts;
    }

    public void setAppliedDiscounts(List<AppliedDiscountDto> appliedDiscounts) {
        this.appliedDiscounts = appliedDiscounts;
    }
}