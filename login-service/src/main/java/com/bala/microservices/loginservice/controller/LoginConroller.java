package com.bala.microservices.loginservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginConroller {
	
	@GetMapping("/login")
	public String doLogin() {
		return "Successful";
	}

}
