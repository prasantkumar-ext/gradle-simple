package com.singtel.security.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.singtel.common.model.AccountCredentials;
import com.singtel.security.service.ISecurityService;

@CrossOrigin
@RestController
@RequestMapping("/ai/security/")
@RestControllerAdvice
public class SecuritySingtelController {

	@Autowired
	ISecurityService iSecurityService;

	@GetMapping(value = "validate")
	public AccountCredentials validateToken(@RequestParam("token") final String token) {
		return iSecurityService.validateToken(token);
	}

	@PostMapping(value = "token")
	public String generateToken(@RequestBody final AccountCredentials accountCredentials) {
		return iSecurityService.generateToken(accountCredentials);
	}
}
