package com.mystuff.obj;

public class SignupModel {
	
	private String firstName ;
	private String surName ;
	private String password ;
	private String secondPassword ;
	private String email ;
	
	public SignupModel() {
		// TODO Auto-generated constructor stub
	}

	public SignupModel(String firstName, String surName, String password, String secondPassword, String email) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.password = password;
		this.secondPassword = secondPassword;
		this.email = email;
	}

	public String getName() {
		return firstName;
	}

	public void setName(String name) {
		this.firstName = name;
	}

	public String getF_name() {
		return surName;
	}

	public void setF_name(String f_name) {
		this.surName = f_name;
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
