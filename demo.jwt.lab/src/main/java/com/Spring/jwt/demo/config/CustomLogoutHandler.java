package com.Spring.jwt.demo.config;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import com.Spring.jwt.demo.model.Token;
import com.Spring.jwt.demo.repsitory.TokenRepository;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomLogoutHandler implements LogoutHandler{

	private final TokenRepository tokenrepo;
	public CustomLogoutHandler(TokenRepository tokenrepo) {
		super();
		this.tokenrepo = tokenrepo;
	}
	@Override
	public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
		// TODO Auto-generated method stub
		String authHeader=request.getHeader("Authorization");
		if(authHeader ==null || ! authHeader.startsWith("bearer ")) {
			return;
		}
		String token=authHeader.substring(7);
		//get stored token from db
		Token storedToken= tokenrepo.findByToken(token).orElse(null);
		if(token !=null) {
			storedToken.setLoggedout(true);
			tokenrepo.save(storedToken);
			
		}
	}

}
