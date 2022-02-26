package com.bala.microservices.cloudconfigserver.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfiguration 
	extends WebSecurityConfigurerAdapter 
	{
	// Disable this in production
	@Override
	public void configure(HttpSecurity http) throws Exception {
		http.csrf()
				.disable()
				.httpBasic();
	}
	
}