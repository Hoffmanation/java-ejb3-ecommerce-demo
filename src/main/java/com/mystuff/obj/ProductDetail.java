package com.mystuff.obj;

import javax.persistence.Column;

//TO DELETE
public class ProductDetail {

	private String name;
	private double price;
	private ProType type;
	private String description;
	private String imagePath;
	private int stock;
	
	public ProductDetail() {
		// TODO Auto-generated constructor stub
	}

	public ProductDetail(String name, double price, ProType type, String description, String imagePath, int stock) {
		super();
		this.name = name;
		this.price = price;
		this.type = type;
		this.description = description;
		this.imagePath = imagePath;
		this.stock = stock;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}
	
	
	
}
