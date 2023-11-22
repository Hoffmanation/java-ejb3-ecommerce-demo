package com.mystuff.obj.dto;

import java.util.Date;
import java.util.List;

import com.mystuff.entity.Product;

public class OrderDTO extends ModelDtoObject {

	private int orderId;
	private Date timestamp;
	private double amount;
	private List<Product> products;

	public OrderDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

}
