package com.ks.service.impl;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.ks.dao.BaseDao;
import com.ks.service.BaseService;

public class BaseServiceImpl implements BaseService{

	static final org.apache.logging.log4j.Logger log = LogManager.getLogger();

	protected NamedParameterJdbcTemplate jdbcTemplate;
	
	@Autowired
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		log.debug("Datasource set...");
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	protected SessionFactory sessionFactory;

	@Autowired
	protected BaseDao baseDaoImpl;
	
	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public void reload(Object obj) {
		baseDaoImpl.reload(obj);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public Object save(Object obj) {
		// For debugging purposes .....
		
		/*log.warn("Saving Object: "+obj.getClass().getName());
		
		try {
			if(obj.getClass().getName().equals("com.pwc.ccs.db.domain.Contract")){
				throw new Exception();
			}
				
		} catch (Exception e) {
			try {
			Method getstatusMethod = obj.getClass().getMethod("getStatus");
			
			ContractStatusEnum commentText = (ContractStatusEnum)getstatusMethod.invoke(obj, null);
			log.warn("Saving contract with contract Status:"+commentText.getValue());
			} catch (Exception e1) {
				log.error("My message Method not found");
				e1.printStackTrace();
			 }
			log.warn("Call Stack trace");
			e.printStackTrace();
			return baseDaoImpl.save(obj);
		}*/
		return baseDaoImpl.save(obj);
		
		
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public void delete(Object obj) {
		baseDaoImpl.delete(obj);
	}
	
	@Override
	public void evict(Object obj) {
		baseDaoImpl.evict(obj);
	}
	
	/* (non-Javadoc)
	 */
	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public <T> T merge(Object obj) {
		return baseDaoImpl.merge(obj);
	}

	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public <T> List<T> get(Class<T> clazz) {
		return baseDaoImpl.get(clazz);
	}
	
	@Override
	@Transactional(rollbackFor = Throwable.class, propagation=Propagation.REQUIRED)
	public <T> T get(Class<T> clazz, Integer id) {
		return baseDaoImpl.get(clazz, id);
	}
	
	public boolean tableExists(String tableName){
		 try {
			 DatabaseMetaData metaData = dataSource.getConnection().getMetaData();
			 ResultSet tables = metaData.getTables(null, null, tableName, new String[] { "TABLE" } );
			 if (tables.next()){
				 return true;
			 }else{
				 return false;
			 }
		} catch (SQLException e) {
			log.error(e.getMessage(), e);
			return false;
		}
	}
}
