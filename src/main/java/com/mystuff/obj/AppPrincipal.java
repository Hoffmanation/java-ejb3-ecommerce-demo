package com.mystuff.obj;

import java.security.Principal;

import com.mystuff.obj.dto.CustomerDTO;

/**
 * Customer implementation of the java.security.Principal 
 */
public class AppPrincipal implements Principal{

	public final CustomerDTO customerDto;
	
	
	public AppPrincipal(CustomerDTO customerDto) {
		this.customerDto = customerDto;
	}

	@Override
	public String getName() {
		return this.customerDto.getEmail();
	}

	public CustomerDTO getUser() {
		return this. customerDto ;
	}
	
	public int getPrincipalId() {
		return this.customerDto.getCustomerId();
	}
}
