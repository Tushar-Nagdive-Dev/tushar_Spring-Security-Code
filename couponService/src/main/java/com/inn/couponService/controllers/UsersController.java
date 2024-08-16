package com.inn.couponService.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.inn.couponService.security.CustomSecurityService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UsersController {
	
	@Autowired
	private CustomSecurityService customSecurityService;
	
	@GetMapping("/")
	public String showLoginPage() {
		return "login";
	}
	
	public String login(String email, String password, HttpServletRequest request, HttpServletResponse response) {
		Boolean loginResponse = customSecurityService.login(email, password, request, response);
		if(Boolean.TRUE.equals(loginResponse)) {
			return "index";
		}
		
		return "login";
	}
}
