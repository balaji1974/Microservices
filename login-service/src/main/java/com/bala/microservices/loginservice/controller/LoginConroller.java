package com.bala.microservices.loginservice.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.resilience4j.retry.annotation.Retry;

@RestController
public class LoginConroller {
	
	Logger logger=LoggerFactory.getLogger(LoginConroller.class);
	
	@GetMapping("/login")
	public String doLogin() {
		return "Successful";
	}
	
	
	@GetMapping("/hello")
	@Retry(name="hello-api" , fallbackMethod="helloFallback")
	public String helloWorld() throws RuntimeException{
		logger.info("Method is being called");
		if(true) throw new RuntimeException();
		return "Hello World";
	}
	
	public String helloFallback(Exception e) {
		return "This is fallback response";
	}

}


