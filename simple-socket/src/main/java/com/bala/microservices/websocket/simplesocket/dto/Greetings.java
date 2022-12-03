package com.bala.microservices.websocket.simplesocket.dto;

public class Greetings {
	private String content;

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Greetings(String content) {
		super();
		this.content = content;
	}

	public Greetings() {
		super();
		// TODO Auto-generated constructor stub
	}
	
}
