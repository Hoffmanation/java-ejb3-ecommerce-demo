package myStuff.Pojo.Jpa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name="WISHLIST")

@NamedQueries({
@NamedQuery(name="getWishlistById" , query="SELECT p FROM Wishlist AS p WHERE p.customerId = :customerId"),
@NamedQuery(name="getAllWishlist" , query="SELECT p FROM Wishlist AS p"),

})


public class Wishlist implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static int Counter = 1 ;
	private int customerId ;
	private int whishlistId ;
	private List<Product> wishlist = new ArrayList<>() ;
	
	
	   @OneToOne 
	   @JoinColumn(name="CUSTOMER_WISHLIST") 
	    private Customer customer;
	  
	
	public Wishlist() {
		// TODO Auto-generated constructor stub
	}


	public Wishlist(int customerId,List<Product> wishlist) {
		super();
		this.customerId = customerId;
		this.whishlistId = Counter++ ; ;
		this.wishlist = wishlist;
	}


	public static int getCounter() {
		return Counter;
	}


	public static void setCounter(int counter) {
		Counter = counter;
	}


	public int getCustomerId() {
		return customerId;
	}


	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}


	@Id         
	@Column(name="WISHLIST_ID")
	public int getWhishlistId() {
		return whishlistId;
	}


	public void setWhishlistId(int whishlistd) {
		this.whishlistId = whishlistd;
	}


	@ManyToMany(fetch=FetchType.EAGER)
	public List<Product> getWishlist() {
		return wishlist;
	}


	public void setWishlist(List<Product> wishlist) {
		this.wishlist = wishlist;
	}





	public Customer getCustomer() {
		return customer;
	}


	public void setCustomer(Customer customer) {
		this.customer = customer;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customer == null) ? 0 : customer.hashCode());
		result = prime * result + customerId;
		result = prime * result + whishlistId;
		result = prime * result + ((wishlist == null) ? 0 : wishlist.hashCode());
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
		Wishlist other = (Wishlist) obj;
		if (customer == null) {
			if (other.customer != null)
				return false;
		} else if (!customer.equals(other.customer))
			return false;
		if (customerId != other.customerId)
			return false;
		if (whishlistId != other.whishlistId)
			return false;
		if (wishlist == null) {
			if (other.wishlist != null)
				return false;
		} else if (!wishlist.equals(other.wishlist))
			return false;
		return true;
	}


	@Override
	public String toString() {
		return "Customer_Wishlist: customerId: " + customerId + "  | whishlistd: " + whishlistId + ", wishlist: " + wishlist ; 
	}


	
	
	
	
}
