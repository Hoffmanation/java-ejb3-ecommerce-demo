package com.mystuff.obj.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystuff.util.Utilities;

@JsonInclude(JsonInclude .Include.NON_NULL)
public class OrderDTO extends ModelDtoObject {

	private int orderId;
	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="yyyy-MM-dd'T'HH:mm")
	private Date timestamp;
	private double amount;
	private List<ProductDTO> products;
	private int size ;
	private int sum ;

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

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	
	public int getSize() {
		return products.isEmpty() ? 0 : products.size();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getSum() {
		return Utilities.getCartSum(this.products);
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

}
