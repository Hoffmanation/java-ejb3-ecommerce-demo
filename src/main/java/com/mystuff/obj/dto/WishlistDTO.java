package com.mystuff.obj.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishlistDTO extends ModelDtoObject {

	private int whishlistId;
	private List<ProductDTO> wishlistProducts;
	private CustomerDTO customer;

	public WishlistDTO() {
		// TODO Auto-generated constructor stub
	}

	public int getWhishlistId() {
		return whishlistId;
	}

	public void setWhishlistId(int whishlistId) {
		this.whishlistId = whishlistId;
	}

	public List<ProductDTO> getWishlistProducts() {
		return wishlistProducts;
	}

	public void setWishlistProducts(List<ProductDTO> wishlistProducts) {
		this.wishlistProducts = wishlistProducts;
	}

	public CustomerDTO getCustomer() {
		return customer;
	}

	public void setCustomer(CustomerDTO customer) {
		this.customer = customer;
	}

}
