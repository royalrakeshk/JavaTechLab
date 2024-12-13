package com.Spring.jwt.demo.service;

import com.Spring.jwt.demo.model.User;

public interface JwtService {

	public String generateToken(User user);
	
}
