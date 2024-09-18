package com.Spring.jwt.demo.service.imp;

import java.util.Optional;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Spring.jwt.demo.model.AuthenticateResponse;
import com.Spring.jwt.demo.model.User;
import com.Spring.jwt.demo.repsitory.UserRepository;
import com.Spring.jwt.demo.service.JwtService;

@Service
public class AuthenticationService {
	
	private JwtService jwtsvc;
	
	private PasswordEncoder passwordEncoder;
	
	private  UserRepository userRepo;

	private AuthenticationManager authmanager;

	
	public AuthenticationService(JwtService jwtsvc, PasswordEncoder passwordEncoder, UserRepository userRepo,
			AuthenticationManager authmanager) {
		super();
		this.jwtsvc = jwtsvc;
		this.passwordEncoder = passwordEncoder;
		this.userRepo = userRepo;
		this.authmanager = authmanager;
	}


	public AuthenticateResponse register(User request) {
		
		User user= new User();
		user.setFirst_name(request.getFirst_name());
		user.setLast_name(request.getLast_name());
		user.setUsername(request.getUsername());
		user.setPassword(passwordEncoder.encode(request.getPassword()));
		user.setRole(request.getRole());
		user=userRepo.save(user);
		
		String token=jwtsvc.generateToken(user);
		return new AuthenticateResponse(token);
		
	}
	public AuthenticateResponse authenticate(User request) {
		authmanager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		User user=userRepo.findByUsername(request.getUsername()).orElseThrow();
		String token =jwtsvc.generateToken(user);
		
		return new AuthenticateResponse(token);
	}

}
