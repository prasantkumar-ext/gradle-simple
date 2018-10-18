/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.repository;

/**
 * Created by deepak.j
 */
import org.springframework.context.annotation.Primary;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.singtel.common.model.User;

@Repository
@Primary
public interface UserRepository extends MongoRepository<User, String> {

	User findByUsername(String lowerCase);
}
