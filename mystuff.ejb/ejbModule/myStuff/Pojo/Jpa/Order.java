package myStuff.Pojo.Jpa;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.HibernateValidator;


@Entity
@Table(name="ORDERS")


@NamedQueries({
@NamedQuery(name="getAllOrders" , query="SELECT o FROM Order  AS o ORDER BY o.orderId ASC"),
@NamedQuery(name="getOrderByOrderId" , query="SELECT o FROM Order  AS o WHERE o.orderId = :orderId"),

})
public class Order implements Serializable{
	private static final long serialVersionUID = 7149327144463841266L;
	

	public static int Counter = 1 ;
	private int orderId;
	private int customerId ; 
	private String dateStamp ; 
	private double payment ; 
	private List<Product> products ; 
	private String productsArray ; 
	
	public Order() {
		
	}

	public Order(int customerId,String dateStamp,double payment, List<Product>products) {
		super();
		this.orderId = Counter++;
		this.dateStamp = dateStamp;
		this.payment=payment;
		this.products = products ; 
		this.customerId=customerId ; 
		
	}
	
	public Order(int customerId,String dateStamp,double payment, String productsArray) {
		super();
		this.orderId = Counter++;
		this.dateStamp = dateStamp;
		this.payment=payment;
		this.productsArray = productsArray ; 
		this.customerId=customerId ; 
		
	}

	@Id         
	@Column(name="ORDER_ID")
	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}


	public String getDateStamp() {
		return dateStamp;
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	
	}

	public void setDateStamp(String dateStamp) {
		this.dateStamp = dateStamp;
	}


	@ManyToMany(fetch=FetchType.EAGER)
	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}


	public double getPayment() {
		return payment;
	}

	public void setPayment(double payment) {
		this.payment = payment;
	}



	public String getProductsArray() {
		return productsArray;
	}

	public void setProductsArray(String productsArray) {
		this.productsArray = productsArray;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + customerId;
		result = prime * result + ((dateStamp == null) ? 0 : dateStamp.hashCode());
		result = prime * result + orderId;
		long temp;
		temp = Double.doubleToLongBits(payment);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((products == null) ? 0 : products.hashCode());
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
		Order other = (Order) obj;
		if (customerId != other.customerId)
			return false;
		if (dateStamp == null) {
			if (other.dateStamp != null)
				return false;
		} else if (!dateStamp.equals(other.dateStamp))
			return false;
		if (orderId != other.orderId)
			return false;
		if (Double.doubleToLongBits(payment) != Double.doubleToLongBits(other.payment))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Orders details: OrderId: " + orderId + " | CustomerId: "+customerId+" | Payment: "+payment+" | Date: "+ dateStamp+" | " +products ;
	}
	
	
	
	

}
