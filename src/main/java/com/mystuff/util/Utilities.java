package com.mystuff.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.Principal;
import java.security.SecureRandom;
import java.security.spec.KeySpec;
import java.util.Base64;
import java.util.List;
import java.util.stream.Collectors;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import javax.ws.rs.core.SecurityContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;
import org.jboss.logging.Logger;
import org.modelmapper.ModelMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Customer;
import com.mystuff.entity.Product;
import com.mystuff.obj.AppPrincipal;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.UserRole;
import com.mystuff.obj.dto.CustomerDTO;

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
	public static <T> T convertToOrFromDto (Object convertFrom, Class<T> convertionClass) {
	    return modelMapper.map(convertFrom, convertionClass);
	}
	
	/**
	 * Convert Array of Entities to ObjectDTO by {@link ModelMapper}
	 * @param <T>
	 * @param convertFrom
	 * @param convertionClass
	 * @return T new instance of the requested object
	 */
	public static <T> List<T> convertListToOrFromDto (List<? extends Serializable> convertFrom, Class<T> convertionClass) {
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
			} else if (firstPass.length() > 12) {
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
		try {
			String productsToCreate = new String(Files.readAllBytes(Paths.get(AppConstants.DUMMY_PRODUCTS_FILE ))) ;
			List<Product> participantJsonList = mapper.readValue(productsToCreate, new TypeReference<List<Product>>() {});
			participantJsonList.forEach(productStub::create);
			
			//Create ADMIN customer
			String password = Utilities.encrypt("oren", "oren", "oren@gmail.com") ;
			Customer orenCustomer = new Customer("Oren", "Hoffman", password, "oren@gmail.com", UserRole.ADMIN);
			customerStub.create(orenCustomer) ;
			logg.info("Finished creating all dummy products");
		} catch (Exception e) {
			logg.error("An error occurred while trying to create all dummy products", e);
		}
	}

	/**
	 * Get Amount to pay for all products in the wishlist
	 * @param wishlistProducts
	 * @return
	 */
	public static double getCartSum(List<? extends Serializable> wishlistProducts) {
		return  wishlistProducts
		.stream()
		.map(genericProduct -> {
					try {
						Method m = genericProduct.getClass().getMethod("getPrice");
						return (double) m.invoke(genericProduct) ;
					} catch (Exception e) {
						logg.error("An error occurred while trying to getCartSum with reflection", e);
					}
					return 0.0 ;
		})
		.collect(Collectors.summingDouble(Double::doubleValue));
	}

	/**
	 * Retrive application principal as the looged in user and return his ID
	 * @param securityContext
	 * @return PK of the logged in {@link CustomerDTO}
	 */
	public static int getLogedInCustomerId(SecurityContext securityContext) {
		Principal principal = securityContext.getUserPrincipal() ;
		AppPrincipal  logedInUser = (AppPrincipal) principal ;
		return logedInUser.getPrincipalId() ;
	}
	
	
	/**
	 * Encrypt User parrword with AES algorithm 
	 * @param strToEncrypt
	 * @param secretKey
	 * @param salt
	 * @return
	 * @throws Exception 
	 */
	public static String encrypt(String strToEncrypt, String secretKey, String salt) throws Exception {
			SecureRandom secureRandom = new SecureRandom();
			byte[] iv = new byte[16];
			secureRandom.nextBytes(iv);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec, ivspec);

			byte[] cipherText = cipher.doFinal(strToEncrypt.getBytes("UTF-8"));
			byte[] encryptedData = new byte[iv.length + cipherText.length];
			System.arraycopy(iv, 0, encryptedData, 0, iv.length);
			System.arraycopy(cipherText, 0, encryptedData, iv.length, cipherText.length);

			return Base64.getEncoder().encodeToString(encryptedData);
	}
	
	/**
	 * Decrypt User parrword with AES algorithm 
	 * @param strToEncrypt
	 * @param secretKey
	 * @param salt
	 * @return
	 * @throws Exception 
	 */
	public static String decrypt(String strToDecrypt, String secretKey, String salt) throws Exception {
			byte[] encryptedData = Base64.getDecoder().decode(strToDecrypt);
			byte[] iv = new byte[16];
			System.arraycopy(encryptedData, 0, iv, 0, iv.length);
			IvParameterSpec ivspec = new IvParameterSpec(iv);

			SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
			KeySpec spec = new PBEKeySpec(secretKey.toCharArray(), salt.getBytes(), 65536, 256);
			SecretKey tmp = factory.generateSecret(spec);
			SecretKeySpec secretKeySpec = new SecretKeySpec(tmp.getEncoded(), "AES");

			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec, ivspec);

			byte[] cipherText = new byte[encryptedData.length - 16];
			System.arraycopy(encryptedData, 16, cipherText, 0, cipherText.length);

			byte[] decryptedText = cipher.doFinal(cipherText);
			return new String(decryptedText, "UTF-8");
	}

	public static String convertToJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	


}
