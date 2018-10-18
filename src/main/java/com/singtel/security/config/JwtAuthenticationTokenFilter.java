/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.config;

/**
 * Filter that orchestrates authentication by using supplied JWT token
 *
 * @author deepak.j
 */
import java.io.IOException;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.filter.GenericFilterBean;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singtel.security.service.impl.TokenAuthenticationService;

import io.jsonwebtoken.Jwts;

@Service
public class JwtAuthenticationTokenFilter extends GenericFilterBean {
	public static final String SECRET = "XY7kmzoNzl100";
	public static final long EXPIRATION_TIME = 864_000_000; // 10 days
	public static final String TOKEN_PREFIX = "Bearer ";
	public static final String HEADER_STRING = "Authorization";

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain)
			throws IOException, ServletException {
		String token = ((HttpServletRequest) request).getHeader(HEADER_STRING);
		if (token != null) {
			Date date = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
					.getBody().getExpiration();
			if (date.compareTo(new Date()) < 0) {
				HttpServletResponse res = (HttpServletResponse) response;
				JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken("");
				authenticationToken.setTokenExpired(true);
				authenticationToken.setHttpCode(HttpStatus.UNAUTHORIZED.value());
				ObjectMapper mapper = new ObjectMapper();
				res.setContentType("application/json");
				res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				response.getWriter().write(mapper.writeValueAsString(authenticationToken));
				response.getWriter().flush();
				response.getWriter().close();
			} else {
				Authentication authentication = TokenAuthenticationService
						.getAuthentication((HttpServletRequest) request);
				SecurityContextHolder.getContext().setAuthentication(authentication);
				filterChain.doFilter(request, response);
			}
		} else {
			Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		}
	}
}
