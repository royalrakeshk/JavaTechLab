package com.Spring.jwt.demo.filter;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.Spring.jwt.demo.service.JwtService;
import com.Spring.jwt.demo.service.imp.JwtServiceImpl;
import com.Spring.jwt.demo.service.imp.UserServiceImpl;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticateFilter extends OncePerRequestFilter{
	
	private final JwtServiceImpl jwtservice;
	private final UserServiceImpl userServiceImpl;

	public JwtAuthenticateFilter(JwtServiceImpl jwtservice,UserServiceImpl userServiceImpl) {
		super();
		this.jwtservice = jwtservice;
		this.userServiceImpl=userServiceImpl;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request,
			HttpServletResponse response, 
			FilterChain filterChain)
			throws ServletException, IOException {

		String authHeader=request.getHeader("Authorization");
		if(authHeader ==null || ! authHeader.startsWith("bearer ")) {
			filterChain.doFilter(request, response);
			return;
		}
		String token=authHeader.substring(7);
		String username=jwtservice.extractUserNamne(token);
		if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null) {
			UserDetails userdetails=userServiceImpl.loadUserByUsername(username);
			if(jwtservice.isvalid(token, userdetails)) {
				UsernamePasswordAuthenticationToken authtoken=new UsernamePasswordAuthenticationToken(userdetails, null, userdetails.getAuthorities());
				authtoken.setDetails(
						new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authtoken);
			}
			
		}
		filterChain.doFilter(request, response);
	}

}
