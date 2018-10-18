/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.exception;

/**
 * Created by deepak.j
 */
import java.io.PrintWriter;
import java.io.StringWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.singtel.security.response.SingtelApiResponse;

@RestControllerAdvice
public class WebRestControllerAdvice {

	private static final Logger LOGGER = LoggerFactory.getLogger(WebRestControllerAdvice.class);

	@ExceptionHandler(Exception.class)
	public SingtelApiResponse handleNotFoundException(Exception ex) {
		StringWriter sw = new StringWriter();
		ex.printStackTrace(new PrintWriter(sw));
		String exceptionAsString = sw.toString();
		LOGGER.error(exceptionAsString);
		return new SingtelApiResponse(HttpStatus.INTERNAL_SERVER_ERROR, HttpStatus.INTERNAL_SERVER_ERROR.value(),
				ex.getMessage(), ex);
	}

}
