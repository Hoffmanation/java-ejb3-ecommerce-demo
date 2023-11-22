//package com.mystuff.service;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import javax.ejb.Stateless;
//
//import com.mystuff.entity.Customer;
//import com.mystuff.entity.Order;
//import com.mystuff.entity.Product;
//import com.mystuff.entity.Wishlist;
//import com.mystuff.obj.ProType;
//import com.mystuff.util.MyStuffException;
//
//@Stateless
//public class AdminService {
//	
//	private  List<Product> wishlist = new ArrayList<Product>();
//
//	@Override
//	public boolean addToWishlistArray(Product product) {
//		if (this.wishlist.add(product)) {
//			return true;
//		}
//		return false;
//	}
//
//	@Override
//	public boolean removeFromWishlistArray(Product product) {
//		for (int i = 0; i < wishlist.size(); i++) {
//			if (wishlist.get(i).getId() == product.getId()) {
//				wishlist.remove(i);
//				return true;
//			}
//		}
//		return false;
//
//	}
//
//	// the wishlist entity in sql
//	@Override
//	public String addWishlist(Customer customer) {
//		return null;
//	}
//
//	@Override
//	public boolean updateWishlist(Customer customer) {
//		Wishlist tempWish = null;
//		List<Wishlist> wishlist1 = em.createNamedQuery("getAllWishlist", Wishlist.class).getResultList();
//		for (int i = 0; i < wishlist1.size(); i++) {
//			if (wishlist1.get(i).getCustomerId() == customer.getId()) {
//				tempWish = em.find(Wishlist.class, wishlist1.get(i).getWhishlistId());
//				tempWish.setWishlist(wishlist);
//				em.merge(tempWish);
//				return true;
//			}
//		}
//		Wishlist w = new Wishlist(customer.getId(), wishlist);
//		em.merge(w);
//		return false;
//
//	}
//
//	@Override
//	public boolean ClearWishlist(Customer customer) {
//		List<Product> tempwish = new ArrayList<Product>();
//		tempwish.clear();
//		Wishlist w = new Wishlist(customer.getId(), tempwish);
//		em.merge(w);
//		return false;
//	}
//
//	@Override
//	public Wishlist getWishlist(int customerId) {
//		Wishlist wish = em.createNamedQuery("getWishlistById", Wishlist.class).setParameter("customerId", customerId)
//				.getSingleResult();
//		return wish;
//	}
//
//	@Override
//	public String getWishlistSize(Customer customer) {
//		Wishlist wish = em.createNamedQuery("getWishlistById", Wishlist.class)
//				.setParameter("customerId", customer.getId()).getSingleResult();
//		wish.getWishlist();
//		return "" + wish.getWishlist().size();
//	}
//	
//
//	
//	@Override
//	public List<Order> getCustomerOrderById(Integer customerId) {
//		List<Order> customerOrder = em.createNamedQuery("getOrderByCustomerId", Order.class)
//				.setParameter("customerId", customerId).getResultList();
//		return customerOrder;
//	}
//
//	@Override
//	public List<Order> getOrderByOrderId(Integer orderId) {
//		List<Order> customerOrder = em.createNamedQuery("getOrderByOrderId", Order.class)
//				.setParameter("orderId", orderId).getResultList();
//		return customerOrder;
//	}
//
//	@Override
//	public Product getProductByName(String name) {
//		Product product = em.createNamedQuery("getProductByName", Product.class).setParameter("name", name)
//				.getSingleResult();
//		return product;
//	}
//
//
//	@Override
//	public List<Product> getAllProductByPriceEx() {
//		List<Product> product = em.createNamedQuery("getAllProductByPriceEx", Product.class).getResultList();
//		return product;
//	}
//
//	@Override
//	public List<Product> getAllProductByPriceChe() {
//		List<Product> product = em.createNamedQuery("getAllProductByPriceChe", Product.class).getResultList();
//		return product;
//	}
//
//	@Override
//	public Customer getCustomerByName(String name) {
//		Customer customer = em.createNamedQuery("getCustomerByName", Customer.class).setParameter("name", name)
//				.getSingleResult();
//		return customer;
//	}
//
//	@Override
//	public Customer getCustomerByEmail(String email) {
//		email = email.toLowerCase();
//		Customer customer = em.createNamedQuery("getCustomerByEmail", Customer.class).setParameter("email", email)
//				.getSingleResult();
//		return customer;
//	}
//
//	@Override
//	public List<Product> getAllProductsByType(ProType type) {
//		List<Product> produts = em.createNamedQuery("getAllProductsByType", Product.class).setParameter("type", type)
//				.getResultList();
//		return produts;
//	}
//
//	@Override
//	public Product getProductById(Integer id) {
//		Product produt = em.find(Product.class, id);
//		return produt;
//	}
//
//	@Override
//	public List<Product> searchProductByPrice(double minPrice, double maxPrice) {
//		List<Product> produts = em.createNamedQuery("serchProductByPriceRange", Product.class)
//				.setParameter("minPrice", minPrice).setParameter("maxPrice", maxPrice).getResultList();
//		return produts;
//	}
//
//	@Override
//	public List<Product> getCurrProducts(List<Product> currProd, ProType type) {
//		List<Product> proType = getAllProductsByType(type);
//		currProd.addAll(proType);
//
//		return null;
//	}
//
//}
