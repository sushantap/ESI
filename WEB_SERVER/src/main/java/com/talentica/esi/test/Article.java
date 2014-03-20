package com.talentica.esi.test;

public class Article {
	private final String header;
	private final String body;
	
	public Article(String header, String body) {
		this.header = header;
		this.body = body;
	}
	public String getHeader() {
		return header;
	}

	public String getBody() {
		return body;
	}
}
