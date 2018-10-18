/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.config;

/**
 * Holder for JWT token from the request.
 * <p/>
 * Other fields aren't used but necessary to comply to the contracts of
 * AbstractUserDetailsAuthenticationProvider
 *
 * @author deepak.j
 */
public class JwtAuthenticationToken {

	private String token;
	private String clientId;
	private boolean tokenExpired = false;
	private int httpCode;

	public JwtAuthenticationToken() {

	}

	public JwtAuthenticationToken(String token) {

		this.token = token;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public boolean isTokenExpired() {
		return tokenExpired;
	}

	public void setTokenExpired(boolean tokenExpired) {
		this.tokenExpired = tokenExpired;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

}
