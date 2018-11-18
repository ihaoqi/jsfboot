package com.easyeip.jsfboot.core.services;

import com.easyeip.jsfboot.core.JsfbootDriver;
import com.easyeip.jsfboot.core.driver.AppMessageService;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.options.type.ServiceConf;

/**
 * 服务运行上下文
 * @author liao
 *
 */
public interface ServiceContext extends NotifySender, ServiceNotification{
	
	/**
	 * 取得驱动程序
	 * @return
	 */
	JsfbootDriver getDriver();
	
	/**
	 * 取得服务所在模块
	 * @return
	 */
	JsfbootModule getModule();
	
	/**
	 * 取得服务消息管理
	 * @return
	 */
	AppMessageService getAppMessage();
	
	/**
	 * 取得服务名称
	 * @return
	 */
	String getServiceName();
	
	/**
	 * 取得服务参数
	 * @return
	 */
	ServiceConf getServiceConf();
}
