package com.Spring.jwt.demo.service.imp;

import java.security.Key;
import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.Spring.jwt.demo.model.User;
import com.Spring.jwt.demo.service.JwtService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtServiceImpl implements JwtService{

	private  final String SECRET_KEY="d82ee56fcf9b122a2a091e4778b7115d69b756b44bc63a94532f535d9af18d13";

	public String extractUserNamne(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	private boolean isTokenExpire(String token) {
		return expirationdate(token).before(new Date());
	}
	
	private Date expirationdate(String token) {
		return extractClaim(token, Claims::getExpiration);
	}
	public boolean isvalid(String token,UserDetails user) {
		String username=extractUserNamne(token);
		return username.equals(user.getUsername()) && !isTokenExpire(token);
	}
	public <T> T extractClaim(String token,Function<Claims, T> resolver) {
		Claims claim=extractAllClaim(token);
		return resolver.apply(claim);
	}
	
	private Claims extractAllClaim(String token) {
		
		return Jwts.parser()
				.verifyWith(getSignKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	@Override
	public String generateToken(User user) {
		// TODO Auto-generated method stub
		String token=Jwts
				     .builder()
				     .subject(user.getUsername())
				     .issuedAt(new Date(System.currentTimeMillis()))
				     .expiration(new Date(System.currentTimeMillis()+24*60*60*1000))
				     .signWith(getSignKey())
				     .compact();
		return token;
	}

	private SecretKey getSignKey() {
		// TODO Auto-generated method stub
		byte[] byteKey=Decoders.BASE64URL.decode(SECRET_KEY);
		
		return Keys.hmacShaKeyFor(byteKey);
	}
	
}
