package com.bala.microservices.websocket.simplesocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

import com.bala.microservices.websocket.simplesocket.dto.Employee;
import com.bala.microservices.websocket.simplesocket.dto.Greetings;

@Controller
public class GreetingsController {

	@MessageMapping("/hello")
	@SendTo("/topic/greetings")
	public Greetings greeting(Employee employee) throws Exception {
		Thread.sleep(1000); // simulated delay
		return new Greetings("Hello, " + HtmlUtils.htmlEscape(employee.getName()) + "!");
	}
}
