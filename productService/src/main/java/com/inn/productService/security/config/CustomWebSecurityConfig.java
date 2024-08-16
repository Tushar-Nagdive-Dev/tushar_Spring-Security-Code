package com.inn.productService.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class CustomWebSecurityConfig {

	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/ProductApi/product/**")
				.hasAnyRole("USER", "ADMIN").requestMatchers(HttpMethod.POST, "/ProductApi/product")
				.hasRole("ADMIN"));
		
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
}
