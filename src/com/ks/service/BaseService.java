package com.ks.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

public interface BaseService {
	public void reload(Object obj);

	@Transactional(rollbackFor = Throwable.class)
	public Object save(Object obj);
	
	@Transactional(rollbackFor = Throwable.class)
	public void delete(Object obj);
	
	void evict(Object obj);
	
	/**
	 * @see org.hibernate.Session#merge(Object object)
	 */
	<T> T merge(Object obj);

	public <T> List<T> get(Class<T> clazz);
	
	public <T> T get(Class<T> clazz, Integer id);

	public boolean tableExists(String tableName);
}
