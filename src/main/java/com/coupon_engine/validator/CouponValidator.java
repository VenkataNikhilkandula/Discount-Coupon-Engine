package com.coupon_engine.validator;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.UserCoupon;
import com.coupon_engine.exception.*;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Component
public class CouponValidator {

    public void validate(Coupon coupon, UserCoupon userCoupon, BigDecimal orderAmount, String category) {
        if (coupon == null) {
            throw new InvalidCouponException("Coupon not found.");
        }

        // 1. Expiration check
        if (coupon.getExpiryDate() != null && coupon.getExpiryDate().isBefore(LocalDateTime.now())) {
            throw new CouponExpiredException("Coupon '" + coupon.getCode() + "' has expired.");
        }

        // 2. Global usage limit check
        if (coupon.getUsageLimit() != null && coupon.getUsageCount() >= coupon.getUsageLimit()) {
            throw new CouponLimitExceededException("Coupon '" + coupon.getCode() + "' has reached its usage limit.");
        }

        // 3. User eligibility & reuse check (if userCoupon is specified)
        if (userCoupon != null && Boolean.TRUE.equals(userCoupon.getUsedFlag())) {
            throw new CouponAlreadyUsedException("Coupon '" + coupon.getCode() + "' has already been used by user ID: " + userCoupon.getUserId());
        }

        // 4. Minimum order value check
        if (coupon.getMinOrderValue() != null && orderAmount.compareTo(coupon.getMinOrderValue()) < 0) {
            throw new MinimumOrderValueException("Order value of " + orderAmount + 
                " is less than the minimum required value of " + coupon.getMinOrderValue() + 
                " for coupon '" + coupon.getCode() + "'.");
        }

        // 5. Category applicability check
        if (coupon.getApplicableCategory() != null) {
            if (category == null || !coupon.getApplicableCategory().equalsIgnoreCase(category.trim())) {
                throw new CategoryNotApplicableException("Coupon '" + coupon.getCode() + 
                    "' is only applicable for category '" + coupon.getApplicableCategory() + 
                    "', but order category is '" + (category != null ? category : "N/A") + "'.");
            }
        }
    }
}