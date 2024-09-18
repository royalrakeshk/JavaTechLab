package com.Spring.jwt.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizedUrl;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.Spring.jwt.demo.filter.JwtAuthenticateFilter;
import com.Spring.jwt.demo.service.imp.JwtServiceImpl;
import com.Spring.jwt.demo.service.imp.UserServiceImpl;

@Configuration
@EnableWebSecurity
public class JwtWebSecConfig {
	
	private final UserServiceImpl usersvc;
	
	private final JwtAuthenticateFilter jwtAuthFilterservice;
	
	public JwtWebSecConfig(UserServiceImpl usersvc, JwtAuthenticateFilter jwtAuthFilterservice) {
		super();
		this.usersvc = usersvc;
		this.jwtAuthFilterservice = jwtAuthFilterservice;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
		return http.csrf(AbstractHttpConfigurer::disable)
				.authorizeHttpRequests(
						req->req.requestMatchers("/login/**", "/register/**")
						.permitAll()
						.anyRequest()
						.authenticated()
                     ).userDetailsService(usersvc)
				      .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				      .addFilterBefore(jwtAuthFilterservice, UsernamePasswordAuthenticationFilter.class)
				      .build();
				
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		return new  BCryptPasswordEncoder();
	}
	@Bean
	public AuthenticationManager authenticationmanager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
		
	}

}
