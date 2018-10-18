/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.config;

/**
 * Created by deepak.j
 */
import java.io.IOException;
import java.util.Collections;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import com.singtel.common.model.AccountCredentials;
import com.singtel.common.model.User;
import com.singtel.security.repository.UserRepository;
import com.singtel.security.service.impl.TokenAuthenticationService;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

	private UserRepository repository;

	private static final Logger LOGGER = LoggerFactory.getLogger(JWTLoginFilter.class);

	static final String SECRET = "XY7kmzoNzl100";
	static final String TOKEN_PREFIX = "Bearer";

	public JWTLoginFilter(String url, AuthenticationManager authManager, UserRepository repository) {
		super(new AntPathRequestMatcher(url));
		setAuthenticationManager(authManager);
		this.repository = repository;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest req, HttpServletResponse res)
			throws IOException, ServletException {
		AccountCredentials creds = new ObjectMapper().readValue(req.getInputStream(), AccountCredentials.class);
		return getAuthenticationManager().authenticate(new UsernamePasswordAuthenticationToken(creds.getUsername(),
				creds.getPassword(), Collections.emptyList()));
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest req, HttpServletResponse res, FilterChain chain,
			Authentication auth) throws IOException, ServletException {
		User user = repository.findByUsername(auth.getName());
		if (user != null) {
			addAuthentication(res, auth.getName());
			LOGGER.info("User with username logged in {}", auth.getName());
		} else {
			TokenAuthenticationService.throwAuthError(res);
		}
	}

	@Override
	protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException failed) throws IOException, ServletException {
		TokenAuthenticationService.throwAuthError(response);
	}

	private void addAuthentication(HttpServletResponse res, String username) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		AccountCredentials accountCredentials = new AccountCredentials();
		accountCredentials.setUsername(username);
		accountCredentials.setScope("LOGIN");
		String jwt = TokenAuthenticationService.generateToken(accountCredentials);
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken("");
		authenticationToken.setToken(TOKEN_PREFIX + " " + jwt);
		authenticationToken.setTokenExpired(false);
		authenticationToken.setHttpCode(HttpStatus.OK.value());
		res.setStatus(HttpServletResponse.SC_OK);
		res.setContentType("application/json");
		res.getWriter().write(mapper.writeValueAsString(authenticationToken));
		res.getWriter().flush();
		res.getWriter().close();
	}
}
