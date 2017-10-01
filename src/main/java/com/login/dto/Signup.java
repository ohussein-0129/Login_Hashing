package com.login.dto;


/*this is a bean for the signup page*/ 
public class Signup {
	private String username;
	private String password;
	private String firstname;
	private String surname;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String lastname) {
		this.surname = lastname;
	}
	
	
}
