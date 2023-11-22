package com.mystuff.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.MapsId;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "whishlist")

@NamedQueries({
	@NamedQuery(name = "getWishlistById", query = "SELECT w FROM Wishlist AS w WHERE w.whishlistId = :customerId"),
	@NamedQuery(name = "getAllWishlists", query = "SELECT w FROM Wishlist  AS w ORDER BY w.whishlistId ASC"),
	@NamedQuery(name = "deleteWishlistById", query = "DELETE FROM Wishlist AS w WHERE w.whishlistId = :customerId"),

})

public class Wishlist  implements Serializable{
	private static final long serialVersionUID = 250885203288254412L;

	@Id
	private int whishlistId;

	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "wishlist_product", 
	joinColumns = { @JoinColumn(name = "wishlist_id") }, 
	inverseJoinColumns = {
	@JoinColumn(name = "product_id") }, 
	uniqueConstraints = @UniqueConstraint(columnNames = { "wishlist_id","product_id" }))
	private List<Product> wishlistProducts = new ArrayList<>();

	@OneToOne
	@MapsId
	@JoinColumn(name = "customer_id")
	@JsonIgnore
	private Customer customer;

	public Wishlist() {
	}

	public Wishlist(List<Product> products, Customer customer) {
		this.wishlistProducts = products;
		this.customer = customer;
	}

	public int getWhishlistId() {
		return whishlistId;
	}

	public void setWhishlistId(int whishlistId) {
		this.whishlistId = whishlistId;
	}

	public List<Product> getWishlistProducts() {
		return wishlistProducts;
	}

	public void setWishlistProducts(List<Product> wishlistProducts) {
		this.wishlistProducts = wishlistProducts;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String toString() {
		return "Wishlist [whishlistId=" + customer.getCusomerId() + "]";
	}

}
