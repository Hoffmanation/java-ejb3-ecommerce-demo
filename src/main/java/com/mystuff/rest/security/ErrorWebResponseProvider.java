package com.mystuff.rest.security;

import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import com.mystuff.obj.WebResponse;
import com.mystuff.util.AppConstants;

/**
 * ExceptionMapper provider, will supply error response when an Unhandled 
 * Exception will be throw during invocation of any of the REST endpoint lifeCycle
 */
@Provider
public class ErrorWebResponseProvider implements ExceptionMapper<Exception>{


	@Override
	public Response toResponse(Exception e) {
		return 
				Response.serverError() // create response builder with Error code: 500
				.entity(new WebResponse(AppConstants.SERVER_ERROR,false,e.getMessage())) // create a JSON within the response
				.build(); // create the response using the builder
	}

}
