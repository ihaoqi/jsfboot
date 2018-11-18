package com.easyeip.jsfboot.core;

import javax.servlet.ServletContext;

import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.options.JsfbootOptions;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.secutiry.SecutiryService;
import com.easyeip.jsfboot.core.services.ServiceManager;
import com.easyeip.jsfboot.core.surface.SurfaceService;

/**
 * @author ihaoqi
 *
 */
public interface JsfbootDriver {
	
	ServletContext getServletContext();
	
	/**
	 * 取得运行时信息
	 * @return
	 */
	RuntimeInfo getRuntimeInfo();
	
	/**
	 * 取得系统配置
	 * @return
	 */
	JsfbootOptions getSiteOptions();

	/**
	 * 取得模块管理
	 * @return
	 */
	ModuleManager getModuleManager();

	/**
	 * 取得服务管理
	 * @return
	 */
	ServiceManager getServiceManager();

	/**
	 * 取得注册表服务
	 * @return
	 */
	RegistryService getRegistryService();

	/**
	 * 取得安全管理服务
	 * @return
	 */
	SecutiryService getSecutiryService();
	
	/**
	 * 取得外观管理服务
	 * @return
	 */
	SurfaceService getSurfaceService();
	
	/**
	 * 获取数据源服务
	 * @return
	 */
	DataSourceService getDataSources();
	
	/**
	 * 取得Jsf或spring bean
	 * @param beanName
	 * @return
	 */
	Object getAppBean (String beanName);
}
