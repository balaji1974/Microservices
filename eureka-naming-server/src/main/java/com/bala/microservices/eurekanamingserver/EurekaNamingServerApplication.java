package com.bala.microservices.eurekanamingserver;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
@EnableEurekaServer
public class EurekaNamingServerApplication implements CommandLineRunner {
	
	@Autowired
	BCryptPasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(EurekaNamingServerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Pwd Encode : "+passwordEncoder.encode("randowpwd"));
		
	}

}
