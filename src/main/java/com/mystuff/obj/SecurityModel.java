package com.mystuff.obj;

public class SecurityModel {

	private String email;
	private UserRole role;

	public SecurityModel() {
		// TODO Auto-generated constructor stub
	}

	public SecurityModel(String email, UserRole role) {
		super();
		this.email = email;
		this.role = role;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

}
