package com.easyeip.jsfboot.core.options;

import com.easyeip.jsfboot.core.options.type.AppVersion;
import com.easyeip.jsfboot.core.options.type.ServiceConf;

public interface JsfbootOptions {
	// 配置已被修改
	public static final String NOTIFY_CONF_MODIFY = "conf_modify";
	
	/**
	 * 取得应用程序版本信息
	 * @return
	 */
	AppVersion getAppVersion();
	
	/**
	 * 按接口名称查找服务对应的配置
	 * @param serviceClazz
	 * @return 返回配置对象，没有就返回null
	 */
	ServiceConf getServiceConf(Class<?> serviceClazz);
	
	/**
	 * 按名称查找指定服务对应的配置
	 * @param serviceName
	 * @return 返回配置对象，没有就返回null
	 */
	ServiceConf getServiceConf (String serviceName);
}
