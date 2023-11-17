package com.mystuff.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Product;

@Stateless
public class ProductDaoImpl extends DaoBase<Product> {

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	@Override
	public Product create(Product product) {
		em.persist(product);
		return product;
	}

	@Override
	public Product update(Product product) {
		em.merge(product);
		return product;
	}

	@Override
	public boolean delete(int productId) {
		int isSuccessful = em.createNamedQuery("deleteProductById", Product.class)
				.setParameter("productId", productId)
				.executeUpdate();
		return isSuccessful!=0;
	}

	@Override
	public Optional<Product> get(int productId) {
		Product product = em.createNamedQuery("getProductById", Product.class)
				.setParameter("productId", productId).getSingleResult() ;
		return Optional.of(product) ;
	}

	@Override
	public List<Product> getAll() {
		return  em.createNamedQuery("getAllProducts", Product.class)
				.getResultList();
	}


}
