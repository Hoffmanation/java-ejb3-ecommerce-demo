package com.mystuff.entity;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import com.mystuff.obj.ProType;

@Entity
@Table(name = "product")

@NamedQueries({ @NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product  AS p ORDER BY p.productId ASC"),
		@NamedQuery(name = "getProductByName", query = "SELECT p FROM Product AS p WHERE p.name = :name"),
		@NamedQuery(name = "getProductById", query = "SELECT p FROM Product AS p WHERE p.productId = :productId"),
		@NamedQuery(name = "getAllProductByPriceEx", query = "SELECT p FROM Product AS p ORDER BY p.price desc"),
		@NamedQuery(name = "getAllProductByPriceChe", query = "SELECT p FROM Product AS p ORDER BY p.price asc"),
		@NamedQuery(name = "searchProductByName", query = "SELECT p FROM Product p WHERE UPPER(p.name) LIKE :name"),
		@NamedQuery(name = "getAllProductsByType", query = "SELECT p FROM Product p WHERE p.type = :type"),
		@NamedQuery(name = "serchProductByPriceRange", query = "SELECT p FROM Product p WHERE p.price  BETWEEN  :price AND :price"),
		@NamedQuery(name = "deleteProductById", query = "DELETE from Product p where p.productId=:productId"),

})
public class Product {

	private int quantity;
	@Id
	@Column(name = "product_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int productId;
	private String name;
	private double price;
	
	@Enumerated(EnumType.STRING)
	private ProType type;
	
	@Column(columnDefinition = "text")
	private String description;
	
	private String imagePath;
	
	private int stock;


	public Product() {

	}

	public Product(String name, double price, ProType type, String description, String imagePath, int stock,
			int quantity) {
		super();
		this.quantity = quantity;
		this.name = name;
		this.price = price;
		this.type = type;
		this.description = description;
		this.imagePath = imagePath;
		this.stock = stock;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProType getType() {
		return type;
	}

	public void setType(ProType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product [quantity=" + quantity + ", productId=" + productId + ", name=" + name + ", price=" + price
				+ ", type=" + type + ", description=" + description + ", imagePath=" + imagePath + ", stock=" + stock
				+ "]";
	}

}
