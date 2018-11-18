package com.easyeip.jsfboot.core.jdbc;

import javax.sql.DataSource;

import com.dexcoder.assistant.persistence.JdbcDao;
import com.dexcoder.assistant.persistence.JdbcQuery;
import com.dexcoder.assistant.persistence.NameHandler;
import com.easyeip.jsfboot.persistence.DataSourceResolver;
import com.easyeip.jsfboot.persistence.JdbcDaoFactory;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;

/**
 * 支持jdbcDao的数据服务基类
 * @author liao
 *
 */
public abstract class JdbcDaoServiceBase extends GenericService implements DataSourceResolver {
	private ServiceContext context;
	private JdbcDaoFactory daoFactory;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		this.context = context;
		daoFactory = new JdbcDaoFactory (this, createNameHandler());
	}
	
	@Override
	public DataSource resolver() throws Exception{
		// 取得服务中的定义的数据源
		DataSourceService sourceService = context.getDriver().getDataSources();
		DataSource dataSource = sourceService.getDataSourceObject(context.getModule().getName());
		return dataSource;
	}
	
	/**
	 * 获取dao工厂
	 * @return
	 */
	protected JdbcDaoFactory getDaoFactory(){
		return this.daoFactory;
	}

	/**
	 * 取得dao
	 * @return
	 */
	public JdbcDao getJdbcDao() {
		return daoFactory.getJdbcDao();
	}
	
	public JdbcQuery getJdbcQuery() {
		return daoFactory.getJdbcDao();
	}
	
	/**
	 * 取得名称句柄
	 * @return
	 */
	public abstract NameHandler createNameHandler();
}
