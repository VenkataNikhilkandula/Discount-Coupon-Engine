package com.coupon_engine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupons")
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Coupon code is required")
    @Column(unique = true, nullable = false)
    private String code;

    @NotNull(message = "Coupon type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CouponType type;

    @NotNull(message = "Discount value is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Discount value must be positive")
    @Column(name = "discount_value", nullable = false)
    private BigDecimal discountValue;

    @DecimalMin(value = "0.0", message = "Minimum order value cannot be negative")
    @Column(name = "min_order_value")
    private BigDecimal minOrderValue;

    @Column(name = "applicable_category")
    private String applicableCategory;

    @NotNull(message = "Expiry date is required")
    @Column(name = "expiry_date", nullable = false)
    private LocalDateTime expiryDate;

    @NotNull(message = "Usage limit is required")
    @Min(value = 1, message = "Usage limit must be at least 1")
    @Column(name = "usage_limit", nullable = false)
    private Integer usageLimit;

    @NotNull
    @Min(value = 0, message = "Usage count cannot be negative")
    @Column(name = "usage_count", nullable = false)
    private Integer usageCount = 0;

    @Column(nullable = false)
    private Boolean combinable = true;

    // Constructors
    public Coupon() {}

    public Coupon(String code, CouponType type, BigDecimal discountValue, BigDecimal minOrderValue, 
                  String applicableCategory, LocalDateTime expiryDate, Integer usageLimit, Boolean combinable) {
        this.code = code;
        this.type = type;
        this.discountValue = discountValue;
        this.minOrderValue = minOrderValue;
        this.applicableCategory = applicableCategory;
        this.expiryDate = expiryDate;
        this.usageLimit = usageLimit;
        this.usageCount = 0;
        this.combinable = combinable != null ? combinable : true;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public CouponType getType() {
        return type;
    }

    public void setType(CouponType type) {
        this.type = type;
    }

    public BigDecimal getDiscountValue() {
        return discountValue;
    }

    public void setDiscountValue(BigDecimal discountValue) {
        this.discountValue = discountValue;
    }

    public BigDecimal getMinOrderValue() {
        return minOrderValue;
    }

    public void setMinOrderValue(BigDecimal minOrderValue) {
        this.minOrderValue = minOrderValue;
    }

    public String getApplicableCategory() {
        return applicableCategory;
    }

    public void setApplicableCategory(String applicableCategory) {
        this.applicableCategory = applicableCategory;
    }

    public LocalDateTime getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(LocalDateTime expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getUsageLimit() {
        return usageLimit;
    }

    public void setUsageLimit(Integer usageLimit) {
        this.usageLimit = usageLimit;
    }

    public Integer getUsageCount() {
        return usageCount;
    }

    public void setUsageCount(Integer usageCount) {
        this.usageCount = usageCount;
    }

    public Boolean getCombinable() {
        return combinable;
    }

    public void setCombinable(Boolean combinable) {
        this.combinable = combinable;
    }
}