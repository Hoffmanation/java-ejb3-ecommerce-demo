package com.mystuff.obj;

public class LoginWebModel {
	
	private String email ;
	private String password;
	
	public LoginWebModel() {
		// TODO Auto-generated constructor stub
	}

	public LoginWebModel(String email, String password) {
		super();
		this.email = email;
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	

}
