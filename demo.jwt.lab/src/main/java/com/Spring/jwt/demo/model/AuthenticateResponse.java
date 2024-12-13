package com.Spring.jwt.demo.model;

public class AuthenticateResponse {
	
	private String token;

	public AuthenticateResponse(String token) {
		super();
		this.token = token;
	}

	public String getToken() {
		return token;
	}

}
