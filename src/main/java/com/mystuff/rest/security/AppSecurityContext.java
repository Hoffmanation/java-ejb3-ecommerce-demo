package com.mystuff.rest.security;

import java.security.Principal;
import java.util.List;

import javax.ws.rs.core.SecurityContext;

import com.mystuff.obj.AppPrincipal;
import com.mystuff.obj.SecurityModel;
import com.mystuff.obj.dto.CustomerDTO;

/**
 * Customer implementation of the JAX RS SecurityContext Store user after
 * authentication in a SecurityContext object shared by the WEB container
 */
public class AppSecurityContext implements SecurityContext {

	// List of Roles that exists on the requested resource
	// Corresponded to @RolesAllowed("THE-ROLE")
	private final List<String> requiredRoles;
	// User authentication info to be validate
	private final CustomerDTO securityModeluser;

	public AppSecurityContext(List<String> requiredRoles, CustomerDTO securityModeluser) {
		this.requiredRoles = requiredRoles;
		this.securityModeluser = securityModeluser;
	}

	@Override
	public Principal getUserPrincipal() {
		return new AppPrincipal(securityModeluser) ;
	}

	@Override
	public boolean isUserInRole(String role) {
		return securityModeluser != null && securityModeluser.getRole().name().equals(role);
	}

	@Override
	public boolean isSecure() {
		return true;
	}

	@Override
	public String getAuthenticationScheme() {
		return "Basic";
	}

	// Validate user contain desired role
	public boolean isUserAllow() {
		return requiredRoles.stream().anyMatch(requiredRole -> requiredRole.equals(securityModeluser.getRole().name()));
	}

	public int getPrincipalId() {
		return this.securityModeluser.getCustomerId();
	}

}