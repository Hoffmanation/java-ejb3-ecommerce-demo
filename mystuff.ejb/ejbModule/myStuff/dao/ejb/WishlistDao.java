package myStuff.dao.ejb;

import java.util.List;

import javax.ejb.Remote;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Product;
import myStuff.Pojo.Jpa.Wishlist;

@Remote
public interface WishlistDao {

	public boolean addToWishlistArray(Product product);

	public String addWishlist(Customer customer);

	public boolean ClearWishlist(Customer customer);

	public boolean updateWishlist(Customer customer);

	public boolean removeFromWishlistArray(Product product);

	public Wishlist getWishlist(int customerId);

	public String getWishlistSize(Customer customer);

}
