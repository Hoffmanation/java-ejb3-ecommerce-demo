package myStuff.DaoBean.ejb;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.ejb.PostActivate;
import javax.ejb.PrePassivate;
import javax.ejb.Remove;
import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Customer_order;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.dao.ejb.CartDao;
import myStuff.service.util.MyStuffException;

@Stateful
public class CartDaoBean implements CartDao, Serializable {
	private static final long serialVersionUID = -5390229253489172920L;

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	private List<Product> pro;
	private static List<Product> tempProducts;
	private static List<Double> values;

	public CartDaoBean() {

	}

	@PostConstruct
	public void init() {
		this.values = new ArrayList<>();
		this.tempProducts = new ArrayList<>();
	}

	@PreDestroy
	public void destroy() {
	}

	@Override
	public boolean addToCart(Product product) throws MyStuffException {
		if (this.tempProducts.add(product) && this.values.add(product.getPrice())) {
			 substructProductQuantity(product);
			return true;
		}
		return false;
	}

	@Override
	public double getCartSum() {
		double rv = 0;
		for (double value : this.values) {
			rv += value;
		}
		return rv;
	}

	@Override
	public boolean removeFromCart(Product product) {
		for (int i = 0; i < tempProducts.size(); i++) {
			if (tempProducts.get(i).getId() == product.getId()) {
				tempProducts.remove(i);
				values.remove(product.getPrice());
				return true;
			}
		}
		return false;
	}

	@Override
	public Order checkOut(Customer cust) {
		if (!tempProducts.isEmpty()) {
			Order tempOrder = null;
			DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date date = new Date();
			tempOrder = new Order(cust.getId(), dateFormat.format(date), getCartSum(), getCart());
			Customer_order association = new Customer_order();
			association.setCustomerId(cust.getId());
			association.setOrderId(tempOrder.getOrderId());
			em.merge(tempOrder);
			em.merge(association);
			this.ClearCart();
			return tempOrder;
		}
		return null;
	}

	@Override
	public List<Product> getCart() {
			return tempProducts;
	}

	@Override
	public int getCartsize() {
		return tempProducts.size();
	}

	@Override
	public void ClearCart() {
		tempProducts.clear();
		values.clear();
	}

	public boolean substructProductQuantity(Product product) throws MyStuffException {
		if (product.getId()!=0){
			Product tempProduct = em.createNamedQuery("getProductById", Product.class).setParameter("id", product.getId())
					.getSingleResult();
			tempProduct.setStock(tempProduct.getStock() - 1);
			em.merge(tempProduct);
			return true ;
		}
		else {
			throw new MyStuffException("Invalid Product entity");
		}
	}

	@Override
	@Remove
	public void clear() {
	}

	@PrePassivate
	public void prePassivate() {

	}

	@PostActivate
	public void postActivate() {

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((em == null) ? 0 : em.hashCode());
		result = prime * result + ((pro == null) ? 0 : pro.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CartDaoBean other = (CartDaoBean) obj;
		if (em == null) {
			if (other.em != null)
				return false;
		} else if (!em.equals(other.em))
			return false;
		if (pro == null) {
			if (other.pro != null)
				return false;
		} else if (!pro.equals(other.pro))
			return false;
		return true;
	}

}
