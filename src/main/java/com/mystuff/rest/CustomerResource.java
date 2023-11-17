//package com.mystuff.rest;
//
//import javax.ejb.EJB;
//import javax.naming.InitialContext;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import javax.servlet.http.HttpSession;
//import javax.validation.constraints.NotNull;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.GET;
//import javax.ws.rs.POST;
//import javax.ws.rs.PUT;
//import javax.ws.rs.Path;
//import javax.ws.rs.PathParam;
//import javax.ws.rs.Produces;
//import javax.ws.rs.core.Context;
//import javax.ws.rs.core.MediaType;
//import javax.ws.rs.core.Response;
//
//import org.apache.commons.lang3.StringUtils;
//
//import com.mystuff.dao.CartDao;
//import com.mystuff.dao.CustomerDao;
//import com.mystuff.entity.Customer;
//import com.mystuff.entity.Order;
//import com.mystuff.entity.Product;
//import com.mystuff.obj.CustomMessage;
//import com.mystuff.obj.LoginModel;
//import com.mystuff.obj.Message;
//import com.mystuff.obj.ProType;
//import com.mystuff.obj.SignupModel;
//import com.mystuff.util.AppConstants;
//import com.mystuff.util.MyStuffException;
//import com.mystuff.util.Utilities;
//
//@Path("/customer")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class CustomerResource {
//
//	@EJB
//	private CustomerDao custf;
//	private static final String CUSTOMER_SESSION_ATTR = "cust";
//
//	@GET
//	@Path("/customerSession")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response customerSession(@Context HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(false);
//		if (null != session) {
//			return Response.status(200).entity(session.getAttribute(CUSTOMER_SESSION_ATTR)).build();
//		} else {
//			return Response.status(404).entity(new CustomMessage("*You'r not looged in", false, "SessionIsDead"))
//					.build();
//		}
//	}
//
//	@POST
//	@Path("/signUp")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response signUp(@NotNull SignupModel signupModel, @Context HttpServletRequest request,
//			@Context HttpServletResponse response) throws Exception {
//
//		String signUpError = Utilities.getSignUpErrors(signupModel);
//		if (StringUtils.isNotEmpty(signUpError)) {
//			return Response.status(200).entity(new CustomMessage(signUpError, false, null)).build();
//		}
//
//		boolean signUpResult = custf.signUp(signupModel.getName(), signupModel.getF_name(),
//				signupModel.getPassword(), signupModel.getEmail());
//
//		if (signUpResult) {
//			login(new LoginModel(signupModel.getEmail(), signupModel.getPassword()), request);
//			return Response.status(200)
//					.entity(new CustomMessage(AppConstants.GOOD_SUGNUP, true, signupModel.getName())).build();
//		} else {
//			return Response.status(200)
//					.entity(new CustomMessage(AppConstants.BAD_SUGNUP, false, signupModel.getName())).build();
//		}
//
//	}
//
//	@POST
//	@Path("/login")
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	public Response login(LoginModel loginDetails, @Context HttpServletRequest request) throws Exception {
//		if (custf.login(loginDetails.getEmail(), loginDetails.getPassword())) {
//			HttpSession session = request.getSession(true);
//			Customer customer = custf.getCustomerByEmail(loginDetails.getEmail());
//			session.setAttribute(CUSTOMER_SESSION_ATTR, customer);
//			session.setMaxInactiveInterval(20 * 60);
//			return Response.status(200).entity(new CustomMessage(AppConstants.GOOD_LOGIN, true, customer.getName()))
//					.build();
//		} else {
//			return Response.status(200).entity(new CustomMessage(AppConstants.BAD_LOGIN, false, null)).build();
//		}
//	}
//
//	@GET
//	@Path("/getAllProducts")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] getAllProducts(@Context HttpServletRequest request) throws Exception {
//		return custf.getAllProducts().toArray(new Product[0]);
//
//	}
//
//	@GET
//	@Path("/searchProductByPrice/{min_price}/{max_price}/")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] searchProductByPrice(@PathParam("min_price") double min_price,
//			@PathParam("max_price") double max_price, @Context HttpServletRequest request) {
//		return custf.searchProductByPrice(min_price, max_price).toArray(new Product[0]);
//	}
//
//	@PUT
//	@Path("/updateCustomer/{name}/{f_name}/{password}/{email}/{phone}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Message updateCustomer(@PathParam("name") String name, @PathParam("f_name") String f_name,
//			@PathParam("password") String password, @PathParam("email") String email, @PathParam("phone") String phone,
//			@Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		Customer customer = (Customer) session.getAttribute(CUSTOMER_SESSION_ATTR);
//		if (custf.updateCustomer(customer, password, email)) {
//			return new Message("Your acount detail has been updated successfully! ");
//		}
//		return new Message("We couldn't be able to update your account, please try again later!");
//	}
//
//	@GET
//	@Path("/getAllProductByPriceEx")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] getAllProductByPriceEx(@Context HttpServletRequest request) {
//		return custf.getAllProductByPriceEx().toArray(new Product[0]);
//
//	}
//
//	@GET
//	@Path("/getAllProductByPriceChe")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] getAllProductByPriceChe(@Context HttpServletRequest request) {
//		return custf.getAllProductByPriceChe().toArray(new Product[0]);
//	}
//
//	@GET
//	@Path("/getProductById/{id}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product getProductById(@PathParam("id") int id, @Context HttpServletRequest request) {
//		return custf.getProductById(id);
//
//	}
//
//	@GET
//	@Path("/getAllProductsByType/{Type}")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Product[] getAllProductsByType(@PathParam("Type") String type, @Context HttpServletRequest request) {
//		return custf.getAllProductsByType(ProType.valueOf(type.toUpperCase())).toArray(new Product[0]);
//	}
//
//	@GET
//	@Path("/getAllCustomerOrdersById")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Order[] getCustomerOrderById(@Context HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		Customer customer = (Customer) session.getAttribute(CUSTOMER_SESSION_ATTR);
//		return custf.getCustomerOrderById(customer.getId()).toArray(new Order[0]);
//	}
//
//	@POST
//	@Path("/getOrderByOrderId")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Order[] getOrderByOrderId(Integer orderId, @Context HttpServletRequest request) {
//		HttpSession session = request.getSession(false);
//		session.getAttribute(CUSTOMER_SESSION_ATTR);
//		return custf.getOrderByOrderId(orderId).toArray(new Order[0]);
//	}
//
//	@GET
//	@Path("/getCartsize")
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
//
//
//	@GET
//	@Path("/logout")
//	public Response logout(@Context HttpServletRequest request) throws Exception {
//		try {
//			HttpSession session = request.getSession(false);
//			Customer customer = (Customer) session.getAttribute(CUSTOMER_SESSION_ATTR);
//			session.invalidate();
//			request = null;
//			return Response.status(200).entity(new CustomMessage("You are secsussfully loged out", true,
//					"Goodbye " + customer.getName() + ", and thank you for shoping at Mystuff")).build();
//		} catch (Exception e) {
//			return Response.status(200).entity(new CustomMessage("You are not logged in", false, null)).build();
//		}
//	}
//
//	private CartDao getStubFromSession(HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(true);
//		CartDao cart = (CartDao) session.getAttribute("cart");
//		if (cart == null) {
//			cart = (CartDao) new InitialContext()
//					.lookup("java:global/mystuff/CartDaoBean!myStuff.dao.ejb.CartDao");
//			session.setAttribute("cart", cart);
//		}
//
//		return cart;
//	}
//
//}
