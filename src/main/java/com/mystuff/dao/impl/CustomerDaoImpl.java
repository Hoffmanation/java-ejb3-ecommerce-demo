package com.mystuff.dao.impl;

import java.util.List;
import java.util.Optional;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import com.mystuff.dao.DaoBase;
import com.mystuff.entity.Customer;

@Stateless
public class CustomerDaoImpl extends DaoBase<Customer> {

	@PersistenceContext(unitName = "mystuff")
	private EntityManager em;
	
	@Override
	public Customer create(Customer customer) {
		em.persist(customer);
		return customer;
	}

	@Override
	public Customer update(Customer customer) {
		em.merge(customer);
		return customer;
	}

	@Override
	public Optional<Customer> get(int cusomerId) {
		Customer customer = em.createNamedQuery("getCustomerById", Customer.class)
				.setParameter("cusomerId", cusomerId)
				.getSingleResult();
		return Optional.of(customer);
	}

	@Override
	public List<Customer> getAll() {
		return  em.createNamedQuery("getAllCustomers", Customer.class)
				.getResultList();
	}

	@Override
	public boolean delete(int id) {
		// No Impl
		return false;
	}

}
