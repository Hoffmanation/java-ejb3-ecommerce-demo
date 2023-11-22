package com.mystuff.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import com.mystuff.dao.DaoBase;
import com.mystuff.dao.impl.OrderDaoImpl;
import com.mystuff.dao.impl.ProductDaoImpl;
import com.mystuff.entity.Order;
import com.mystuff.entity.Product;
import com.mystuff.obj.ProType;
import com.mystuff.obj.dto.OrderDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.util.MyStuffException;
import com.mystuff.util.Utilities;

@Stateless
public class CustomerService {

	@EJB
	private ProductDaoImpl  productDaoStub;
	
	@EJB
	private OrderDaoImpl orderDaoStub;

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

	public ProductDTO getProductById(int productId) throws MyStuffException {
		Optional<Product> optionalProduct = productDaoStub.get(productId) ;
		Product product = optionalProduct.orElseThrow(() -> new MyStuffException("Record not found")) ;
		return Utilities.convertToDto(product, ProductDTO.class) ;
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

}
