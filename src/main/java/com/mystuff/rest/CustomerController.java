package com.mystuff.rest;

import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.jboss.logging.Logger;

import com.mystuff.obj.LoginWebModel;
import com.mystuff.obj.ProType;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.CustomerDTO;
import com.mystuff.obj.dto.OrderDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.service.CustomerService;
import com.mystuff.service.SecurityService;
import com.mystuff.util.AppConstants;
import com.mystuff.util.MyStuffException;
import com.mystuff.util.Utilities;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"CUSTOMER"})
public class CustomerController {
	private static final Logger logger= Logger.getLogger(CustomerController.class);

	@EJB
	private SecurityService securityService;

	@EJB
	private CustomerService customerService;

	@POST
	@Path("/signUp")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response signUp(@NotNull SignupWebModel signupModel, @Context HttpServletRequest request,@Context HttpServletResponse response) {
		WebResponse webResponse = securityService.signUp(signupModel);
		if (webResponse.isSuccesfullOpt()) {
			this.login(new LoginWebModel(signupModel.getEmail(), signupModel.getPassword()), request);
			return Response.status(200).entity(webResponse).build();
		}
		return Response.status(500).entity(webResponse).build();
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response login(LoginWebModel loginDetails, @Context HttpServletRequest request) {
		WebResponse webResponse = securityService.login(loginDetails.getEmail(), loginDetails.getPassword());
		if (webResponse.isSuccesfullOpt()) {
			return Response.status(200).entity(webResponse).build();
		}
		return Response.status(401).entity(webResponse).build();
	}

	@GET
	@Path("/getAllProducts")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getAllProducts(@Context HttpServletRequest request) {
		List<ProductDTO> productDto = customerService.getAllProducts();
		return Response.status(200).entity(productDto).build();

	}

	@GET
	@Path("/searchProductByPrice/{min_price}/{max_price}/")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response searchProductByPriceRange(@PathParam("min_price") double minPrice,@PathParam("max_price") double maxPrice) {
		List<ProductDTO> productDto = customerService.searchProductByPriceRange(minPrice, maxPrice);
		return Response.status(200).entity(productDto).build();

	}

	@GET
	@Path("/getAllProductByPriceEx")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getAllProductByPriceEx(@Context HttpServletRequest request) {
		List<ProductDTO> productDto = customerService.getAllProductByPriceEx();
		return Response.status(200).entity(productDto).build();
	}

	@GET
	@Path("/getAllProductByPriceChe")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getAllProductByPriceChe(@Context HttpServletRequest request) {
		List<ProductDTO> productDto = customerService.getAllProductByPriceChe();
		return Response.status(200).entity(productDto).build();
	}

	@GET
	@Path("/getProductById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getProductById(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			ProductDTO productDto = customerService.getProductById(id);
			return Response.status(200).entity(productDto).build();
		} catch (MyStuffException e) {
			return Response.status(404).entity(new WebResponse(e.getMessage(), false)).build();
		}

	}

	@GET
	@Path("/getAllProductsByType/{Type}")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response getAllProductsByType(@PathParam("Type") String type, @Context HttpServletRequest request) {
		List<ProductDTO> productDto =  customerService.getAllProductsByType(ProType.valueOf(type.toUpperCase())) ;
		return Response.status(200).entity(productDto).build();
	}

	@GET
	@Path("/getAllCustomerOrdersById")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomerOrdersById(@Context HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		CustomerDTO logedinCustomer = (CustomerDTO) session.getAttribute(AppConstants.CUSTOMER_SESSION_ATTR);
		List<OrderDTO> orders = customerService.getAllCustomerOrdersById(logedinCustomer.getCustomerId()) ;
		return Response.status(200).entity(orders).build();
	}

	@POST
	@Path("/getOrderByOrderId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderByOrderId(Integer orderId, @Context HttpServletRequest request) {
		OrderDTO order = customerService.getCustomerOrdersById(orderId) ;
		return Response.status(200).entity(order).build();
	}

//	@GET
//	@Path("/getCartSize")
//	@Produces(MediaType.APPLICATION_JSON)
//	public int getCartsize(@Context HttpServletRequest request) throws Exception {
//		CartDao cart = getStubFromSession(request);
//		return cart.getCartsize();
//	}
//
//	@GET
//	@Path("/addToCart/{productId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public boolean addToCart(@PathParam("productId") int productId, @Context HttpServletRequest request)
//			throws Exception {
//		CartDao cart = getStubFromSession(request);
//		if (cart.addToCart(custf.getProductById(productId))) {
//			return true;
//		}
//		return false;
//	}
//
//	@GET
//	@Path("/getCart")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] getCart(@Context HttpServletRequest request) throws Exception {
//		CartDao cart = getStubFromSession(request);
//		return cart.getCart().toArray(new Product[0]);
//	}
//
//	@GET
//	@Path("/getCartSum")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message getCartSum(@Context HttpServletRequest request) throws Exception {
//		CartDao cart = getStubFromSession(request);
//		return new Message("" + cart.getCartSum());
//	}
//
//	@GET
//	@Path("/removeFromCart/{productId}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message removeFromCart(@PathParam("productId") int productId, @Context HttpServletRequest request)
//			throws Exception {
//		CartDao cart = getStubFromSession(request);
//		cart.removeFromCart(custf.getProductById(productId));
//		return new Message("Product #" + productId + " was removed from cart ");
//	}
//
//	@GET
//	@Path("/checkOut")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message chackOut(@Context HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(false);
//		Customer customer = (Customer) session.getAttribute(CUSTOMER_SESSION_ATTR);
//		CartDao cart = getStubFromSession(request);
//
//		String signUpError = Utilities.getChackOutErrors(customer, cart);
//		if (StringUtils.isNotEmpty(signUpError)) {
//			return new Message(signUpError);
//		}
//		cart.checkOut(customer);
//		return new Message("Your order has been processed, Thank you for shopping at Mystuff");
//	}
//
//	@PUT
//	@Path("/ClearCart")
//	@Produces(MediaType.APPLICATION_JSON)
//	public void ClearCart(@Context HttpServletRequest request) throws Exception {
//		CartDao cart = getStubFromSession(request);
//		cart.ClearCart();
//	}


}
