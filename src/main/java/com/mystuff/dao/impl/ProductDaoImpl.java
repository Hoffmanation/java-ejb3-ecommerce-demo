package com.mystuff.dao.impl;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Order;
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
		Product product = em.find(Product.class, productId);
		return Optional.of(product) ;
	}

	@Override
	public List<Product> getAll() {
		return  em.createNamedQuery("getAllProducts", Product.class)
				.getResultList();
	}

	@Override
	public Product getResultCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Product> namedQueryStatment = em.createNamedQuery(namedQuery, Product.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getSingleResult();
	}

	@Override
	public List<Product> getResultListCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Product> namedQueryStatment = em.createNamedQuery(namedQuery, Product.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getResultList();
	}


}
