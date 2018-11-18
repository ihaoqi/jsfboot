package com.easyeip.jsfboot.persistence;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import com.dexcoder.assistant.persistence.DefaultNameHandler;
import com.dexcoder.assistant.persistence.JdbcDao;
import com.dexcoder.assistant.persistence.DefaultJdbcDao;
import com.dexcoder.assistant.persistence.NameHandler;

/**
 * 支持数据表操作的jdbc
 * @author ihaoqi
 *
 */
public class JdbcDaoFactory {
	
	private DataSourceResolver context;
	private DefaultJdbcDao jdbcDao;
	private NameHandler nameHandler;
	
	public JdbcDaoFactory (DataSourceResolver context){
		this(context, new DefaultNameHandler());
	}
	
	public JdbcDaoFactory (DataSourceResolver context, NameHandler nameHandler){
		this.context = context;
		this.nameHandler = nameHandler;
	}
	
	/**
	 * 取得JdbcDao
	 * @return
	 * @throws RuntimeException
	 */
	public JdbcDao getJdbcDao() throws RuntimeException{
		if (jdbcDao == null){
			DefaultJdbcDao jdbc = new DefaultJdbcDao();
			try {
				setJdbcTemplate(jdbc, context, nameHandler);
				jdbcDao = jdbc;
			} catch (Exception e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return jdbcDao;
	}
	
	/**
	 * 执行事务，无异常则自动提交，否则回滚
	 * @return
	 */
	public void doTranstaion (Runnable action) throws Exception{
		JdbcDaoTranstaion jdt = new JdbcDaoTranstaion(getJdbcDao());
		try{
			action.run();
			jdt.commit();
		}catch (Throwable e){
			jdt.rollback();
			throw e;
		}
	}
	
	/**
	 * 启动一个事务，需自行提交或回滚
	 * @return
	 */
	public JdbcDaoTranstaion beginTranstaion(){
		return new JdbcDaoTranstaion(getJdbcDao());
	}
	
	/**
	 * 获取jdbc模版
	 * @return
	 */
	public JdbcTemplate getJdbcTemplate(){
		return (JdbcTemplate) jdbcDao.getJdbcTemplate();
	}
	
	/**
	 * 为JDBC设置数据源
	 * @param jdbcDao
	 * @param context
	 * @throws Exception 
	 */
	private void setJdbcTemplate(DefaultJdbcDao jdbcDao, DataSourceResolver context,
									NameHandler nameHandler) throws Exception {
		// 取得绑定的数据源
		DataSource dataSource = context.resolver();
		
		// 支持事务的数据源
		TranstaionJdbcTemplate jdbcTemplate = new TranstaionJdbcTemplate(dataSource);
		jdbcDao.setJdbcTemplate(jdbcTemplate);
		jdbcDao.setNameHandler(nameHandler);
	}

}
