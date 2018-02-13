package com.ks.dao;

import java.util.List;

public interface BaseDao {
	public void reload(Object obj);

	public Object save(Object obj);
	
	public void delete(Object obj);

	public void evict(Object obj);
	
	/**
	 * @see org.hibernate.Session#merge(Object object)
	 */
	public <T> T merge(Object obj);
	
	public <T> List<T> get(Class<T> clazz);

	public <T> T get(Class<T> clazz, Integer id);
	
	
	

}
