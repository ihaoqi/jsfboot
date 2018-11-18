package com.easyeip.jsfboot.admin.datasource.service;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSourceFactory;

/**
 * 创建自定义的数据源
 * @author ihaoqi
 *
 */
public class CustomDataSourceFactory {
	
	/**
	 * 创建Druid数据源
	 * @param definition
	 * @return
	 */
	public static DataSource create (DataSourceDefinition definition) throws Exception{
		// TODO 创建Druid数据源
		return DruidDataSourceFactory.createDataSource(definition.allProperty());
	}
}
