package com.coupon_engine.repository;

import com.coupon_engine.model.UserCoupon;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon WHERE uc.userId = :userId AND uc.coupon.code = :code")
    Optional<UserCoupon> findByUserIdAndCouponCode(@Param("userId") Long userId, @Param("code") String code);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon WHERE uc.userId = :userId AND uc.coupon.code = :code")
    Optional<UserCoupon> findByUserIdAndCouponCodeForUpdate(@Param("userId") Long userId, @Param("code") String code);

    @Query("SELECT uc FROM UserCoupon uc JOIN FETCH uc.coupon WHERE uc.userId = :userId")
    List<UserCoupon> findByUserId(@Param("userId") Long userId);
}