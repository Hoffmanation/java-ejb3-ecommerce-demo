package com.mystuff.service;

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
		Customer customerWithSameEmail = customerDaoStub.getResultCustomQuery("getCustomerByEmail", 
				Collections.singletonMap("email", signupModel.getEmail())) ;

		String signUpError = Utilities.getSignUpErrors(signupModel,customerWithSameEmail);
		if (StringUtils.isNotEmpty(signUpError)) {
			logger.error("Failde to signUp, Error message: " + signUpError);
			return new WebResponse(AppConstants.BAD_SIGNUP, false,signUpError) ;
		}

		Customer newCustomer = new Customer(signupModel.getFirstName(), signupModel.getSurName(), 
				signupModel.getPassword(), signupModel.getEmail(),UserRole.CUSTOMER);
		customerDaoStub.create(newCustomer); 
		return new WebResponse(AppConstants.GOOD_SIGNUP, true) ;
	}

	public WebResponse login(String email, String password) {
		Map<String, Object> parameters = new HashMap<>();
		parameters.put("email", email);
		parameters.put("password", password);
		Customer loginCustomer =  customerDaoStub.getResultCustomQuery("getCustomerByEmailAndPassword", parameters) ;
		if (loginCustomer!=null) {
			CustomerDTO customerDto = Utilities.convertToOrFromDto(loginCustomer, CustomerDTO.class) ;
			return new WebResponse(AppConstants.GOOD_LOGIN, true, customerDto) ;
		}
		return new WebResponse(AppConstants.BAD_LOGIN, false) ;
	}

}
