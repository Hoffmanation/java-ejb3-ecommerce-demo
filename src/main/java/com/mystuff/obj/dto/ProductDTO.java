package com.mystuff.obj.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystuff.obj.ProType;

@JsonInclude(JsonInclude .Include.NON_NULL)
public class ProductDTO extends ModelDtoObject implements Serializable{
	private static final long serialVersionUID = 8886191333597387937L;
	
	private int quantity;
	private int productId;
	private String name;
	private double price;
	private ProType type;
	private String description;
	private String imagePath;
	private int stock;

	public ProductDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
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

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

}
