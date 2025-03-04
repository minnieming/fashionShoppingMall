package com.fashionmall.coupon.repository;

import com.fashionmall.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long>, CouponRepositoryCustom {

    Optional<Coupon> findByName(String name);
}
