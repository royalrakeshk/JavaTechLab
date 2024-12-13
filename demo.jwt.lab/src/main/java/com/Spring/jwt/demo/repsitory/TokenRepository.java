package com.Spring.jwt.demo.repsitory;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.Spring.jwt.demo.model.Token;
import com.Spring.jwt.demo.model.User;

public interface TokenRepository extends JpaRepository<Token, Integer>{
	
	@Query("select t from Token t inner join User u on t.user.id=u.id where t.user.id=:userId and t.loggedout=false")
	public List<Token> findAllTokenByUser(Integer userId);

	
	Optional<Token> findByToken(String token);
}
