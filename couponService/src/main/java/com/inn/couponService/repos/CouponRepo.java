package com.inn.couponService.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.couponService.model.Coupon;

public interface CouponRepo extends JpaRepository<Coupon, Long>{

	Coupon findByCode(String code);

}
