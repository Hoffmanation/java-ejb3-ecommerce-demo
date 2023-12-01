package com.mystuff.service;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;

import com.mystuff.dao.impl.CustomerDaoImpl;
import com.mystuff.entity.Customer;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.UserRole;
import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.rest.security.AppSecurityContext;
import com.mystuff.util.AppConstants;
import com.mystuff.util.Utilities;

/**
 * Service to hold security method 
 * Injected within {@link AppSecurityContext}
 */
@Stateless
public class SecurityService {
	private static final Logger logger = Logger.getLogger(SecurityService.class);

	@EJB
	private CustomerDaoImpl customerDaoStub;

	public WebResponse signUp(@NotNull SignupWebModel signupModel) {
		Customer customerWithSameEmail = this.getCustomerByEmail(signupModel.getEmail());
		String signUpError = Utilities.getSignUpErrors(signupModel,customerWithSameEmail);
		if (StringUtils.isNotEmpty(signUpError)) {
			logger.error("Failde to signUp, Error message: " + signUpError);
			return new WebResponse(AppConstants.BAD_SIGNUP, false,signUpError) ;
		}
		
		try {
			//Encrypt Password and create the new user 
			String encryptedPassword = Utilities.encrypt(signupModel.getPassword(), signupModel.getPassword(), signupModel.getEmail()) ;
			Customer newCustomer = new Customer(signupModel.getFirstName(), signupModel.getSurName(), 
					encryptedPassword, signupModel.getEmail(),UserRole.CUSTOMER);
			customerDaoStub.create(newCustomer); 
			CustomerDTO customerDto = Utilities.convertToOrFromDto(newCustomer, CustomerDTO.class) ;
			return new WebResponse(AppConstants.GOOD_SIGNUP, true,customerDto) ;
		} catch (Exception e) {
			e.printStackTrace();
			return new WebResponse(AppConstants.BAD_SIGNUP, false,e.getMessage()) ;
		}
	}

	public WebResponse login(String email, String password) {
		try {
			Customer loginCustomer = this.getCustomerByEmail(email);
			String decryptedPassword = 
					loginCustomer != null
					? Utilities.decrypt(loginCustomer.getPassword(), password, email)
					: StringUtils.EMPTY;

			if (decryptedPassword.equals(password)) {
				CustomerDTO customerDto = Utilities.convertToOrFromDto(loginCustomer, CustomerDTO.class);
				return new WebResponse(AppConstants.GOOD_LOGIN, true, customerDto);
			}
		} catch (Exception e) {
			logger.error(String.format("An error occurred while trying to login user, customer email: %s", email));
		}
		return new WebResponse(AppConstants.BAD_LOGIN, false);
	}
	
	public Customer getCustomerByEmail(String email) {
		return  customerDaoStub.getResultCustomQuery("getCustomerByEmail", 
				Collections.singletonMap("email", email)) ;
	}

}
