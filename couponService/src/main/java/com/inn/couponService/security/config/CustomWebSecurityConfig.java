package com.inn.couponService.security.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.DelegatingSecurityContextRepository;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.security.web.context.RequestAttributeSecurityContextRepository;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.RegexRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.servlet.handler.HandlerMappingIntrospector;

@Configuration
public class CustomWebSecurityConfig {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomWebSecurityConfig.class);
	
	public static final String ADMIN = "ADMIN";
	public static final String USER = "USER";
	
	@Bean
	BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Bean
	AuthenticationManager authManager() {
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
		provider.setUserDetailsService(userDetailsService);
		provider.setPasswordEncoder(passwordEncoder());
		return new ProviderManager(provider);
	}
	
	@Bean
	SecurityContextRepository securityContextRespository() {
		return new DelegatingSecurityContextRepository(new RequestAttributeSecurityContextRepository(), new HttpSessionSecurityContextRepository());
	}
	
	/**
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		http.httpBasic(Customizer.withDefaults());
		
		http.authorizeHttpRequests(authorize -> authorize.requestMatchers(HttpMethod.GET, "/CouponApi/coupons/**", "/").hasAnyRole("USER", "ADMIN")
				.requestMatchers(HttpMethod.POST, "/CouponApi/coupons", "/saveCoupon").hasRole("ADMIN")
				.requestMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "createResponse").hasAnyRole("ADMIN"));
		
		http.csrf(csrf -> csrf.disable());
		
		return http.build();
	}
	*/
	
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception{
		http.authorizeHttpRequests().requestMatchers(HttpMethod.GET, "/CouponApi/coupons/{code:^[A-Z]*$}", "/login","/showGetCoupon", "/getCoupon").hasAnyRole(USER, ADMIN)
		.requestMatchers(HttpMethod.GET, "/showCreateCoupon", "/createCoupon", "/createResponse").hasAnyRole(ADMIN)
		.requestMatchers(HttpMethod.POST, "/CouponApi/coupons", "/saveCoupon").hasRole(ADMIN)
		.requestMatchers(HttpMethod.POST, "/getCoupon").hasAnyRole(USER, ADMIN)
		.requestMatchers("/","/login", "/showReg", "/registerUser").permitAll().anyRequest().denyAll()
		.and().logout().logoutSuccessUrl("/");
		
		http.csrf(csrfCustomizer -> {
			  csrfCustomizer.ignoringRequestMatchers("/CouponApi/coupons/**");
			  RequestMatcher regexMatcher = new RegexRequestMatcher("/CouponApi/coupons/[A-Z]+", "POST");
			  RequestMatcher mvcMatcher = new MvcRequestMatcher(new HandlerMappingIntrospector(), "/getCoupon");
			  csrfCustomizer.ignoringRequestMatchers(regexMatcher, mvcMatcher);
			});
		
		http.securityContext(securityContext -> securityContext.requireExplicitSave(true));
		return http.build();
	}
}
