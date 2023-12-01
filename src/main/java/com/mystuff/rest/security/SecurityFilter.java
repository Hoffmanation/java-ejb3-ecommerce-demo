package com.mystuff.rest.security;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.reflect.Method;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.InitialContext;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.mystuff.entity.Customer;
import com.mystuff.obj.LoginWebModel;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.service.SecurityService;
import com.mystuff.util.AppConstants;
import com.mystuff.util.Utilities;
/**
 * Main SecurityFilter, Will validate and Authorized user Role
 * This application uses Authorization Basic token header to Authenticate users who trying to get secured resource
 */
@Provider
@Priority(1)
public class SecurityFilter implements ContainerRequestFilter {
	
	private static final Logger logger = Logger.getLogger(SecurityFilter.class);

	@Context
	private UriInfo uriInfo;

	@Context
	private ResourceInfo resourceInfo;

	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = resourceInfo.getResourceMethod();
		RolesAllowed rolesAllowed = method.getAnnotation(RolesAllowed.class);
		if (rolesAllowed == null) {
			Class<?> clazz = resourceInfo.getResourceClass();
			rolesAllowed = clazz.getAnnotation(RolesAllowed.class);
		}

		if (!userIsAuthorized(requestContext, rolesAllowed, method)) {
			requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED)
					.entity(new WebResponse(AppConstants.NOT_AUTHORIZED, false,AppConstants.PLEASE_LOG_IN)).build());
		}

	}

	/**
	 * Validate if user is Authorized or not to get a hold of the requested resource
	 * @param requestContext
	 * @param rolesAllowed
	 * @param method
	 * @return {@link Boolean} 
	 */
	private boolean userIsAuthorized(ContainerRequestContext requestContext, RolesAllowed rolesAllowed, Method method) {
		// Access allowed for all
		if (method.isAnnotationPresent(PermitAll.class)) {
			return true;
		}

		// Fetch authorization header
		String authorizationHeader = requestContext.getHeaderString(AppConstants.AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorizationHeader == null || StringUtils.isEmpty(authorizationHeader) || authorizationHeader.contains("undefined")) {
			return false;
		}

		//In case its a Google+ Login 
		// Get encoded google login credentials
		final String encodedGooleCredentials = StringUtils.substringAfter(authorizationHeader,AppConstants.BEARER);
		if (!StringUtils.isEmpty(encodedGooleCredentials)) {
			return handleGoogleUser(encodedGooleCredentials.trim(), requestContext, rolesAllowed);
		}
		
		// Get encoded username and password
		final String encodedUserPassword = StringUtils.substringAfter(authorizationHeader,AppConstants.BASIC);

		// Decode username and password
		String usernameAndPassword = null;
		try {
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));
		} catch (Exception e) {
			return false;
		}

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String email = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		try {
			SecurityService securityService = (SecurityService) new InitialContext().lookup("java:global/mystuff/SecurityService");
			Customer customer = securityService.getCustomerByEmail(email) ;
			String decryptedPassword = customer!=null ?  Utilities.decrypt(customer.getPassword(), password, email) : StringUtils.EMPTY ;
			
			if (decryptedPassword.equals(password)) {
				CustomerDTO customerDto = Utilities.convertToOrFromDto(customer, CustomerDTO.class) ;
				final AppSecurityContext securityContext = new AppSecurityContext(Arrays.asList(rolesAllowed.value()) , customerDto);
				if (securityContext.isUserAllow()){
					requestContext.setSecurityContext(securityContext);
					return true ;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		logger.error("Unauthorized user is trying to access secured resource: " + String.format("User name {%s}, Requested Resource {%s} ", email, method.getName()));
		return false;
	}

	private boolean handleGoogleUser(String encodedGooleCredentials,ContainerRequestContext requestContext,RolesAllowed rolesAllowed) {
		try {
			DecodedJWT  jwt = JWT.decode(encodedGooleCredentials) ;
			String email = jwt.getClaim("email").asString() ;
			String firstName =  jwt.getClaim("given_name").asString() ;
			String surName =  jwt.getClaim("family_name").asString() ;
			String password =  jwt.getClaim("sub").asString() ;
			String aud =  jwt.getClaim("aud").asString() ;
			
			boolean IsVarified = this.varifiedGoogleToken(encodedGooleCredentials, aud, email) ;
			if (!IsVarified) {
				return false ;
			}
			
			//Check If its first time login, if so - execute SignUp
			SecurityService securityService = (SecurityService) new InitialContext().lookup("java:global/mystuff/SecurityService");
			Customer customer = securityService.getCustomerByEmail(email) ;
			
			//User Already have account
			if (customer!=null) {
				int mid = password.length() / 2; 
				password = password.substring(0, mid) ;
				String decryptedPassword = customer!=null ?  Utilities.decrypt(customer.getPassword(), password, email) : StringUtils.EMPTY ;
				if (decryptedPassword.equals(password)) {
					CustomerDTO customerDto = Utilities.convertToOrFromDto(customer, CustomerDTO.class) ;
					final AppSecurityContext securityContext = new AppSecurityContext(Arrays.asList(rolesAllowed.value()) , customerDto);
					if (securityContext.isUserAllow()){
						requestContext.setSecurityContext(securityContext);
						String loginWebModel = Utilities.convertToJson(new LoginWebModel(email, password)) ;
						requestContext.setEntityStream(new ByteArrayInputStream(loginWebModel.getBytes())) ;
						return true ;
					}
				}
			}
			
			//Need to signup user
			else  {
				//Google password (User PK at google) too long so cut in half
				int mid = password.length() / 2; 
				password = password.substring(0, mid) ;
				WebResponse signUpResponse = securityService.signUp(new SignupWebModel(firstName, surName, password, password, email));
				
				if (signUpResponse.isSuccesfullOpt()) {
					CustomerDTO customerDto = (CustomerDTO) signUpResponse.getModelDtoObject() ;
					final AppSecurityContext securityContext = new AppSecurityContext(Arrays.asList(rolesAllowed.value()) , customerDto);
					if (securityContext.isUserAllow()){
						requestContext.setSecurityContext(securityContext);
						String loginWebModel = Utilities.convertToJson(new LoginWebModel(email, password)) ;
						requestContext.setEntityStream(new ByteArrayInputStream(loginWebModel.getBytes())) ;
						return true ;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	
	private boolean varifiedGoogleToken(String jwt, String clientId,String email) {
		try {
			GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(new NetHttpTransport(),
					JacksonFactory.getDefaultInstance()).setAudience(Collections.singletonList(clientId)).build();

			GoogleIdToken idToken = verifier.verify(jwt);

			if (idToken == null) {
				return false ;
			}
			return idToken.getPayload().getEmail().equals(email);
		} catch (Exception e) {
			return false;
		}
	}
}
