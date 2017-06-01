package myStuff.dao.ejb;

import java.util.List;

import javax.ejb.Remote;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Order;
import myStuff.Pojo.Jpa.Product;
import myStuff.service.util.MyStuffException;

@Remote
public interface CartDao {
	public boolean addToCart(Product product) throws MyStuffException;

	public double getCartSum();

	public void ClearCart();

	public boolean removeFromCart(Product product);

	public Order checkOut(Customer cust);

	public List<Product> getCart();

	public int getCartsize();

	public void clear();

}
