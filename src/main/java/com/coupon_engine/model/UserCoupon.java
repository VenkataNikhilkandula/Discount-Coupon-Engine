package com.coupon_engine.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "user_coupons", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"user_id", "coupon_id"})
})
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "User ID is required")
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @NotNull(message = "Coupon is required")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @NotNull
    @Column(name = "used_flag", nullable = false)
    private Boolean usedFlag = false;

    // Constructors
    public UserCoupon() {}

    public UserCoupon(Long userId, Coupon coupon, Boolean usedFlag) {
        this.userId = userId;
        this.coupon = coupon;
        this.usedFlag = usedFlag != null ? usedFlag : false;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Coupon getCoupon() {
        return coupon;
    }

    public void setCoupon(Coupon coupon) {
        this.coupon = coupon;
    }

    public Boolean getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Boolean usedFlag) {
        this.usedFlag = usedFlag;
    }
}