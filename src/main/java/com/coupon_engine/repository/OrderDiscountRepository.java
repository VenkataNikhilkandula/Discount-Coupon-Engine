package com.coupon_engine.repository;

import com.coupon_engine.model.OrderDiscount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDiscountRepository extends JpaRepository<OrderDiscount, Long> {
}