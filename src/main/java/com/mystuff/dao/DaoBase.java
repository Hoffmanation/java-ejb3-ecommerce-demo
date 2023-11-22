package com.mystuff.dao;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.ejb.Local;

@Local
public abstract class DaoBase<T> {

	public abstract Optional<T> get(int id);

	public abstract List<T> getAll();

	public abstract T create(T t);

	public abstract T update(T t);

	public abstract boolean delete(int id);

	public abstract T getResultCustomQuery(String namedQuery,Map<String, Object> parameters);

	public abstract List<T> getResultListCustomQuery(String namedQuery,Map<String, Object> parameters);

}
