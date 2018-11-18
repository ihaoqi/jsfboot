package com.easyeip.jsfboot.core.jdbc;

import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 数据源管理
 * @author ihaoqi
 *
 */
public interface DataSourceService {

	public static final String NOTIFY_READY = "ready";	// 已准备好，可以用了

	/**
	 * 获取模块中定义的数据源列表（只读）
	 * @return
	 */
	List<ModuleDataSource> getModuleDataSources();
	
	/**
	 * 把一个数据源名称绑定到别名上
	 * @param moduleName	模块数据源名称，来源 ModuleDataSource.getModuleName()
	 * @param dataSourceName	要绑定的数据源名称
	 */
	void bindModuleDataSource (String moduleName, String dataSourceName);

	/**
	 * 取得绑定的数据源
	 * @param moduleName 模块数据源名称，来源 ModuleDataSource.getModuleName()
	 * @return
	 */
	DataSource getDataSourceObject(String moduleName) throws NamingException;
	
	/**
	 * 注册一个数据源查找器
	 * @param finder
	 */
	void registryFinder (DataSourceFinder finder);
	
	/**
	 * 取消注册
	 * @param finder
	 */
	void unregistryFinder(DataSourceFinder finder);
}
