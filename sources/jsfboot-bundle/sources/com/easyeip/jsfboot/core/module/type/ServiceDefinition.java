package com.easyeip.jsfboot.core.module.type;

import java.util.List;

/**
 * 模块服务定义
 * @author ihaoqi
 *
 */
public interface ServiceDefinition {

	/**
	 * 取得服务名称，通常与类名称相同
	 * @return
	 */
	String getServiceName();
	
	/**
	 * 取得服务要创建的类名称，如com.easyeip.jsfboot.core.registry.JsfbootRegistry
	 * @return
	 */
	String getServiceClass();
	
//	boolean isOverride();
	
	String getExplain();
	
	/**
	 * 取得服务选项
	 * @return
	 */
	List<ServiceOption> getOptions();
}
