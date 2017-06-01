package mystuff.web;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.apache.commons.lang3.text.WordUtils;

import myStuff.DaoBean.ejb.ProType;
import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Customer_order;
import myStuff.Pojo.Jpa.Message;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.dao.ejb.AdminDao;

@Path("/admin")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AdminResource {

	@EJB
	private AdminDao adminf;
	HttpSession session;

	// working plus exception
	@GET
	@Path("/login/{name}/{password}")
	@Produces(MediaType.APPLICATION_JSON)
	public Message login(@PathParam("name") String name, @PathParam("password") String password,
			@Context HttpServletRequest request) throws Exception {
		try {
			if (adminf.login(name, password)) {
				session = request.getSession(true);
				session.setAttribute("adminf", adminf);
				return new Message("Logged in as administrator ");
			} else {
				adminf = null;
				session.invalidate();
			}
		} catch (Exception e) {
			return new Message("Invalid password or username, Please try again");
		}
		return null;

	}

	// working ? exception
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/createProduct/{name}/{description}/{imagePath}/{price}/{protype}/{quantity}")
	public Message createProduct(@PathParam("name") String name, @PathParam("description") String description,
			@PathParam("imagePath") String imagePath, @PathParam("price") int price,
			@PathParam("protype") String protype, @PathParam("quantity") int quantity,
			@Context HttpServletRequest request) throws Exception {
		ProType newType = ProType.valueOf(protype.toUpperCase());
		try {
			adminf.createProduct(name, description, imagePath, price, newType, quantity);
			return new Message("A new product was added to the site!");
		} catch (Exception e) {
			e.printStackTrace();
			return new Message("Server error! ");
		}
	}

	// working plus exception
	@PUT
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/updateProduct/{productId}/{name}/{description}/{imagePath}/{price}/{protype}/{quantity}")
	public Message updateProduct(@PathParam("productId") int productId, @PathParam("name") String name,
			@PathParam("description") String description, @PathParam("imagePath") String imagePath,
			@PathParam("price") int price, @PathParam("protype") String protype, @PathParam("quantity") int quantity,
			@Context HttpServletRequest request) throws Exception {
		try {
				ProType newType = ProType.valueOf(protype.toUpperCase());
				adminf.updateProduct(productId, description, imagePath, price, quantity);
				return new Message("Product #" + productId + ", " +name + "has been updated ");

		} catch (Exception e) {
			return new Message("Please insert all parameters accordingly !");
		}
	}

	// working plus exception
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removeProduct/{productId}")
	public Message removeProduct(@PathParam("productId") int productId, @Context HttpServletRequest request)
			throws Exception {
		try {
			Product tempProduct = adminf.getProductById(productId);
			if (tempProduct.getName() != null) {
				adminf.removeProduct(productId);
				return new Message(
						"Product #" + productId + ", " + tempProduct.getName() + " has been removed from the site");
			}
		} catch (Exception e) {
			return new Message("Product #" + productId + " douse't exist, Please insert a valid id number !");
		}
		return null;
	}



	// working ? exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getProductById/{id}")
	public Product getProductById(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			Product product = adminf.getProductById(id);
			if (product.getName() != null) {
				return product;
			}
		} catch (Exception e) {
			new Message("Product #" + id + " douse't exist, Please insert a valid id number !");
		}
		return null;
	}


	// working ? exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getAllCutsomers")
	public Customer[] getAllCutsomers(@Context HttpServletRequest request) {
		try {
			List<Customer> customers = adminf.getAllCutsomers();
			if (!customers.isEmpty()) {
				return customers.toArray(new Customer[0]);
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			new Message("There are no customers registered in Mystuff's systems right now ");
		}
		return null;
	}

	// working ? exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCustomerByName/{name}")
	public Customer getCustomerByName(@PathParam("name") String name, @Context HttpServletRequest request) {
		try {
			Customer customer = adminf.getCustomerByName(name);
			WordUtils.capitalize(customer.getName());
			return customer;
		} catch (Exception e) {
			new Message("No such customer registered in Mystuff systems !");
		}
		return null;
	}



	// working ? exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getOrderById/{id}")
	public Order getOrderById(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			Order order = adminf.getOrderById(id);
			if (order.getOrderId() != 0) {
				return order;
			}
		} catch (Exception e) {
			new Message("Order #" + id + " douse't exist, Please insert a valid id number !");
		}
		return null;
	}

	// working plus exception
	@DELETE
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/removeCustomer/{id}")
	public Message removeCustomer(@PathParam("id") int id, @Context HttpServletRequest request) {

		try {
				adminf.removeCustomer(id);
				return new Message(
						"Customer #" + id +" has been removed from the site");
		} catch (Exception e) {
			return new Message("Customer #" + id + " douse't exist, Please insert a valid id number ");
		}
	}

	// working plus exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCustomerById/{id}")
	public Message getCustomerById(@PathParam("id") int id, @Context HttpServletRequest request) {
		try {
			Customer tempCustomer = adminf.getCustomerById(id);
			if (tempCustomer.getName() != null) {
				return new Message(tempCustomer.toString());
			}
		} catch (Exception e) {
			return new Message("Customer #" + id + " douse't exist, Please insert a valid id number ");
		}
		return null;
	}


	//working plus exception
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/getCustomerOrderById/{customerId}")
	public Message getCustomerOrderById(@PathParam("customerId") int customerId, @Context HttpServletRequest request) {
		try {
			List<Customer_order> customerOrder = adminf.getCustomerOrderById(customerId);
			if (!customerOrder.isEmpty()) {
				return new Message("Customer's order/s: " + customerOrder.toString());
			}
			else {
				return new Message("No order is associated with Customer #" + customerId);
			}
		} catch (Exception e) {
			return new Message("No order is associated with Customer #" + customerId);
		}

	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/logout")
	public Message logout(@Context HttpServletRequest request) throws Exception {
		session = request.getSession(false);

		if (session != null) {
			session.invalidate();
			request = null;
			return new Message("Logged out successfully, goodbye administrator");
		}
		else {
		return new Message("Your are not looged in ! ");
		}
	}
}
