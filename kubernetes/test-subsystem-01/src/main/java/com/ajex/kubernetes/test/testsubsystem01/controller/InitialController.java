package com.ajex.kubernetes.test.testsubsystem01.controller;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import reactor.core.publisher.Mono;

@RestController
public class InitialController {
	
	@GetMapping("/calculate/square/{number}")
	public ResponseEntity<String> startup(@PathVariable Integer number) {
		WebClient client = WebClient.builder()
				  .baseUrl("http://test-subsystem-02:8090/calculate/square/"+number)
				  .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) 
				  .build();
		return client.get()
	    .retrieve()
	    .onStatus(
	        status -> status.value() == 401,
	        clientResponse -> Mono.empty()
	    )
	    .toEntity(String.class)
	    .block();
	}

}
