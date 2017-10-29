package com.login.dto;

/**
 * this is a bean for the login page
 * */ 
public class Login{
	private String username;
	private char[] password;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public char[] getPassword() {
		return password;
	}
	public void setPassword(char[] password) {
		this.password = password;
	}
}
