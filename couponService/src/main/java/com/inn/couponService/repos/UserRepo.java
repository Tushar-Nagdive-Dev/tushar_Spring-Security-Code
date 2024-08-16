package com.inn.couponService.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.couponService.model.User;

public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
