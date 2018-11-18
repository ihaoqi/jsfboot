package com.easyeip.jsfboot.core.jdbc;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据源查找器
 * @author ihaoqi
 *
 */
public interface DataSourceFinder {
	
	/**
	 * 按名称查找数据源
	 * @param dataSourceName 数据源名称
	 * @return 如果找到了对应的数据源，就返回非null对象，如果没找到就返回null
	 * @throws Exception 如果找到了，但创建出错就抛出异常
	 */
	DataSource getDataSourceObject(String dataSourceName) throws NamingException;

}
