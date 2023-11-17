package com.mystuff.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mystuff.dao.DaoBase;
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
		Order order = em.createNamedQuery("getOrderById", Order.class)
				.setParameter("orderId", orderId)
				.getSingleResult();
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

}
