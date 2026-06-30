package com.coupon_engine.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

public class CheckoutRequest {

    @NotNull(message = "User ID is required")
    private Long userId;

    private List<String> couponCodes;

    @NotNull(message = "Original order amount is required")
    @DecimalMin(value = "0.00", inclusive = false, message = "Original order amount must be greater than zero")
    private BigDecimal originalAmount;

    private String category;

    // Constructors
    public CheckoutRequest() {}

    public CheckoutRequest(Long userId, List<String> couponCodes, BigDecimal originalAmount, String category) {
        this.userId = userId;
        this.couponCodes = couponCodes;
        this.originalAmount = originalAmount;
        this.category = category;
    }

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<String> getCouponCodes() {
        return couponCodes;
    }

    public void setCouponCodes(List<String> couponCodes) {
        this.couponCodes = couponCodes;
    }

    public BigDecimal getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(BigDecimal originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}