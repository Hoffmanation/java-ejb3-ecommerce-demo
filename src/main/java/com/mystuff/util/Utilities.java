package com.mystuff.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Customer;
import com.mystuff.entity.Product;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.UserRole;

/* Utility class 
 * @author Oren Hoffman
 */
public abstract class Utilities {

	private static final Logger logg = Logger.getLogger(Utilities.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final ModelMapper modelMapper = new ModelMapper();
	
	/**
	 * Convert Entity to ObjectDTO by {@link ModelMapper}
	 * @param <T>
	 * @param convertFrom
	 * @param convertionClass
	 * @return T new instance of the requested object
	 */
	public static <T> T convertToDto (Object convertFrom, Class<T> convertionClass) {
	    return modelMapper.map(convertFrom, convertionClass);
	}
	
	/**
	 * Convert Array of Entities to ObjectDTO by {@link ModelMapper}
	 * @param <T>
	 * @param convertFrom
	 * @param convertionClass
	 * @return T new instance of the requested object
	 */
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
		try {
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

			BeanInfo beanInfo = Introspector.getBeanInfo(SignupWebModel.class);
			for (PropertyDescriptor propertyDesc : beanInfo.getPropertyDescriptors()) {
				String propertyName = propertyDesc.getName();
				Object value = propertyDesc.getReadMethod().invoke(signupModel);
				if (StringUtils.isEmpty(value.toString())) {
					loginError = AppConstants.FIELD_NOT_VALID.replace("{}", propertyName);
					break;
				}
			}
		} catch (Exception e) {
			logg.error("An error occurred while trying to validate SignupWebModel");
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
	public static void initializeDB(DaoBase<Product> productStub, DaoBase<Customer> customerStub) {
		InputStream is = null;
		try {
			logg.info("Attempting to create all products from products.json");
			is = Utilities.class.getClassLoader().getResourceAsStream(AppConstants.DUMMY_PRODUCTS_FILE);
			String productsToCreate = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8))
					.lines()
					.collect(Collectors.joining("\n"));

			List<Product> participantJsonList = mapper.readValue(productsToCreate, new TypeReference<List<Product>>() {});
			participantJsonList.forEach(productStub::create);
			
			//Create ADMIN customer
			Customer orenCustomer = new Customer("Oren", "Hoffman", "oren", "oren@gmail.com", UserRole.ADMIN);
			customerStub.create(orenCustomer) ;
			logg.info("Finished creating all dummy products");
		} catch (Exception e) {
			logg.error("An error occurred while trying to create all dummy products", e);
		}
	}

	
	public static double getCartSum(List<Product> wishlistProducts) {
		return wishlistProducts.stream().map(x -> x.getPrice())
				  .collect(Collectors.summingDouble(Double::doubleValue));
	}


}
