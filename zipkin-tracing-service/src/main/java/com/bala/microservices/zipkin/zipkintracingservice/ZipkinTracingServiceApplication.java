package com.bala.microservices.zipkin.zipkintracingservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import zipkin.server.EnableZipkinServer;



@SpringBootApplication
@EnableZipkinServer
public class ZipkinTracingServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZipkinTracingServiceApplication.class, args);
	}

}
