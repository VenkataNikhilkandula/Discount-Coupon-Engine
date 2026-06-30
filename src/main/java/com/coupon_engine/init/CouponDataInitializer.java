package com.coupon_engine.init;

import com.coupon_engine.model.Coupon;
import com.coupon_engine.model.CouponType;
import com.coupon_engine.repository.CouponRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Component
public class CouponDataInitializer implements CommandLineRunner {

    private static final Logger log = LoggerFactory.getLogger(CouponDataInitializer.class);
    private final CouponRepository couponRepository;

    public CouponDataInitializer(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public void run(String... args) {
        if (couponRepository.count() > 0) {
            log.info("Database already contains coupon data. Skipping initialization.");
            return;
        }

        log.info("Initializing sample coupon database records...");

        Coupon welcome10 = new Coupon(
                "WELCOME10", 
                CouponType.FLAT, 
                BigDecimal.valueOf(10.00), 
                BigDecimal.ZERO, 
                null, 
                LocalDateTime.now().plusMonths(3), 
                500, 
                true
        );

        Coupon summer20 = new Coupon(
                "SUMMER20", 
                CouponType.PERCENTAGE, 
                BigDecimal.valueOf(20.00), 
                BigDecimal.valueOf(50.00), 
                null, 
                LocalDateTime.now().plusMonths(2), 
                1000, 
                true
        );

        Coupon elect15 = new Coupon(
                "ELECT15", 
                CouponType.CONDITIONAL, 
                BigDecimal.valueOf(15.00), 
                BigDecimal.valueOf(100.00), 
                "Electronics", 
                LocalDateTime.now().plusMonths(1), 
                200, 
                true
        );

        Coupon expired5 = new Coupon(
                "EXPIRED5", 
                CouponType.FLAT, 
                BigDecimal.valueOf(5.00), 
                BigDecimal.ZERO, 
                null, 
                LocalDateTime.now().minusDays(5), // Expired
                10, 
                true
        );

        Coupon singleUse = new Coupon(
                "SINGLEUSE", 
                CouponType.FLAT, 
                BigDecimal.valueOf(50.00), 
                BigDecimal.valueOf(100.00), 
                null, 
                LocalDateTime.now().plusMonths(6), 
                1, // Only 1 globally
                true
        );

        couponRepository.saveAll(Arrays.asList(welcome10, summer20, elect15, expired5, singleUse));
        log.info("Sample coupons successfully loaded into database.");
    }
}