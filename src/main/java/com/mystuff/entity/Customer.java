package com.mystuff.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

import com.mystuff.obj.UserRole;

@Entity
@Table(name = "customer")
@NamedQueries({ @NamedQuery(name = "getAllCustomers", query = "SELECT c FROM Customer AS c ORDER BY c.cusomerId asc"),
		@NamedQuery(name = "getCustomerByName", query = "SELECT c FROM Customer AS c WHERE UPPER(c.firstName) LIKE :firstName"),
		@NamedQuery(name = "getCustomerByEmail", query = "SELECT c FROM Customer AS c WHERE c.email = :email"),
		@NamedQuery(name = "getCustomerByEmailAndPassword", query = "SELECT c FROM Customer AS c WHERE c.password = :password AND c.email = :email"), })

public class Customer implements Serializable {
	private static final long serialVersionUID = -3240716900978864647L;

	@Id
	@Column(name = "customer_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int cusomerId;
	
	private String firstName;
	
	private String surName;
	
	@Column(columnDefinition = "VARCHAR(10)")
	private String password;
	
	@Column(name = "email",unique = true, updatable = false)
	private String email;
	
	@OneToOne(fetch = FetchType.EAGER, mappedBy = "customer", cascade = CascadeType.ALL)
	@PrimaryKeyJoinColumn
	private Wishlist wishlist;
	
	@OneToMany(mappedBy="customer", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private List<Order> orders = new ArrayList<>();
	
	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	 private UserRole role;

	public Customer() {

	}
	
	public Customer(String firstName, String surName, String password, String email, UserRole role) {
		super();
		this.firstName = firstName;
		this.surName = surName;
		this.password = password;
		this.email = email;
		this.role = role;
	}

	public int getCusomerId() {
		return cusomerId;
	}

	public void setCusomerId(int cusomerId) {
		this.cusomerId = cusomerId;
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}
	
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	public UserRole getRole() {
		return role;
	}

	public void setRole(UserRole role) {
		this.role = role;
	}

	@Override
	public String toString() {
		return "Customer [cusomerId=" + cusomerId + 
				", firstName=" + firstName + ", surName=" + surName + ", password="
				+ password + ", email=" + email + "]";
	}

}
