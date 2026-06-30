package com.coupon_engine.controller;

import com.coupon_engine.dto.CheckoutRequest;
import com.coupon_engine.dto.OrderCheckoutResponse;
import com.coupon_engine.service.CheckoutService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutService checkoutService;

    public CheckoutController(CheckoutService checkoutService) {
        this.checkoutService = checkoutService;
    }

    @PostMapping
    public ResponseEntity<OrderCheckoutResponse> performCheckout(@Valid @RequestBody CheckoutRequest request) {
        OrderCheckoutResponse response = checkoutService.checkout(request);
        return ResponseEntity.ok(response);
    }
}