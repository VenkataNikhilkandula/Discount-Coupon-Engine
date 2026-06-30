package com.coupon_engine.exception;

public class CouponAlreadyUsedException extends CouponEngineException {
    public CouponAlreadyUsedException(String message) {
        super(message);
    }
}