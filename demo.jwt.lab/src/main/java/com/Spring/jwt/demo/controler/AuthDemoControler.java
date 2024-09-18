package com.Spring.jwt.demo.controler;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthDemoControler {
	
	@GetMapping("/demo")
	public ResponseEntity<String> getDemoToekn(){
		return ResponseEntity.ok("This is from demo controler");
	}

}
