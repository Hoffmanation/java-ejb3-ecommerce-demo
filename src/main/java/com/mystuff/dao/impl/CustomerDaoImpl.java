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
import com.mystuff.entity.Wishlist;

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
		return em.merge(customer);
	}

	@Override
	public Optional<Customer> get(int cusomerId) {
		Customer customer = 	em.find(Customer.class, cusomerId);
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

	@Override
	public Customer getResultCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Customer> namedQueryStatment = em.createNamedQuery(namedQuery, Customer.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getResultList().stream().findFirst().orElse(null);
	}

	@Override
	public List<Customer> getResultListCustomQuery(String namedQuery, Map<String, Object> parameters) {
		 TypedQuery<Customer> namedQueryStatment = em.createNamedQuery(namedQuery, Customer.class) ;
		 if (parameters!=null&& !parameters.isEmpty()) {
			 parameters.forEach((k,v) -> namedQueryStatment.setParameter(k,v));
		}
		 return namedQueryStatment.getResultList();
	}



}
