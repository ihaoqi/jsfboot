package com.easyeip.jsfboot.admin.datasource;

import java.util.List;

import com.easyeip.jsfboot.admin.datasource.service.DataSourceDefinition;
import com.easyeip.jsfboot.core.services.JsfbootService;

/**
 * 自定义数据源服务
 * @author ihaoqi
 *
 */
public interface CustomDataSourceService extends JsfbootService {
	
	/**
	 * 获取自定义数据源名称列表
	 * @return
	 */
	List<String> getDataSourceList();

	/**
	 * 取得指定数据源定义
	 * @param name 数据源名称
	 * @return 不存在返回null
	 */
	DataSourceDefinition getCustomDataSource (String name);
	
	/**
	 * 创建一个不存在的数据源
	 * @param name
	 * @return
	 * @throws Exception
	 */
	DataSourceDefinition createCustomDataSource (String name) throws Exception;
	
	/**
	 * 更新已存在的数据源定义
	 * @param definition
	 * @throws Exception
	 */
	void updateCustomDataSource(DataSourceDefinition definition) throws Exception;
	
	/**
	 * 移除已存在的数据源定义
	 * @param name
	 * @return
	 */
	boolean removeCustomDataSource(String name);
}
