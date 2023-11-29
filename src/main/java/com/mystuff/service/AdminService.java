package com.mystuff.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import com.mystuff.dao.impl.CustomerDaoImpl;
import com.mystuff.dao.impl.ProductDaoImpl;
import com.mystuff.entity.Customer;
import com.mystuff.entity.Product;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.util.MyStuffException;
import com.mystuff.util.Utilities;

@Stateless
public class AdminService {
	
	@EJB
	private ProductDaoImpl productDaoStub ;
	
	@EJB
	private CustomerDaoImpl customerDaoStub ;

	public ProductDTO createProduct(ProductDTO productDto) {
		Product newProduct = Utilities.convertToOrFromDto(productDto, Product.class) ;
		newProduct = productDaoStub.create(newProduct) ;
		return Utilities.convertToOrFromDto(newProduct, ProductDTO.class) ;
	}

	public List<CustomerDTO> getAlCustomers() {
		List<Customer> allCustomers = customerDaoStub .getAll() ;
		return Utilities.convertListToOrFromDto(allCustomers, CustomerDTO.class) ;
	}

	public List<CustomerDTO> removeCustomer(int customerId) throws MyStuffException {
		if(customerDaoStub.delete(customerId)) {
			List<Customer> allCustomers = customerDaoStub .getAll() ;
			return Utilities.convertListToOrFromDto(allCustomers, CustomerDTO.class) ;
		} ;
		throw new MyStuffException("Faild to remove customer", customerId) ;
	}

	public CustomerDTO getCustomerById(int customerId) throws MyStuffException {
		Optional<Customer> optionalCustomer = customerDaoStub.get(customerId);
		Customer customer = optionalCustomer.orElseThrow(() -> new MyStuffException("Record not found", customerId));
		return Utilities.convertToOrFromDto(customer, CustomerDTO.class);
	}

}
