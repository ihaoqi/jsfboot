package com.easyeip.jsfboot.core.jdbc;

import com.easyeip.jsfboot.core.module.type.DataSourceRequire;

/**
 * 模块中的数据源定义与绑定的真实数据源名称
 * @author ihaoqi
 *
 */
public interface ModuleDataSource {
	
	/**
	 * 取得所在的模块名称
	 * @return
	 */
	String getModuleName();
	
	/**
	 * 取得原始数据源定义
	 * @return
	 */
	DataSourceRequire getDefinition();
	
	/**
	 * 取得绑定的数据源名称
	 * @return
	 */
	String getDataSource();
}
