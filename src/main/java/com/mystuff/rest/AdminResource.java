//package com.mystuff.rest;
//
//import java.util.Date;
//
//import javax.ejb.EJB;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpSession;
//import javax.ws.rs.Consumes;
//import javax.ws.rs.DELETE;
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
//import com.mystuff.dao.IAdminDao;
//import com.mystuff.entity.Customer;
//import com.mystuff.entity.Order;
//import com.mystuff.entity.Product;
//import com.mystuff.obj.CustomMessage;
//import com.mystuff.obj.LoginModel;
//import com.mystuff.obj.ProductDetail;
//import com.mystuff.util.MyStuffException;
///**
// * A collection of rest API's endpoint for the admin methods
// * @author The Hoff
// *
// */
//@Path("/admin")
//@Produces(MediaType.APPLICATION_JSON)
//@Consumes(MediaType.APPLICATION_JSON)
//public class AdminResource {
//
//	@EJB
//	private IAdminDao adminf;
//
//	@POST
//	@Path("/login")
//	@Produces(MediaType.APPLICATION_JSON)
//	public Response login(LoginModel loginModel, @Context HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(true);
//		if (adminf.login(loginModel.getEmail(), loginModel.getPassword())) {
//			session.setAttribute("admin", loginModel.getEmail());
//			return Response.status(200).entity(new CustomMessage("Welcome Administrator.", true, loginModel.getEmail()))
//					.build();
//		} else {
//			session.invalidate();
//			return Response.status(200).entity(new CustomMessage("*Invalid password or username, Please try again", false, null)).build();
//		}
//
//	}
//
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Consumes(MediaType.APPLICATION_JSON)
//	@Path("/createProduct")
//	public Response createProduct(ProductDetail product, @Context HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		if (adminf.createProduct(new Product(product.getName(), product.getPrice(), product.getType(),
//				product.getDescription(), product.getImagePath(), product.getStock(), 0))) {
//			return Response.status(200)
//					.entity(new CustomMessage("A new product added succssesfully", true, "" + product.getName()))
//					.build();
//		}
//		return Response.status(200).entity(new CustomMessage("Product was not added!", false, "" + product.getName()))
//				.build();
//	}
//
//	/*
//	 * @PUT
//	 * 
//	 * @Produces(MediaType.APPLICATION_JSON)
//	 * 
//	 * @Path("/updateProduct") public Response updateProduct(int productId, String
//	 * name, String description, String imagePath, int price, String protype, int
//	 * quantity, @Context HttpServletRequest request) throws Exception { if
//	 * (adminf.updateProduct(productId, description, imagePath, price, quantity)) {
//	 * return Response.status(200).entity(new CustomMessage("Product #" + productId
//	 * + " was updated", true, "")) .build(); } return
//	 * Response.status(200).entity(new CustomMessage("Product #" + productId +
//	 * " was not updated", false, "")) .build(); }
//	 */
//
//	@DELETE
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/removeProduct")
//	public Response removeProduct(int productId, @Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		if (adminf.removeProduct(productId)) {
//			return Response.status(200)
//					.entity(new CustomMessage("Product # " + adminf.getProductById(productId).getId() + " was removed",
//							true, new Date().toGMTString()))
//					.build();
//		} else {
//			return Response.status(200)
//					.entity(new CustomMessage(
//							"Product # " + adminf.getProductById(productId).getId() + " was not removed!", false,
//							new Date().toGMTString()))
//					.build();
//		}
//
//	}
//
//	@POST
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getProductById")
//	public Response getProductById(int productId, @Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		return Response.status(200).entity(adminf.getProductById(productId)).build();
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getAllCutsomers")
//	public Response getAllCutsomers(@Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		return Response.status(200).entity(adminf.getAllCutsomers().toArray(new Customer[0])).build();
//
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getCustomerByName/{name}")
//	public Customer getCustomerByName(@PathParam("name") String name, @Context HttpServletRequest request)
//			throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		return adminf.getCustomerByName(name);
//	}
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/getOrderById/{id}")
//	public Order getOrderById(@PathParam("id") int id, @Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		return adminf.getOrderById(id);
//	}
//
//	@DELETE
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/removeCustomer/")
//	public Response removeCustomer(int customerId, @Context HttpServletRequest request) throws MyStuffException {
//		HttpSession session = request.getSession(false);
//		session.getAttribute("admin");
//		if (adminf.removeCustomer(customerId)) {
//			return Response.status(200).entity(new CustomMessage("Customer #" + customerId + " was deleted", true, ""))
//					.build();
//		} else {
//			return Response.status(200)
//					.entity(new CustomMessage("Customer #" + customerId + " was not deleted!", true, "")).build();
//		}
//	}
//
//	/*
//	 * @GET
//	 * 
//	 * @Produces(MediaType.APPLICATION_JSON)
//	 * 
//	 * @Path("/getCustomerById}") public Customer getCustomerById(int
//	 * customerId, @Context HttpServletRequest request) throws MyStuffException {
//	 * HttpSession session = request.getSession(false);
//	 * session.getAttribute("admin"); return adminf.getCustomerById(customerId); }
//	 */
//
//	@GET
//	@Produces(MediaType.APPLICATION_JSON)
//	@Path("/logout")
//	public Response logout(@Context HttpServletRequest request) throws Exception {
//		HttpSession session = request.getSession(false);
//		try {
//			session.invalidate();
//			request = null;
//			return Response.status(200).entity(new CustomMessage("Admin logout successfully", true, "")).build();
//		} catch (Exception e) {
//			return Response.status(200).entity(new CustomMessage("Admin is not logged in ! ", false, "")).build();
//		}
//	}
//
//}
