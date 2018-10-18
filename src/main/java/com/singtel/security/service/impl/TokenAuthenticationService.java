/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.service.impl;

/**
 * Created by deepak.j
 */
import static java.util.Collections.emptyList;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.singtel.common.model.AccountCredentials;
import com.singtel.security.config.JwtAuthenticationToken;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class TokenAuthenticationService {
	static final String SECRET = "XY7kmzoNzl100";
	static final String TOKEN_PREFIX = "Bearer";
	static final String HEADER_STRING = "Authorization";

	private TokenAuthenticationService() {

	}

	public static Authentication getAuthentication(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			String user = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
					.getBody().getSubject();

			return user != null ? new UsernamePasswordAuthenticationToken(user, null, emptyList()) : null;
		}
		return null;
	}

	public static String getAuthenticationUser(HttpServletRequest request) {
		String token = request.getHeader(HEADER_STRING);
		if (token != null) {
			// parse the token.
			return Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim()).getBody()
					.getSubject();
		}
		return null;
	}

	public static AccountCredentials getAuthenticatedUserFromToken(String token) {
		// parse the token.
		Claims body = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim())
				.getBody();
		AccountCredentials accountCredentials = new AccountCredentials();
		accountCredentials.setScope(body.get("scope").toString());
		accountCredentials.setUsername(body.getSubject());
		return accountCredentials;
	}

	public static void throwAuthError(HttpServletResponse httpServletResponse) throws IOException {
		JwtAuthenticationToken authenticationToken = new JwtAuthenticationToken("");
		authenticationToken.setTokenExpired(true);
		authenticationToken.setHttpCode(HttpStatus.UNAUTHORIZED.value());
		httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		ObjectMapper mapper = new ObjectMapper();
		httpServletResponse.setContentType("application/json");
		httpServletResponse.getWriter().write(mapper.writeValueAsString(authenticationToken));
		httpServletResponse.getWriter().flush();
		httpServletResponse.getWriter().close();
	}

	public static String generateToken(AccountCredentials accountCredentials) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(new Date());
		cal.add(Calendar.MINUTE, 30);
		return Jwts.builder().setSubject(accountCredentials.getUsername()).claim("scope", accountCredentials.getScope())
				.setExpiration(cal.getTime()).signWith(SignatureAlgorithm.HS512, SECRET).compact();
	}

}
