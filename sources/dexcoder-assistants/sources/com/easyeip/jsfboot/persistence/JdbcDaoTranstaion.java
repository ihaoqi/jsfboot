package com.easyeip.jsfboot.persistence;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import com.dexcoder.assistant.persistence.DefaultJdbcDao;
import com.dexcoder.assistant.persistence.JdbcQuery;
import com.easyeip.jsfboot.persistence.TranstaionJdbcTemplate;

/**
 * jdbcDao事务处理
 * @author ihaoqi
 *
 */
public class JdbcDaoTranstaion {
	
	private Map<DataSource, TranstaionJdbcTemplate> transtaions;
	
	/**
	 * 构造函数
	 * @param list jdbcDao列表
	 */
	public JdbcDaoTranstaion(JdbcQuery ... list){
		transtaions = new HashMap<DataSource, TranstaionJdbcTemplate>();
		
		for (JdbcQuery jdbc : list){
			// 判断是否支持事务
			if (!(jdbc instanceof DefaultJdbcDao)){
				throw new RuntimeException ("不支持事务的JdbcDao实现。");
			}
			DefaultJdbcDao daoImpl = (DefaultJdbcDao)jdbc;
			if (!(daoImpl.getJdbcTemplate() instanceof TranstaionJdbcTemplate)){
				throw new RuntimeException ("需要类型为TranstaionJdbcTemplate的JdbcTemplate模板。");
			}
			
			// 生成事务
			TranstaionJdbcTemplate jdbcTemplate = (TranstaionJdbcTemplate) daoImpl.getJdbcTemplate();
			DataSource dataSource = jdbcTemplate.getDataSource();
			transtaions.put(dataSource, jdbcTemplate);
		}
		
		// 开始事务
		for (TranstaionJdbcTemplate jdbcDao : transtaions.values()){
			jdbcDao.beginTranstaion();
		}
	}

	public void commit() {
		for (TranstaionJdbcTemplate jdbcDao : transtaions.values()){
			jdbcDao.commit();
		}
	}

	public void rollback() {
		for (TranstaionJdbcTemplate jdbcDao : transtaions.values()){
			jdbcDao.rollback();
		}
	}
}
