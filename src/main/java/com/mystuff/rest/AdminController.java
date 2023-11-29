package com.mystuff.rest;

import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.service.AdminService;
import com.mystuff.util.AppConstants;
import com.mystuff.util.MyStuffException;

/**
 * A collection of rest API's endpoint for the admin methods
 * 
 * @author The Hoff
 *
 */
@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({ "ADMIN" })
public class AdminController {

	@EJB
	private AdminService adminService;

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/createProduct")
	public Response createProduct(ProductDTO productDto, @Context HttpServletRequest request) {
		try {
			productDto = adminService.createProduct(productDto);
			return Response.status(201).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, productDto)).build();
		} catch (Exception e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCutsomers")
	public Response getAllCutsomers(@Context HttpServletRequest request) {
		List<CustomerDTO> customers = adminService.getAlCustomers();
		return Response.status(200).entity(customers).build();
	}

	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removeCustomer/")
	public Response removeCustomer(int customerId, @Context HttpServletRequest request) {
		try {
			List<CustomerDTO> customers = adminService.removeCustomer(customerId);
			return Response.status(200).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, customers)).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCustomerById}")
	public Response getCustomerById(int customerId, @Context HttpServletRequest request)  {
		CustomerDTO customer;
		try {
			customer = adminService.getCustomerById(customerId);
			return Response.status(200).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, customer)).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}

}
