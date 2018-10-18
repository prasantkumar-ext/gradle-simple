/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.service;

import com.singtel.common.model.AccountCredentials;

public interface ISecurityService {

	public AccountCredentials validateToken(String token);

	public String generateToken(AccountCredentials accountCredentials);
}
