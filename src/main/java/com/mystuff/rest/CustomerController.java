package com.mystuff.rest;

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
import javax.ws.rs.core.SecurityContext;
import org.jboss.logging.Logger;
import com.mystuff.obj.LoginWebModel;
import com.mystuff.obj.ProType;
import com.mystuff.obj.SignupWebModel;
import com.mystuff.obj.WebResponse;
import com.mystuff.obj.dto.OrderDTO;
import com.mystuff.obj.dto.ProductDTO;
import com.mystuff.obj.dto.WishlistDTO;
import com.mystuff.service.CustomerService;
import com.mystuff.service.SecurityService;
import com.mystuff.util.AppConstants;
import com.mystuff.util.MyStuffException;
import com.mystuff.util.Utilities;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed({"CUSTOMER", "ADMIN"})
public class CustomerController {
	private static final Logger logger= Logger.getLogger(CustomerController.class);

	@EJB
	private SecurityService securityService;

	@EJB
	private CustomerService customerService;
	
	/*
	 * Customer Login/SignUp
	 */

	@POST
	@Path("/signUp")
	@Produces(MediaType.APPLICATION_JSON)
	@PermitAll
	public Response signUp(@NotNull SignupWebModel signupModel, @Context HttpServletRequest request,@Context HttpServletResponse response) {
		WebResponse webResponse = securityService.signUp(signupModel);
		if (webResponse.isSuccesfullOpt()) {
			this.login(new LoginWebModel(signupModel.getEmail(), signupModel.getPassword()), request);
			return Response.status(201).entity(webResponse).build();
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
		return Response.status(500).entity(webResponse).build();
	}
	
	/*
	 * ProductsAPI
	 */
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
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
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

	/*
	 * Order API
	 */
	@POST
	@Path("/getAllCustomerOrdersById")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getAllCustomerOrdersById(@Context SecurityContext securityContext) {
		int customerId = Utilities.getLogedInCustomerId(securityContext) ;
		List<OrderDTO> orders = customerService.getAllCustomerOrdersById(customerId) ;
		return Response.status(200).entity(orders).build();
	}

	@POST
	@Path("/getOrderByOrderId")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getOrderByOrderId(Integer orderId) {
		OrderDTO order = customerService.getCustomerOrdersById(orderId) ;
		return Response.status(200).entity(order).build();
	}
	
	@POST
	@Path("/checkOut")
	@Produces(MediaType.APPLICATION_JSON)
	public Response chackOut(@Context HttpServletRequest request, @Context SecurityContext securityContext)  {
		try {
			int customerId = Utilities.getLogedInCustomerId(securityContext) ;
			OrderDTO orderDTO = customerService.chackOut(customerId);
			return Response.status(200).entity(new WebResponse(AppConstants.NEW_ORDER,true, orderDTO)).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}

	
	/*
	 * Wishlist API
	 */
	@POST
	@Path("/addToCart/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response addToCart(@PathParam("productId") int productId, @Context SecurityContext securityContext) {
		try {
			int customerId = Utilities.getLogedInCustomerId(securityContext) ;
			WishlistDTO WishListDto = customerService.addToWishlist(productId, customerId);
			return Response.status(201).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, WishListDto)).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}
	
	@POST
	@Path("/getCart")
	@Produces(MediaType.APPLICATION_JSON)
	public Response getCart(@Context SecurityContext securityContext)  {
		try {
			int customerId = Utilities.getLogedInCustomerId(securityContext) ;
			WishlistDTO wishListDto = customerService.getWishlistDTO(customerId);
			return Response.status(200).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, wishListDto)).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}


	@POST
	@Path("/removeFromCart/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Response removeFromCart(@PathParam("productId") int productId, @Context SecurityContext securityContext) {
		try {
			int customerId = Utilities.getLogedInCustomerId(securityContext) ;
			WishlistDTO wishListDto = customerService.removeFromWishlist(productId,customerId);
			return Response.status(200).entity(new WebResponse(AppConstants.OPT_SUCCESSFUL, true, wishListDto )).build();
		} catch (MyStuffException e) {
			return Response.status(500).entity(new WebResponse(e.getMessage(), false)).build();
		}
	}






}
