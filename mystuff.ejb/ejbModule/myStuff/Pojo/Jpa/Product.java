package myStuff.Pojo.Jpa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

import myStuff.DaoBean.ejb.ProType;

@Entity
@Table(name = "PRODUCTS")

@NamedQueries({ @NamedQuery(name = "getAllProducts", query = "SELECT p FROM Product  AS p ORDER BY p.id ASC"),
		@NamedQuery(name = "getProductByName", query = "SELECT p FROM Product AS p WHERE p.name = :name"),
		@NamedQuery(name = "getProductById", query = "SELECT p FROM Product AS p WHERE p.id = :id"),
		@NamedQuery(name = "getAllProductByPriceEx", query = "SELECT p FROM Product AS p ORDER BY p.price desc"),
		@NamedQuery(name = "getAllProductByPriceChe", query = "SELECT p FROM Product AS p ORDER BY p.price asc"),
		@NamedQuery(name = "searchProductByName", query = "SELECT p FROM Product p WHERE UPPER(p.name) LIKE :name"),
		@NamedQuery(name = "getAllProductsByType", query = "SELECT p FROM Product p WHERE p.type = :type"),
		@NamedQuery(name = "serchProductByPriceRange", query = "SELECT p FROM Product p WHERE p.price  BETWEEN  :price AND :price"),

})
public class Product implements Serializable {
	private static final long serialVersionUID = 3844398132096666446L;

	public static int Counter = 1;
	private int quantity;
	private int id;
	private String name;
	private double price;
	private ProType type;
	@Column(columnDefinition="text")
	private String description;
	private String imagePath;
	private int stock;
	private List<Order> orders;
	private List<Wishlist> wishlist;
	String newline = System.getProperty("line.separator");
	
	public Product() {

	}

	public Product(String name, double price, ProType type, String description, String imagePath, int stock,
			int quantity) {
		super();
		this.quantity = quantity;
		this.id = Counter++;
		this.name = name;
		this.price = price;
		this.type = type;
		this.description = description;
		this.imagePath = imagePath;
		this.stock = stock;
	}

	@Id
	@Column(name = "PRODUCT_ID")
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

	
	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public ProType getType() {
		return type;
	}

	@Enumerated(EnumType.STRING)
	public void setType(ProType type) {
		this.type = type;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Wishlist.class)
	public List<Wishlist> getWishlist() {
		return wishlist;
	}

	public void setWishlist(List<Wishlist> wishlist) {
		this.wishlist = wishlist;
	}

	@ManyToMany(fetch = FetchType.EAGER, targetEntity = Order.class)
	public List<Order> getOrders() {
		return orders;
	}

	public void setOrders(List<Order> orders) {
		this.orders = orders;
	}

	@Override
	public String toString() {
		return "Name: " + name + " | Type=" + type+" | Quantity: " + quantity+ " | Price: "+ price ;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + id;
		result = prime * result + ((imagePath == null) ? 0 : imagePath.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((orders == null) ? 0 : orders.hashCode());
		long temp;
		temp = Double.doubleToLongBits(price);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + quantity;
		result = prime * result + stock;
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Product other = (Product) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id != other.id)
			return false;
		if (imagePath == null) {
			if (other.imagePath != null)
				return false;
		} else if (!imagePath.equals(other.imagePath))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (orders == null) {
			if (other.orders != null)
				return false;
		} else if (!orders.equals(other.orders))
			return false;
		if (Double.doubleToLongBits(price) != Double.doubleToLongBits(other.price))
			return false;
		if (quantity != other.quantity)
			return false;
		if (stock != other.stock)
			return false;
		if (type != other.type)
			return false;
		if (wishlist == null) {
			if (other.wishlist != null)
				return false;
		} else if (!wishlist.equals(other.wishlist))
			return false;
		return true;
	}


	

}
