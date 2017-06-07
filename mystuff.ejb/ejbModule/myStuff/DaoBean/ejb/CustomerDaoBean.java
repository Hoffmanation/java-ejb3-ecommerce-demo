package myStuff.DaoBean.ejb;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Customer_order;
import myStuff.Pojo.Jpa.Message;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.dao.ejb.CustomerDao;
import myStuff.dao.ejb.UtilDao;
import myStuff.service.util.MyStuffException;
import myStuff.service.util.Utilities;

@Stateless
public class CustomerDaoBean implements CustomerDao, Serializable {
	private static final long serialVersionUID = 2924343035032255851L;

	@EJB
	private UtilDao utile ;
	
	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	@Override
	public String signUp(String name, String f_name, String password, String email) throws MyStuffException {
		f_name = f_name.toLowerCase();
		name = name.toLowerCase();
		email = email.toLowerCase();
		if (utile.IsValidEmail(email)){
			if (utile.IsUniqueUser(email, password)){
				Customer tempCust = new Customer(name, f_name, password, email);
				em.persist(tempCust);
				return "You have succssfully loged in !" ;
			}
			else {
				return "*There's already a user with that mail !" ; 
			}
		}
			return "*Email is not valid !" ; 
	}

	@Override
	public boolean updateCustomer(Customer customer, String password, String email) throws MyStuffException {
		if (customer.getId() != 0) {
			Customer tempCustomer = em.find(Customer.class, customer.getId());
			tempCustomer.setEmail(email);
			tempCustomer.setPassword(password);
			em.merge(tempCustomer);
			return true;
		} else {
			throw new MyStuffException("Invalid customer entity");
		}
	}

	@Override
	public boolean login(String email, String password) {
		email = email.toLowerCase();
		password = password.toLowerCase();
		if (utile.IsUniqueUser(email, password)) {
			return false;
		} else {
			return true;
		}
	}

	@Override
	public List<Order> getCustomerOrderById(int CustomerId) {
		List<Order> customerOrder = em.createNamedQuery("getOrderByCustomerId", Order.class).setParameter("customerId", CustomerId).getResultList();
		return customerOrder;
	}
	
	@Override
	public List<Order> getOrderByOrderId(int orderId) {
		List<Order> customerOrder = em.createNamedQuery("getOrderByOrderId", Order.class).setParameter("orderId", orderId).getResultList();
		return customerOrder;
	}

	@Override
	public Product getProductByName(String name) {
		Product product = em.createNamedQuery("getProductByName", Product.class).setParameter("name", name)
				.getSingleResult();
		return product;
	}

	@Override
	public List<Product> getAllProducts() {
		List<Product> product = em.createNamedQuery("getAllProducts", Product.class).getResultList();
		return product;
	}

	@Override
	public List<Product> getAllProductByPriceEx() {
		List<Product> product = em.createNamedQuery("getAllProductByPriceEx", Product.class).getResultList();
		return product;
	}

	@Override
	public List<Product> getAllProductByPriceChe() {
		List<Product> product = em.createNamedQuery("getAllProductByPriceChe", Product.class).getResultList();
		return product;
	}

	@Override
	public Customer getCustomerByName(String name) {
		Customer customer = em.createNamedQuery("getCustomerByName", Customer.class).setParameter("name", name)
				.getSingleResult();
		return customer;
	}

	@Override
	public Customer getCustomerByEmail(String email) {
		email = email.toLowerCase();
		Customer customer = em.createNamedQuery("getCustomerByEmail", Customer.class).setParameter("email", email)
				.getSingleResult();
		return customer;
	}

	@Override
	public Customer getCustomerInfo(Customer customer) {
		return customer;
	}

	@Override
	public List<Product> searchProductByName(String name) {
		List<Product> producs = em.createNamedQuery("searchProductByName", Product.class)
				.setParameter("name", "%" + name + "%").getResultList();
		return producs;
	}

	@Override
	public List<Product> getAllProductsByType(ProType type) {
		List<Product> produts = em.createNamedQuery("getAllProductsByType", Product.class).setParameter("type", type)
				.getResultList();
		return produts;
	}

	@Override
	public List<Product> getProductArrayById(int id) {
		List<Product> produts = em.createNamedQuery("getProductById", Product.class).setParameter("id", id)
				.getResultList();
		return produts;
	}

	@Override
	public List<Product> searchProductByPrice(double minPrice, double maxPrice) throws SQLException {
		List<Product> newArray = new ArrayList<>();
		List<Product> produts = em.createNamedQuery("getAllProducts", Product.class).getResultList();
		for (int i = 0; i < produts.size(); i++) {
			if (produts.get(i).getPrice() > minPrice && produts.get(i).getPrice() < maxPrice) {
				newArray.add(produts.get(i));
			} 
		}
		return newArray;
	}

	@Override
	public List<Product> getCurrProducts(List<Product> currProd, ProType type) {
		List<Product> proType = getAllProductsByType(type);
		currProd.addAll(proType);

		return null;
	}

	@Override
	public Product getProductById(int id) throws MyStuffException {
		if (id!=0){
			Product product = em.createNamedQuery("getProductById", Product.class).setParameter("id", id).getSingleResult();
			return product;
		}
		else {
		throw new MyStuffException("Invalid product entity");
		}
	}

}
