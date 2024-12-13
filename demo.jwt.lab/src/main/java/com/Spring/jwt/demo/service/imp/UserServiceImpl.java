package com.Spring.jwt.demo.service.imp;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Spring.jwt.demo.repsitory.UserRepository;

@Service
public class UserServiceImpl implements UserDetailsService{
	
	private final UserRepository userRepo;
	
	public UserServiceImpl(UserRepository repo){
		this.userRepo=repo;
		
	}

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		return userRepo.findByUsername(username).orElseThrow(()->new UsernameNotFoundException("this user not availabe:"));
	}

}
