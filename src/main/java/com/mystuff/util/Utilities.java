package com.mystuff.util;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.naming.InitialContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Customer;
import com.mystuff.entity.Order;
import com.mystuff.entity.Product;
import com.mystuff.entity.Wishlist;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.dto.ModelDtoObject;

/* Utility class 
 * @author Oren Hoffman
 */
public abstract class Utilities {

	private static final Logger logg = Logger.getLogger(Utilities.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ModelMapper modelMapper = new ModelMapper();
	
	
	public static <T> T convertToDto (Object convertFrom, Class<T> convertionClass) {
	    return modelMapper.map(convertFrom, convertionClass);
	}
	
	public static <T> List<T> convertAllToDto (List<? extends Serializable> convertFrom, Class<T> convertionClass) {
		return convertFrom.stream()
		.map(entity -> modelMapper.map(entity, convertionClass))
		.collect(Collectors.toList());
	}

	/**
	 * Validate email structure
	 * 
	 * @param email
	 */
	public static boolean IsValidEmail(String email) {
		return EmailValidator.getInstance().isValid(email);
	}

	/**
	 * Perform validity check upon customer registration Will return error message
	 * in case one was found
	 * 
	 * @param customer
	 * @param cart
	 * @return {@link String} rather empty or contains error description
	 */
	public static String getSignUpErrors(SignupWebModel signupModel,Customer customerWithSameEmail) {
		String loginError = StringUtils.EMPTY;
		String firstPass = signupModel.getPassword();
		String secondPass = signupModel.getSecondPassword();
		String email = signupModel.getEmail();
		boolean emailAlreadyExists = customerWithSameEmail != null;
		if (!firstPass.equals(secondPass)) {
			loginError = AppConstants.PASS_DONT_MATCH;
		} else if (firstPass.length() > 10 || secondPass.length() > 10) {
			loginError = AppConstants.PASS_LENGTH_NOT_VALID;
		} else if (!IsValidEmail(email)) {
			loginError = AppConstants.EMAIL_NOT_VALID;
		} else if (emailAlreadyExists) {
			loginError = AppConstants.EMAIL_EXISTS;
		}
		return loginError;
	}

	/**
	 * Perform validity check upon customer cart checkout Will return error message
	 * in case one was found
	 * 
	 * @param customer
	 * @param cart
	 * @return {@link String} rather empty or contains error description
	 */
	public static String getChackOutErrors(Customer customer, List<Product> cartProducts) {
		String chackOutError = StringUtils.EMPTY;
		if (null == customer) {
			chackOutError = AppConstants.PLEASE_LOG_IN;
		} else if (cartProducts.isEmpty()) {
			chackOutError = AppConstants.CART_IS_EMPTY;
		}
		return chackOutError;
	}

	/**
	 * Read dummy product list from local JSON file and Insert all products to the
	 * DB upon application startup
	 * 
	 * @param productStub
	 */
	public static void initializeDB(DaoBase<Product> productStub) {
		InputStream is = null;
		try {
			logg.info("Attempting to create all products from products.json");
			is = Utilities.class.getClassLoader().getResourceAsStream(AppConstants.DUMMY_PRODUCTS_FILE);
			String productsToCreate = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)).lines()
					.collect(Collectors.joining("\n"));

			List<Product> participantJsonList = mapper.readValue(productsToCreate, new TypeReference<List<Product>>() {
			});
			participantJsonList.forEach(productStub::create);
			logg.info("Finished creating all dummy products");
		} catch (Exception e) {
			logg.error("An error occurred while trying to create all dummy products", e);
		}
	}

	@SuppressWarnings("unchecked")
	public static void initializeUserDB() {
		try {
			logg.info("Attempting to create all Customers");
			
			//DAO from DI
			DaoBase<Wishlist> wishlistStub = (DaoBase<Wishlist>) new InitialContext().lookup("java:global/mystuff/WishlistDaoImpl");
			DaoBase<Customer> customerStub = (DaoBase<Customer>) new InitialContext().lookup("java:global/mystuff/CustomerDaoImpl");
			DaoBase<Product> productStub = (DaoBase<Product>) new InitialContext().lookup("java:global/mystuff/ProductDaoImpl");
			DaoBase<Order> orderStub = (DaoBase<Order>) new InitialContext().lookup("java:global/mystuff/OrderDaoImpl");

			//Create customer
			List<Product> participantJsonList = productStub.getAll() ;
			Customer customer = new Customer("Oren", "Hoffman", "oren", "oren@gmail.com");
			customerStub.create(customer) ;
			
			//Get it back from ORM
			customer  = customerStub.getAll().get(0) ;
			
			//Create the wishlist
			Wishlist wishlist = new Wishlist(participantJsonList,customer);
			wishlistStub.create(wishlist) ;
			
			//Create the order
			Order order = new Order(new Date(), 66.3 , participantJsonList, customer) ;
			orderStub.create(order) ;
			//Update Customer
			List<Order> orders =  customer.getOrders() ;
			orders.add(order) ;
			customer.setWishlist(wishlist);
			
			//Update back the customer
			customerStub.update(customer) ;
			
			Optional<Customer> optionalCustomer = customerStub.get(1) ;
			if (optionalCustomer.isPresent()) {
				Customer createdCustomer = optionalCustomer.get() ;
				
				//Create the new order
				List<Product> newProdList =  new ArrayList<>() ;
				newProdList.add(participantJsonList.get(8));
				Order newOrder = new Order(new Date(), 88.3 , newProdList, createdCustomer) ;
				orderStub.create(newOrder) ;
				
				//Update Customer order
				List<Order> oldOrders =  createdCustomer.getOrders() ;
				oldOrders.add(newOrder) ;
				
				//Update user wishlist 
				Wishlist oldWishlist = createdCustomer.getWishlist() ;
				List<Product> newWishlistProducts = new ArrayList<>();
				newWishlistProducts.add(participantJsonList.get(0));
				newWishlistProducts.add(participantJsonList.get(1));
				oldWishlist.setWishlistProducts(newWishlistProducts);
				customerStub.update(customer) ;
				
				optionalCustomer = customerStub.get(1) ;
				if (optionalCustomer.isPresent()) {
					 createdCustomer = optionalCustomer.get() ; 
					 String  customerJson = mapper.writeValueAsString(createdCustomer);
					 System.err.println(customerJson);
				}
				
				//New transaction 
				//Create customer
				participantJsonList = productStub.getAll() ;
				customer = new Customer("Agatha", "Rotman", "agatha", "agatha@gmail.com");
				customerStub.create(customer) ;
				
				//Get it back from ORM
				customer  = customerStub.getAll().get(1) ;
				
				//Create the wishlist
				wishlist = new Wishlist(participantJsonList,customer);
				wishlistStub.create(wishlist) ;
				createdCustomer.setWishlist(wishlist) ;
				customerStub.update(createdCustomer) ;
				
				List<Customer> latestAllCustomers = customerStub.getAll() ;
				 String  allCustomersJson = mapper.writeValueAsString(latestAllCustomers);
				 System.err.println(allCustomersJson);
				 
				 ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
				    executor.schedule(new Runnable() {
						
						@Override
						public void run() {
							try {
								List<Customer> latestAllCustomers = customerStub.getAll();
								String allCustomersJson = mapper.writeValueAsString(latestAllCustomers);
								System.err.println(allCustomersJson);
								
								System.err.println("From here:\n");
								
								Optional<Wishlist>  wishlistOptional = wishlistStub.get(1) ;
								if (wishlistOptional.isPresent()) {
									Wishlist aishlists = wishlistOptional.get() ;
									String aishlistsJson = mapper.writeValueAsString(aishlists);
									System.err.println(aishlistsJson );
								}

								List<Wishlist> allWishlists = wishlistStub.getAll();
								String allWishlistsJson = mapper.writeValueAsString(allWishlists);
								System.err.println(allWishlistsJson);
								
								
								
							} catch (Exception e) {
								e.printStackTrace();
							}

						}
					}, 10, TimeUnit.SECONDS);
					executor.shutdown();
					executor.awaitTermination(6, TimeUnit.SECONDS);

			}
			logg.info("Finished creating all dummy Customer");
		} catch (Exception e) {
			logg.error("An error occurred while trying to create all dummy Customers", e);
		}

	}

}
