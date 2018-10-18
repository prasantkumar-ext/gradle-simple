package com.singtel.security.service.impl;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.singtel.common.model.AccountCredentials;
import com.singtel.common.model.User;
import com.singtel.security.repository.UserRepository;
import com.singtel.security.service.ISecurityService;
import io.jsonwebtoken.Jwts;

@Service
public class SecurityService implements ISecurityService {

	@Autowired
	UserRepository userRepository;

	private static final Logger LOGGER = LoggerFactory.getLogger(SecurityService.class);

	private static final String SECRET = "XY7kmzoNzl100";
	private static final String TOKEN_PREFIX = "Bearer ";

	@Override
	public AccountCredentials validateToken(String token) {
		AccountCredentials accountCredentials = new AccountCredentials();
		LOGGER.info("Validating token {}", token);
		Date date = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX, "").trim()).getBody()
				.getExpiration();
		if (date.compareTo(new Date()) > 0) {
			accountCredentials = TokenAuthenticationService.getAuthenticatedUserFromToken(token);
			String userName = accountCredentials.getUsername();
			User user = userRepository.findByUsername(userName);
			if (user == null) {
			    LOGGER.info("Invalid user found while parsing token {}", token);
				accountCredentials = new AccountCredentials();
			} 
		} else {
			LOGGER.info("Invalid token {}", token);
		}
		return accountCredentials;
	}

	@Override
	public String generateToken(AccountCredentials accountCredentials) {
		return TokenAuthenticationService.generateToken(accountCredentials);
	}

}
