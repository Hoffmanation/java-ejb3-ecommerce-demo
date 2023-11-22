package com.mystuff.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Product;
import com.mystuff.entity.Wishlist;

@Stateless
public class WishlistDaoImpl extends DaoBase<Wishlist> {

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	@Override
	public Wishlist create(Wishlist wishlist) {
		em.persist(wishlist);
		return wishlist;
	}

	@Override
	public Wishlist update(Wishlist wishlist) {
		em.merge(wishlist);
		return wishlist;
	}

	@Override
	public boolean delete(int wishlistId) {
		Optional<Wishlist> optionalWishlist = this.get(wishlistId) ;
		if (optionalWishlist.isPresent()) {
			em.remove(optionalWishlist.get());	
			return true ;
		}
		return false ;
	}

	@Override
	public Optional<Wishlist> get(int wishlistId) {
		Wishlist Wishlist = em.find(Wishlist.class, wishlistId) ;
		return Optional.of(Wishlist) ;
	}

	@Override
	public List<Wishlist> getAll() {
		return em.createNamedQuery("getAllWishlists", Wishlist.class).getResultList();
	}

	@Override
	public Wishlist getResultCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Wishlist> namedQueryStatment = em.createNamedQuery(namedQuery, Wishlist.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getSingleResult();
	}

	@Override
	public List<Wishlist> getResultListCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Wishlist> namedQueryStatment = em.createNamedQuery(namedQuery, Wishlist.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getResultList();
	}


}
