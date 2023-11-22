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
import com.mystuff.entity.Customer;
import com.mystuff.entity.Order;

@Stateless
public class OrderDaoImpl extends DaoBase<Order> {

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;

	@Override
	public Order create(Order order) {
		em.persist(order);
		return order;
	}

	@Override
	public Optional<Order> get(int orderId) {
		Order order = em.find(Order.class, orderId);
		return Optional.of(order);
	}

	@Override
	public List<Order> getAll() {
		return  em.createNamedQuery("getAllOrders", Order.class)
				.getResultList();
	}

	@Override
	public Order update(Order t) {
		// Not Implemented
		return null;
	}

	@Override
	public boolean delete(int id) {
		// Not Implemented
		return false;
	}

	@Override
	public Order getResultCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Order> namedQueryStatment = em.createNamedQuery(namedQuery, Order.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getSingleResult();
	}

	@Override
	public List<Order> getResultListCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Order> namedQueryStatment = em.createNamedQuery(namedQuery, Order.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getResultList();
	}

}
