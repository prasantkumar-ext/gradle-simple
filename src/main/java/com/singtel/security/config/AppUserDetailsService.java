/*******************************************************************************
 * /*******************************************************************************
 *  * Copyright (C) 2018 Singtel- All Rights Reserved
 * 
 *  * Unauthorized copying of this file, via any medium is strictly prohibited
 *  * Proprietary and confidential
 *  ******************************************************************************/
package com.singtel.security.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.singtel.common.model.User;
import com.singtel.security.repository.UserRepository;

/**
 * Created by deepak.j
 */
@Component
public class AppUserDetailsService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String s) {
		User user = userRepository.findByUsername(s.toLowerCase());
		if (user == null) {
			throw new UsernameNotFoundException(String.format("The username %s doesn't exist", s));
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		for (String userRole : user.getRoles()) {
			authorities.add(new SimpleGrantedAuthority(userRole));
		}
		return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(),
				authorities);
	}
}
