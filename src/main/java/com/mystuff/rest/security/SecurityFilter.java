package com.mystuff.rest.security;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.StringTokenizer;

import javax.annotation.Priority;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;

import org.apache.commons.lang3.StringUtils;
import org.jboss.logging.Logger;
import org.jboss.resteasy.util.Base64;

import com.mystuff.obj.SecurityModel;
import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.service.SecurityService;
import com.mystuff.util.AppConstants;
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

		MultivaluedMap<String, String> val = requestContext.getHeaders() ; 
		System.err.println(val);
		// Fetch authorization header
		String authorizationHeader = requestContext.getHeaderString(AppConstants.AUTHORIZATION_PROPERTY);

		// If no authorization information present; block access
		if (authorizationHeader == null || StringUtils.isEmpty(authorizationHeader)) {
			return false;
		}

		// Get encoded username and password
		final String encodedUserPassword = StringUtils.substringAfter(authorizationHeader,AppConstants.AUTHENTICATION_SCHEME);

		// Decode username and password
		String usernameAndPassword = null;
		try {
			usernameAndPassword = new String(Base64.decode(encodedUserPassword));
		} catch (Exception e) {
			return false;
		}

		// Split username and password tokens
		final StringTokenizer tokenizer = new StringTokenizer(usernameAndPassword, ":");
		final String username = tokenizer.nextToken();
		final String password = tokenizer.nextToken();

		try {
			SecurityService securityService = (SecurityService) new InitialContext().lookup("java:global/mystuff/SecurityService");
			WebResponse webResponse = securityService.login(username, password);
			if (webResponse.isSuccesfullOpt()) {
				//Check user Role
				CustomerDTO customerDto = (CustomerDTO) webResponse.getModelDtoObject();
				final AppSecurityContext securityContext = new AppSecurityContext(Arrays.asList(rolesAllowed.value()) , customerDto);
				if (securityContext.isUserAllow()){
					requestContext.setSecurityContext(securityContext);
					return true ;
				}
			}
		} catch (NamingException e) {
			e.printStackTrace();
		}

		logger.error("Unauthorized user is trying to access secured resource: " + String.format("User name {%s}, Requested Resource {%s} ", username, method.getName()));
		return false;
	}
}
