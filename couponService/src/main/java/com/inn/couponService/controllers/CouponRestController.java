package com.inn.couponService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inn.couponService.model.Coupon;
import com.inn.couponService.repos.CouponRepo;


@RestController
@RequestMapping("/CouponApi")
public class CouponRestController {
	
	private CouponRepo couponRepo;
	
	@Autowired
	public void setCouponRepo(CouponRepo couponRepo) {
		this.couponRepo = couponRepo;
	}

//	@RequestMapping(value = "/coupons", method = RequestMethod.POST)
	@PostMapping("/coupons")
	public Coupon createCoupon(@RequestBody Coupon coupon) {
		return couponRepo.save(coupon);
	}
	
	@GetMapping("/coupons/{code}")
	public Coupon getCouponByCode(@PathVariable String code) {
		return couponRepo.findByCode(code);
	}
}
