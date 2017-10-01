package com.login.table;

import javax.persistence.Embeddable;

/*
 * this is part of the "user" table so both classes in this package make up one table in the database
 */
@Embeddable
public class Name {
	private String firstname;
	private String surname;
	public String getFirstname() {
		return firstname;
	}
	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}
	public String getSurname() {
		return surname;
	}
	public void setSurname(String surname) {
		this.surname = surname;
	}
	@Override
	public String toString() {
		return firstname + " " + surname;
	}
}
