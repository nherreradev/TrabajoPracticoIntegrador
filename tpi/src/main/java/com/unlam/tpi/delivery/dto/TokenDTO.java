package com.unlam.tpi.delivery.dto;

public class TokenDTO {
	
	public TokenDTO() {}

	public TokenDTO(String token) {
		super();
		this.token = token;
	}

	private String token;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

}