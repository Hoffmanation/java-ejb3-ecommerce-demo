package com.mystuff.obj;

public class SignupWebModel {

	private String firstName;
	private String surName;
	private String password;
	private String secondPassword;
	private String email;

	public SignupWebModel() {
		// TODO Auto-generated constructor stub
	}

	public SignupWebModel(String firstName, String surName, String password, String secondPassword, String email) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.password = password;
		this.secondPassword = secondPassword;
		this.email = email;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getSurName() {
		return surName;
	}

	public void setSurName(String surName) {
		this.surName = surName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSecondPassword() {
		return secondPassword;
	}

	public void setSecondPassword(String secondPassword) {
		this.secondPassword = secondPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
