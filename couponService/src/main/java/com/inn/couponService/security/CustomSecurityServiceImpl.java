package com.inn.couponService.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Service
public class CustomSecurityServiceImpl implements CustomSecurityService {
	
	private static final Logger logger = LoggerFactory.getLogger(CustomSecurityServiceImpl.class);
	
//	@Autowired
	private final UserDetailsService userDetailsService;
	
//	@Autowired
	private final AuthenticationManager authenticationManager;
	
	private final SecurityContextRepository securityContextRepository;
	
	public CustomSecurityServiceImpl(UserDetailsService userDetailsService, AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
		this.userDetailsService = userDetailsService;
		this.authenticationManager = authenticationManager;
		this.securityContextRepository = securityContextRepository;
	}

	@Override
	public Boolean login(String userName, String password, HttpServletRequest request, HttpServletResponse response) {
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(userName);
		UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(userName, password, userDetails.getAuthorities());
		this.authenticationManager.authenticate(token);
		Boolean result = token.isAuthenticated();
		logger.info("login User Detaiks ::: {}, isAuthenticated ::: {}", userDetails, result);
		if(Boolean.TRUE.equals(result)) {
			SecurityContext context = SecurityContextHolder.getContext();
			context.setAuthentication(token);
			this.securityContextRepository.saveContext(context, request, response);
		}
		
		return result;
	}

}
