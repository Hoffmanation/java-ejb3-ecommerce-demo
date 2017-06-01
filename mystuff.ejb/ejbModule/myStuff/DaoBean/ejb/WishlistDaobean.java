package myStuff.DaoBean.ejb;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.hibernate.validator.internal.util.privilegedactions.GetConstraintValidatorList;

import myStuff.Pojo.Jpa.Customer;
import myStuff.Pojo.Jpa.Product;
import myStuff.Pojo.Jpa.Wishlist;
import myStuff.dao.ejb.WishlistDao;

@Stateful
public class WishlistDaobean implements WishlistDao, Serializable {
	private static final long serialVersionUID = 7372739505072004810L;

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	private static List<Product> wishlist = new ArrayList<Product>();

	@Override
	public boolean addToWishlistArray(Product product) {
		if (this.wishlist.add(product)) {
			return true;
		}
		return false;
	}

	@Override
	public boolean removeFromWishlistArray(Product product) {
		for (int i = 0; i < wishlist.size(); i++) {
			if (wishlist.get(i).getId() == product.getId()) {
				wishlist.remove(i);
				return true;
			}
		}
		return false;

	}

	// the wishlist entity in sql
	@Override
	public String addWishlist(Customer customer) {
		return null;
	}

	@Override
	public boolean updateWishlist(Customer customer) {
		Wishlist tempWish = null;
		List<Wishlist> wishlist1 = em.createNamedQuery("getAllWishlist", Wishlist.class).getResultList();
		for (int i = 0; i < wishlist1.size(); i++) {
			if (wishlist1.get(i).getCustomerId() == customer.getId()) {
				tempWish = em.find(Wishlist.class, wishlist1.get(i).getWhishlistId());
				tempWish.setWishlist(wishlist);
				em.merge(tempWish);
				return true;
			}
		}
		Wishlist w = new Wishlist(customer.getId(), wishlist);
		em.merge(w);
		return false;

	}

	@Override
	public boolean ClearWishlist(Customer customer) {
		List<Product> tempwish = new ArrayList<Product>();
		tempwish.clear();
		Wishlist w = new Wishlist(customer.getId(), tempwish);
		em.merge(w);
		return false;
	}

	@Override
	public Wishlist getWishlist(int customerId) {
		Wishlist wish = em.createNamedQuery("getWishlistById", Wishlist.class).setParameter("customerId", customerId)
				.getSingleResult();
		return wish;
	}

	@Override
	public String getWishlistSize(Customer customer) {
		Wishlist wish = em.createNamedQuery("getWishlistById", Wishlist.class)
				.setParameter("customerId", customer.getId()).getSingleResult();
		wish.getWishlist();
		return "" + wish.getWishlist().size();
	}

}
