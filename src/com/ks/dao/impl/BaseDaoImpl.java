package com.ks.dao.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ks.dao.BaseDao;

@Repository
public class BaseDaoImpl implements BaseDao {

	protected DataSource dataSource;

	@Autowired
	protected SessionFactory sessionFactory;

	protected NamedParameterJdbcTemplate jdbcTemplate;

	protected Logger log = LogManager.getLogger(this.getClass());

	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	/**
	 * Javadoc from {@link org.hibernate.Session#refresh(Object)}
	 * <p>
	 * ==BEGIN==
	 * <p>
	 * Re-read the state of the given instance from the underlying database. It is
	 * inadvisable to use this to implement long-running sessions that span many
	 * business tasks. This method is, however, useful in certain special circumstances.
	 * For example
	 * <ul>
	 * <li>where a database trigger alters the object state upon insert or update
	 * <li>after executing direct SQL (eg. a mass update) in the same session
	 * <li>after inserting a <tt>Blob</tt> or <tt>Clob</tt>
	 * </ul>
	 * <p>
	 * ==END==
	 * 
	 * @param obj a persistent or detached instance
	 */
	@Override
	public void reload(Object obj) {
		log.debug("Reloading object: " + obj.getClass());
		Session session = sessionFactory.getCurrentSession();
		session.refresh(obj);
	}

	@Override
	public Object save(Object obj) {
		log.debug("Saving object: " + obj.getClass());
		Session session = sessionFactory.getCurrentSession();
		session.saveOrUpdate(obj);
		return obj;
	}

	@Override
	public void delete(Object obj) {
		log.debug("Deleting object: " + obj.getClass());
		Session session = sessionFactory.getCurrentSession();
		session.delete(obj);
	}

	@Override
	public void evict(Object obj) {
		log.debug("Evicting object: " + obj.getClass());
		Session session = sessionFactory.getCurrentSession();
		session.evict(obj);
	}
	
	@Override
	public <T> T merge(Object obj) {
		log.debug("Merging object: " + obj.getClass());
		Session session = sessionFactory.getCurrentSession();
		T o = (T) session.merge(obj);
		return o;
	}
	
	@Override
	public <T> List<T> get(Class<T> clazz) {
		Session session = sessionFactory.getCurrentSession();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<T> query = builder.createQuery(clazz);
		query.from(clazz);
		return session.createQuery(query).getResultList();
	}

	@Override
	public <T> T get(Class<T> clazz, Integer id){
		Session session = sessionFactory.getCurrentSession();
		return (T) session.load(clazz, id);
	}
}
