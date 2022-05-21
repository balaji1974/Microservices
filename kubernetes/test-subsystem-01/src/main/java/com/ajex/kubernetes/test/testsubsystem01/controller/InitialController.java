package com.ajex.kubernetes.test.testsubsystem01.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class InitialController {
	
	@Autowired
	private Environment environment;
	
	@GetMapping("/calculator/square/{number}")
	public ResponseEntity<Object> startup(@PathVariable Integer number) {
		Map<String, Object> p=new HashMap<String, Object>();
		p.put("result", number*number);
		p.put("pod-name", environment.getProperty("HOSTNAME") );
		return ResponseEntity.status(HttpStatus.CREATED).body(
	            Collections.unmodifiableMap(p)
				);
	}

}
