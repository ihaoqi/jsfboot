package com.easyeip.jsfboot.admin.datasource.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

/**
 * 数据源管理
 * @author ihaoqi
 *
 */
public interface AdminDataSourceBean {

	/**
	 * 取得系统中可配置的源列表
	 * @return
	 */
	List<JndiSourceBindRow> getBindSourceList();
	
	/**
	 * 取得自定义数据源列表
	 * @return
	 */
	List<CustomDataSourceForm> getCustomDataSources();
	
	/**
	 * 取得自定义表单
	 * @return
	 */
	CustomDataSourceForm getCustomForm();
	

	/**
	 * 开始保存自定义数据源
	 */
	String doSaveCustomSource();
	
	/**
	 * 删除选中数据源
	 * @param event
	 */
	void doDeleteCustomSource(ActionEvent event);
	
	/**
	 * 开始检测选中连接
	 * @param event
	 */
	void doCheckDataSource(ActionEvent event);

	/**
	 * 取得当前正在检测的消息
	 * @return
	 */
	List<String> getCheckMessages();
}
