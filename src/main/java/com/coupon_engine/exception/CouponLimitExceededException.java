package com.coupon_engine.exception;

public class CouponLimitExceededException extends CouponEngineException {
    public CouponLimitExceededException(String message) {
        super(message);
    }
}