package com.easyeip.jsfboot.admin.datasource.service;

import java.util.Map;

public interface DataSourceDefinition {
	/**
	 * 获取属性列表
	 * @return
	 */
	Map<String,String> allProperty();

	String getName();
	
	String getExplain();
	void setExplain(String explain);
	
	String getDriverClassName();
	void setDriverClassName(String value);
	
	String getConnectionUrl();
	void setConnectionUrl(String value);
	
	String getUsername();
	void setUsername (String name);
	
	String getPassword();
	void setPassword (String pwd);
	
	String getInitialSize();
	void setInitialSize(String value);
	
	String getMinIdle();
	void setMinIdle(String value);
	
	String getMaxActive();
	void setMaxActive(String value);
	
	String getMaxWait();
	void setMaxWait(String value);
	
	String getQueryTimeout();
	void setQueryTimeout(String value);
	
	String getFilters();
	void setFilters (String filters);
	
	String getMoveParams();
	void setMoveParams (String params);
}
