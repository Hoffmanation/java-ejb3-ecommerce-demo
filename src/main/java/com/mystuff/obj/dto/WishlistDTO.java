package com.mystuff.obj.dto;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mystuff.util.Utilities;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WishlistDTO extends ModelDtoObject {

	private int whishlistId;
	private List<ProductDTO> wishlistProducts;
	private CustomerDTO customer;
	private int size ;
	private int sum ;

	public WishlistDTO() {
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

	public int getSize() {
		return wishlistProducts.isEmpty() ? 0 : wishlistProducts.size();
	}

	public void setSize(int size) {
		this.size = size;
	}

	public double getSum() {
		return Utilities.getCartSum(this.wishlistProducts);
	}

	public void setSum(int sum) {
		this.sum = sum;
	}
	
	

}
