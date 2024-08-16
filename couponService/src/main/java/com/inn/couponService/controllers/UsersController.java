package com.inn.couponService.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.inn.couponService.model.User;
import com.inn.couponService.repos.UserRepo;
import com.inn.couponService.security.CustomSecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
	
	private static final Logger logger = LoggerFactory.getLogger(UsersController.class);
	
	private static final String LOGIN = "login";
	
	
	private CustomSecurityService customSecurityService;
	
	private UserRepo userRepo;
	
	private PasswordEncoder encoder;
	
	public UsersController(CustomSecurityService customSecurityService, UserRepo userRepo, PasswordEncoder encoder) {
		this.customSecurityService = customSecurityService;
		this.userRepo = userRepo;
		this.encoder = encoder;
	}
	
	@GetMapping("/showReg")
	public String showRegistrationPage() {
		return "registerUser";
	}
	
	@PostMapping("/registerUser")
	public String register(User user) {
		user.setPassword(this.encoder.encode(user.getPassword()));
		this.userRepo.save(user);
		return LOGIN;
	}
	
	@GetMapping("/")
	public String showLoginPage() {
		return LOGIN;
	}
	
	@PostMapping("/login")
	public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
		Boolean loginResponse = customSecurityService.login(email, password, request, response);
		logger.info("login Details ::: {}", loginResponse);
		if(Boolean.TRUE.equals(loginResponse)) {
			return "index";
		}
		
		return LOGIN;
	}
}
