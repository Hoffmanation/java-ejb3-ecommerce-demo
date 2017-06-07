package myStuff.Pojo.Jpa;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "CUSTOMERS")

@NamedQueries({ @NamedQuery(name = "getAllCustomers", query = "SELECT c FROM Customer AS c ORDER BY c.id asc"),
		@NamedQuery(name = "getCustomerByName", query = "SELECT c FROM Customer AS c WHERE UPPER(c.name) LIKE :name"),
		@NamedQuery(name = "getCustomerByEmail", query = "SELECT c FROM Customer AS c WHERE c.email = :email"),
		@NamedQuery(name = "getCustomerById", query = "SELECT c FROM Customer AS c WHERE c.id = :id"),
		@NamedQuery(name = "login", query = "SELECT c FROM Customer AS c WHERE c.password = :password AND c.email = :email"),

})
public class Customer implements Serializable {
	private static final long serialVersionUID = 8213567790070593752L;

	private static int Counter = 1;
	private int id;
	private String name;
	private String f_name;
	private String password;
	private String email;
	@OneToOne(mappedBy = "customer")
	private Wishlist wishlist;

	public Customer() {

	}

	public Customer(String name, String f_name, String password, String email) {
		super();
		this.id = Counter++;
		this.name = name;
		this.f_name = f_name;
		this.password = password;
		this.email = email;
	}

	@Id
	@Column(name = "CUSTOMER_ID")
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getF_name() {
		return f_name;
	}

	public void setF_name(String f_name) {
		this.f_name = f_name;
	}

	@Column(columnDefinition = "VARCHAR(10)")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Wishlist getWishlist() {
		return wishlist;
	}

	public void setWishlist(Wishlist wishlist) {
		this.wishlist = wishlist;
	}

	@Override
	public String toString() {
		return "Customer's details:  ID: " + id + " | Name: " + name + " " + f_name + " | Password: " + password
				+ " | Email: " + email;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((email == null) ? 0 : email.hashCode());
		result = prime * result + ((f_name == null) ? 0 : f_name.hashCode());
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((password == null) ? 0 : password.hashCode());
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
		Customer other = (Customer) obj;
		if (email == null) {
			if (other.email != null)
				return false;
		} else if (!email.equals(other.email))
			return false;
		if (f_name == null) {
			if (other.f_name != null)
				return false;
		} else if (!f_name.equals(other.f_name))
			return false;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (password == null) {
			if (other.password != null)
				return false;
		} else if (!password.equals(other.password))
			return false;
		if (wishlist == null) {
			if (other.wishlist != null)
				return false;
		} else if (!wishlist.equals(other.wishlist))
			return false;
		return true;
	}

}
