package mystuff.web;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.ejb.AccessTimeout;
import javax.ejb.EJB;
import javax.ejb.Timeout;
import javax.naming.InitialContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import myStuff.DaoBean.ejb.ProType;
import myStuff.Pojo.Jpa.CustomMessage;
import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.LoginModel;
import myStuff.Pojo.Jpa.Message;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.Pojo.Jpa.SignupModel;
import myStuff.dao.ejb.CartDao;
import myStuff.dao.ejb.CustomerDao;
import myStuff.service.util.MyStuffException;

@Path("/customer")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

	@EJB
	private CustomerDao custf;
	private static Customer customer = null;

	@GET
	@Path("/customerSession")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response customerSession(@Context HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		try {
			return Response.status(200).entity(session.getAttribute("custf")).build();
		} catch (Exception e) {
			return Response.status(404).entity(new CustomMessage("You'r not looged in", false, null)).build();
		}
	}

	@POST
	@Path("/login")
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	public Response login(LoginModel loginDetails, @Context HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(true);
		if (customer == null) {
			if (custf.login(loginDetails.getEmail(), loginDetails.getPassword())) {
				customer = custf.getCustomerByEmail(loginDetails.getEmail());
				session.setAttribute("custf", customer);
				return Response.status(200)
						.entity(new CustomMessage("You have successfully logged in", true, customer.getName())).build();
			} else {
				return Response.status(200)
						.entity(new CustomMessage("Invalid password or username, Please try again", false, null))
						.build();
			}
		}
		return Response.status(200).entity(new CustomMessage("You already looged in", false, customer.getName()))
				.build();

	}

	@GET
	@Path("/getAllProducts")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getAllProducts(@Context HttpServletRequest request) throws Exception {
		return custf.getAllProducts().toArray(new Product[0]);

	}

	@POST
	@Path("/signUp")
	@Produces(MediaType.APPLICATION_JSON)
	public Response signUp(SignupModel signupModel, @Context HttpServletRequest request,
			@Context HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession(true);
		session.getAttribute("custf");
		if (signupModel.getPassword().equals(signupModel.getSecondPassword())) {
			String msg = custf.signUp(signupModel.getName(), signupModel.getF_name(), signupModel.getPassword(),
					signupModel.getEmail());
			login(new LoginModel(signupModel.getEmail(), signupModel.getPassword()), request);
			return Response.status(200).entity(new CustomMessage(msg, true, customer.getName())).build();
		}
		return Response.status(404).entity(new CustomMessage("*Looks like these posswords don’t match", false, null))
				.build();
	}

	@GET
	@Path("/searchProductByPrice/{min_price}/{max_price}/")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] searchProductByPrice(@PathParam("min_price") double min_price,
			@PathParam("max_price") double max_price, @Context HttpServletRequest request) throws SQLException {
		return custf.searchProductByPrice(min_price, max_price).toArray(new Product[0]);
	}

	@PUT
	@Path("/updateCustomer/{name}/{f_name}/{password}/{email}/{phone}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message updateCustomer(@PathParam("name") String name, @PathParam("f_name") String f_name,
			@PathParam("password") String password, @PathParam("email") String email, @PathParam("phone") String phone,
			@Context HttpServletRequest request) throws MyStuffException {
		HttpSession session = request.getSession(false);
		session.getAttribute("custf");
		if (custf.updateCustomer(customer, password, email)) {
			return new Message("Your acount detail has been updated successfully! ");
		}
		return new Message("We couldn't be able to update your account, please try again later!");
	}

	@GET
	@Path("/getAllProductByPriceEx")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getAllProductByPriceEx(@Context HttpServletRequest request) {
		return custf.getAllProductByPriceEx().toArray(new Product[0]);

	}

	@GET
	@Path("/getAllProductByPriceChe")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getAllProductByPriceChe(@Context HttpServletRequest request) {
		return custf.getAllProductByPriceChe().toArray(new Product[0]);
	}

	@GET
	@Path("/getProductArrayById/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getProductArrayById(@PathParam("id") int id, @Context HttpServletRequest request) {
		return custf.getProductArrayById(id).toArray(new Product[0]);

	}

	@GET
	@Path("/getAllProductsByType/{Type}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getAllProductsByType(@PathParam("Type") String type, @Context HttpServletRequest request) {
		return custf.getAllProductsByType(ProType.valueOf(type.toUpperCase())).toArray(new Product[0]);
	}

	@GET
	@Path("/getAllCustomerOrdersById")
	@Produces(MediaType.APPLICATION_JSON)
	public Order[] getCustomerOrderById(@Context HttpServletRequest request) {
		Order newOrder;
		List<Order> newOne = new ArrayList<>();
		HttpSession session = request.getSession(false);
		session.getAttribute("custf");
		try {
			List<Order> myOrders = custf.getCustomerOrderById(customer.getId());
			for (Order order : myOrders) {
				newOrder = new Order(order.getOrderId(), order.getDateStamp(), order.getPayment(),
						order.getProducts().toString());
				newOne.add(newOrder);
				break;
			}
			if (!myOrders.isEmpty()) {
				return newOne.toArray(new Order[0]);
			} else {
				new Message("You don't have any recent orders");
			}
		} catch (Exception e) {
			new Message("You don't have any recent orders");
		}
		return null;
	}

	@GET
	@Path("/getCartsize")
	@Produces(MediaType.APPLICATION_JSON)
	public int getCartsize(@Context HttpServletRequest request) throws Exception {
		CartDao cart = getStubFromSession(request);
		return cart.getCartsize();
	}

	@GET
	@Path("/addToCart/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public boolean addToCart(@PathParam("productId") int productId, @Context HttpServletRequest request)
			throws Exception {
		CartDao cart = getStubFromSession(request);
		if (cart.addToCart(custf.getProductById(productId))) {
			return true;
		}
		return false;
	}

	@GET
	@Path("/getCart")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] getCart(@Context HttpServletRequest request) throws Exception {
		CartDao cart = getStubFromSession(request);
		return cart.getCart().toArray(new Product[0]);
	}

	@GET
	@Path("/getCartSum")
	@Produces(MediaType.APPLICATION_JSON)
	public Message getCartSum(@Context HttpServletRequest request) throws Exception {
		CartDao cart = getStubFromSession(request);
		return new Message("" + cart.getCartSum());
	}

	@GET
	@Path("/removeFromCart/{productId}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message removeFromCart(@PathParam("productId") int productId, @Context HttpServletRequest request)
			throws Exception {
		CartDao cart = getStubFromSession(request);
		cart.removeFromCart(custf.getProductById(productId));
		return new Message("Product #" + productId + " was removed from cart ");
	}

	@GET
	@Path("/checkOut")
	@Produces(MediaType.APPLICATION_JSON)
	public Message chackOut(@Context HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(false);
		session.getAttribute("custf");
		CartDao cart = getStubFromSession(request);
		if (cart.getCartsize() != 0) {
			if (customer != null) {
				cart.checkOut(customer);
				return new Message("Your order has been processed, Thank you for shopping at Mystuff");
			}
			return new Message("Plaese login first !");
		}
		return new Message("Your cart is empty!");

	}

	@PUT
	@Path("/ClearCart")
	@Produces(MediaType.APPLICATION_JSON)
	public void ClearCart(@Context HttpServletRequest request) throws Exception {
		CartDao cart = getStubFromSession(request);
		cart.ClearCart();
	}

	@GET
	@Path("/searchProductByName/{searchPro}")
	@Produces(MediaType.APPLICATION_JSON)
	public Product[] searchProductByName(@PathParam("searchPro") String searchPro, @Context HttpServletRequest request)
			throws Exception {
		return custf.searchProductByName(searchPro.toUpperCase()).toArray(new Product[0]);
	}

	@GET
	@Path("/logout")
	public Response logout(@Context HttpServletRequest request) throws Exception {
		try {
			HttpSession session = request.getSession(false);
			session.invalidate();
			request = null;
			customer = null;
			return Response.status(200).entity(new CustomMessage("You are secsussfully loged out", true,
					"Goodbye " + customer.getName() + ", and thank you for shoping at Mystuff©")).build();
		} catch (Exception e) {
			return Response.status(200).entity(new CustomMessage("You are not logged in", false, null)).build();
		}
	}

	private CartDao getStubFromSession(HttpServletRequest request) throws Exception {
		HttpSession session = request.getSession(true);
		CartDao cart = (CartDao) session.getAttribute("cart");
		if (cart == null) {
			cart = (CartDao) new InitialContext()
					.lookup("java:global/mystuff.app/mystuff.ejb/CartDaoBean!myStuff.dao.ejb.CartDao");
			session.setAttribute("cart", cart);
		}

		return cart;
	}

}
