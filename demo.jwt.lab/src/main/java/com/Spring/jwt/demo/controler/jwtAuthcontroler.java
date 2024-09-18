package com.Spring.jwt.demo.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.Spring.jwt.demo.model.AuthenticateResponse;
import com.Spring.jwt.demo.model.User;
import com.Spring.jwt.demo.service.imp.AuthenticationService;

@RestController
public class jwtAuthcontroler {
	
	private AuthenticationService authsvc;

	public jwtAuthcontroler(AuthenticationService authsvc) {
		super();
		this.authsvc = authsvc;
	}
	@PostMapping("/register" )
	public ResponseEntity<AuthenticateResponse> register(@RequestBody User request){
		
		return ResponseEntity.ok(authsvc.register(request));
	}
	@PostMapping("/login")
	public ResponseEntity<AuthenticateResponse> autheUser(@RequestBody User request){
		return ResponseEntity.ok(authsvc.authenticate(request));
		
		
	}

}
