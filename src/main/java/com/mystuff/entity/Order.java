package com.mystuff.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Entity
@Table(name = "orders")
@NamedQueries({ @NamedQuery(name = "getAllOrders", query = "SELECT o FROM Order  AS o ORDER BY o.orderId ASC"),
		@NamedQuery(name = "getOrderById", query = "SELECT o FROM Order  AS o WHERE o.orderId = :orderId"),
		@NamedQuery(name = "getOrdersByCustomerId", query = "SELECT o FROM Order  AS o WHERE o.customer.cusomerId = :customerId"),

})
public class Order implements Serializable{
	private static final long serialVersionUID = -6300981392612885499L;

	@Id
	@Column(name = "order_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int orderId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "create_date")
	private Date timestamp;

	private double amount;

	//Join table for order_product
	@ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
	@JoinTable(name="order_product",
     joinColumns={@JoinColumn(name="order_id")},
     inverseJoinColumns={@JoinColumn(name="product_id")},
    uniqueConstraints=@UniqueConstraint(columnNames={"order_id","product_id"}))
	private List<Product> products = new ArrayList<>();;

	//Join table for customer_order
	@ManyToOne(cascade = CascadeType.ALL )
	@JoinTable(name = "customer_order", 
	joinColumns = { @JoinColumn(name = "order_id") }, 
	inverseJoinColumns = {@JoinColumn(name = "customer_id") },
    uniqueConstraints=@UniqueConstraint(columnNames={"order_id","customer_id"}))
   
	//Due to stackOverFlow exception - Do not serialize the hole customer object, only his id as - "cusomerId"
  @JsonIdentityInfo(generator=ObjectIdGenerators.PropertyGenerator.class, property="cusomerId")
  @JsonIdentityReference(alwaysAsId=true) 
  @JsonProperty("cusomerId")
	private Customer customer;
	
	@Transient
	private int size ;
	
	@Transient
	private int sum ;

	public Order() {

	}

	public Order(Date timestamp, double amount, List<Product> products, Customer customer) {
		super();
		this.timestamp = timestamp;
		this.amount = amount;
		this.products = products;
		this.customer = customer;
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public List<Product> getProducts() {
		return products;
	}

	public void setProducts(List<Product> products) {
		this.products = products;
	}

	public Customer getCustomer() {
		return customer;
	}

	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getSum() {
		return sum;
	}

	public void setSum(int sum) {
		this.sum = sum;
	}

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", timestamp=" + timestamp + ", amount=" + amount + "]";
	}

}
