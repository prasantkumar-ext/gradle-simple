/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.response;

/**
 * Created by deepak.j
 */
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

public class SingtelApiResponse {

	private HttpStatus status;
	private int httpCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp;
	private String message;

	protected SingtelApiResponse() {
		timestamp = LocalDateTime.now();
	}

	SingtelApiResponse(HttpStatus status) {
		this();
		this.status = status;
	}

	SingtelApiResponse(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
	}

	public SingtelApiResponse(HttpStatus status, int httpCode, String message, Throwable ex) {
		this();
		this.httpCode = httpCode;
		this.status = status;
		this.message = message;
	}

	public LocalDateTime getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(LocalDateTime timestamp) {
		this.timestamp = timestamp;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public int getHttpCode() {
		return httpCode;
	}

	public void setHttpCode(int httpCode) {
		this.httpCode = httpCode;
	}

}
