package myStuff.Pojo.Jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "CUSTOMER_ORDER")

@NamedQueries({
		@NamedQuery(name = "getOrderByCustomerId", query = "SELECT c FROM Customer_order AS c WHERE c.customerId = :customerId"),
		@NamedQuery(name = "getAllCustomersOrder", query = "SELECT c FROM Customer_order AS c ORDER BY C.orderId asc"),
})

@IdClass(ProductAssociationId.class)
public class Customer_order implements Serializable {
	private static final long serialVersionUID = 2356940831665710978L;

	@Id
	private int customerId;
	
	@Id
	private int orderId;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "CUSTOMER_ID", referencedColumnName = "ID")
	private Customer customer;

	@ManyToOne
	@PrimaryKeyJoinColumn(name = "ORDER_ID", referencedColumnName = "ID")
	private Order tempOrder;

	public Customer_order() {
	}

	public Customer_order( int orderId,int customerId) {
		super();
		this.orderId = orderId;
		this.customerId = customerId;
		
	}

	public int getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	
	@Override
	public String toString() {
		return "Customer-order's association details:  orderId: " + orderId+" | customerId: " + customerId ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + customerId;
		result = prime * result + orderId;
		result = prime * result + ((tempOrder == null) ? 0 : tempOrder.hashCode());
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
		Customer_order other = (Customer_order) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (customerId != other.customerId)
			return false;
		if (orderId != other.orderId)
			return false;
		if (tempOrder == null) {
			if (other.tempOrder != null)
				return false;
		} else if (!tempOrder.equals(other.tempOrder))
			return false;
		return true;
	}

	

	

}