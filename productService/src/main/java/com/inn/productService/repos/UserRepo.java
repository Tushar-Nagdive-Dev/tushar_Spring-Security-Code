package com.inn.productService.repos;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inn.productService.model.User;


public interface UserRepo extends JpaRepository<User, Long> {
	User findByEmail(String email);
}
