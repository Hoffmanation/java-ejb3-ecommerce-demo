package myStuff.DaoBean.ejb;

import java.io.Serializable;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Customer_order;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.dao.ejb.AdminDao;
import myStuff.service.util.MyStuffException;

@Stateless
public class AdminDaoBean implements AdminDao, Serializable {
	private static final long serialVersionUID = -3169388660717615646L;

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	@SuppressWarnings("unused")
	@Override
	public Product createProduct(String name, String description, String imagePath, int price, ProType protype,int quantity) throws MyStuffException {
		    Product product = new Product();
			product.setName(name);
			product.setDescription(description);
			product.setImagePath(imagePath);
			product.setPrice(price);
			product.setType(protype);
			product.setQuantity(quantity);
			if (product!=null){
				em.persist(product);
				return product;	
			}
			return product;
	}

	@Override
	public Product updateProduct(int id, String description, String imagePath, int price, int quantity)
			throws MyStuffException {
		if (id != 0) {
			Product tempProduct = em.find(Product.class, id);
			tempProduct.setDescription(description);
			tempProduct.setImagePath(imagePath);
			tempProduct.setPrice(price);
			tempProduct.setQuantity(quantity);
			em.merge(tempProduct);
			return tempProduct;

		} else {
		
			throw new MyStuffException("Invalid Product entity");
		}
	}

	@Override
	public boolean removeProduct(int id) throws MyStuffException {
		if (id != 0) {
			em.remove(em.find(Product.class, id));
			return true;
		} else {
			Throwable e = new Throwable();
			throw new MyStuffException("Invalid Product entity" ,e);
		}
	}

	@Override
	public Product getProductById(int id) throws MyStuffException {
		if (id != 0) {
			Product product = em.createNamedQuery("getProductById", Product.class).setParameter("id", id)
					.getSingleResult();
			return product;
		} else {
			throw new MyStuffException("getProductById Failed");
		}
	}

	@Override
	public List<Customer> getAllCutsomers() {
		List<Customer> customers = em.createNamedQuery("getAllCustomers", Customer.class).getResultList();
		return customers;
	}

	// add &LIKE& SQL support
	@Override
	public Customer getCustomerByName(String name) {
		Customer customer = em.createNamedQuery("getCustomerByName", Customer.class).setParameter("name", name)
				.getSingleResult();
		return customer;
	}

	@Override
	public Order getOrderById(int id) throws MyStuffException {
		if (id != 0) {
			Order order = em.createNamedQuery("getOrderByOrderId", Order.class).setParameter("orderId", id)
					.getSingleResult();
			return order;
		} else {
			throw new MyStuffException("getOrderById Failed");
		}

	}

	@Override
	public boolean login(String userName, String password) {
		if (userName.equals("admin") && password.equals("admin")) {
			return true;
		}
		return false;
	}

	@Override
	public Product createProduct(Product product) throws MyStuffException {
		if (product != null) {
			em.persist(product);
		} else {
			throw new MyStuffException("Invalid product entity");
		}
		return product;

	}

	@Override
	public Customer createCustomer(Customer customer) throws MyStuffException {
		if (customer != null) {
			em.persist(customer);
		} else {
			throw new MyStuffException("Invalid Customer entity");
		}
		return customer;

	}

	@Override
	public List<Customer_order> getCustomerOrderById(int customerId) throws MyStuffException {
		if (customerId != 0) {
			List<Customer_order> customerOrder = em.createNamedQuery("getCustomerOrderById", Customer_order.class)
					.setParameter("customerId", customerId).getResultList();
			return customerOrder;
		} else {
			throw new MyStuffException("getCustomerOrderById Failed");
		}
	}

	@Override
	public boolean uploadImage() {
		return false;
	}

	@Override
	public boolean removeCustomer(int id) throws MyStuffException {
		if (id != 0) {
			em.remove(em.find(Customer.class, id));
			return true;
		} else {
			throw new MyStuffException("Cusotomer not found");
		}
	}

	@Override
	public Customer getCustomerById(int id) throws MyStuffException {
		if (id != 0) {
			return em.find(Customer.class, id);
		} else {
			throw new MyStuffException("Cusotomer not found");
		}
	}
}
