package com.mystuff.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mystuff.dao.impl.CustomerDaoImpl;
import com.mystuff.dao.impl.OrderDaoImpl;
import com.mystuff.dao.impl.ProductDaoImpl;
import com.mystuff.dao.impl.WishlistDaoImpl;
import com.mystuff.entity.Customer;
import com.mystuff.entity.Order;
import com.mystuff.entity.Product;
import com.mystuff.entity.Wishlist;
import com.mystuff.obj.ProType;
import com.mystuff.obj.dto.OrderDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.obj.dto.WishlistDTO;
import com.mystuff.rest.CustomerController;
import com.mystuff.util.MyStuffException;
import com.mystuff.util.Utilities;

/**
 * Service to hold Customer ability method 
 * Injected within {@link CustomerController}
 */
@Stateless
public class CustomerService {

	@EJB
	private ProductDaoImpl  productDaoStub;
	
	@EJB
	private OrderDaoImpl orderDaoStub;
	
	@EJB
	private CustomerDaoImpl customerDaoStub;
	
	@EJB
	private WishlistDaoImpl wishlistDaoStub;

	public List<ProductDTO> getAllProducts() {
		 List<Product> products  = productDaoStub.getAll();
		  return Utilities.convertAllToDto(products, ProductDTO.class) ;
	}

	public List<ProductDTO> searchProductByPriceRange(double minPrice, double maxPrice) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("minPrice", minPrice);
		parameters.put("maxPrice", maxPrice);
		List<Product> products  = productDaoStub.getResultListCustomQuery("searchProductByPriceRange", parameters);
		return Utilities.convertAllToDto(products, ProductDTO.class) ;
	}

	public List<ProductDTO> getAllProductByPriceEx() {
		List<Product> products  = productDaoStub.getResultListCustomQuery("getAllProductByPriceEx", null);
		return Utilities.convertAllToDto(products, ProductDTO.class) ;
	}

	public List<ProductDTO> getAllProductByPriceChe() {
		List<Product> products  = productDaoStub.getResultListCustomQuery("getAllProductByPriceChe", null);
		return Utilities.convertAllToDto(products, ProductDTO.class) ;
	}

	public ProductDTO getProductById(int productId) throws MyStuffException   {
		Optional<Product> optionalProduct = productDaoStub.get(productId);
		Product product = optionalProduct.orElseThrow(() -> new MyStuffException("Record not found"));
		return Utilities.convertToDto(product, ProductDTO.class);
	}

	public List<ProductDTO> getAllProductsByType(ProType  type) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("type", type);
		List<Product> products  = productDaoStub.getResultListCustomQuery("getAllProductsByType", parameters);
		return Utilities.convertAllToDto(products, ProductDTO.class) ;
	}

	public List<OrderDTO> getAllCustomerOrdersById(int customerId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("customerId", customerId);
		List<Order> orders  = orderDaoStub.getResultListCustomQuery("getOrdersByCustomerId", parameters);
		return Utilities.convertAllToDto(orders , OrderDTO.class) ;
	}

	public OrderDTO getCustomerOrdersById(int orderId) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("orderId", orderId);
		Order order  = orderDaoStub.getResultCustomQuery("getOrderById", parameters);
		return Utilities.convertToDto(order , OrderDTO.class) ;
	}
	
	public WishlistDTO addToWishlist(int productId, int customerId) throws MyStuffException {
		Optional<Product> optionalProduct = productDaoStub.get(productId) ;
		Product product = optionalProduct.orElseThrow(() -> new MyStuffException("Record not found")) ;
		Wishlist customerWishlist =  this.getCustomerWishlist(customerId);
		customerWishlist.getWishlistProducts().add(product) ;
		wishlistDaoStub.update(customerWishlist) ;
		return Utilities.convertToDto(customerWishlist , WishlistDTO.class) ;
	}

	public WishlistDTO removeFromWishlist(int productId, int customerId) throws MyStuffException {
		Wishlist customerWishlist =  this.getCustomerWishlist(customerId);
		customerWishlist.getWishlistProducts().removeIf(product -> product.getProductId() == productId) ;
		wishlistDaoStub.update(customerWishlist) ;
		return Utilities.convertToDto(customerWishlist , WishlistDTO.class) ;
	}

	public WishlistDTO getWishlistDTO(int customerId) throws MyStuffException {
		Wishlist customerWishlist = this.getCustomerWishlist(customerId) ;
		return Utilities.convertToDto(customerWishlist , WishlistDTO.class) ;
	}
	
	private Wishlist getCustomerWishlist(int customerId) throws MyStuffException {
		Optional<Wishlist> wishlistOptional = wishlistDaoStub.get(customerId) ;
		Wishlist customerWishlist = wishlistOptional.orElseThrow(() -> new MyStuffException("Record not found", customerId)) ;
		if (customerWishlist == null) {
			Optional<Customer> customerOptional = customerDaoStub.get(customerId) ;
			Customer customer = customerOptional.orElseThrow(() -> new MyStuffException("Record not found", customerId)) ;
			customerWishlist = wishlistDaoStub.create(new Wishlist(new ArrayList<>() , customer)) ;
		}
		return customerWishlist ;
	}

	public OrderDTO chackOut(int customerId) throws MyStuffException {
		Optional<Customer> customerOptional = customerDaoStub.get(customerId) ;
		Customer customer = customerOptional.orElseThrow(() -> new MyStuffException("Record not found", customerId)) ;
		Wishlist customerWishlist =  this.getCustomerWishlist(customerId);
		List<Product> wishlistProducts = customerWishlist.getWishlistProducts() ;
		if (wishlistProducts!=null && !wishlistProducts.isEmpty()) {
			
			double cartSum = Utilities.getCartSum(wishlistProducts) ;
			Order order = new Order(new Date(), cartSum , wishlistProducts, customer) ;
			orderDaoStub.create(order) ;
			
			List<Order> orders =  customer.getOrders() ;
			orders.add(order) ;
			customerDaoStub.update(customer) ;
			customerWishlist.setWishlistProducts(new ArrayList<>());
			wishlistDaoStub.update(customerWishlist) ;
			return Utilities.convertToDto(order , OrderDTO.class) ;
		}	
		
		throw new MyStuffException("wishlistProducts is invalid", customerId) ;
	}

}
