package com.easyeip.jsfboot.core.module.type;

/**
 * 模块内数据源别名
 * @author ihaoqi
 *
 */
public interface DataSourceRequire {

	/**
	 * 是否需要数据源
	 * @return
	 */
	boolean isRequired();
	
	/**
	 * 数据源说明
	 * @return
	 */
	String getExplan();
	
	/**
	 * 默认的数据源名称
	 * @return
	 */
	String getDefault();
}
