package com.Spring.jwt.demo.repsitory;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Spring.jwt.demo.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{

	Optional<User> findByUsername(String username);
}
