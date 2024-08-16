package com.inn.couponService.security;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface CustomSecurityService {
	
	Boolean login(String userName, String password, HttpServletRequest request, HttpServletResponse response);
}
