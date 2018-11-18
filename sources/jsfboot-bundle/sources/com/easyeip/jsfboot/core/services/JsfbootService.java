package com.easyeip.jsfboot.core.services;

public interface JsfbootService {
	/**
	 * 初使化服务
	 * @throws Exception
	 */
	void startService(ServiceContext context) throws Exception;
	
	/**
	 * 停止服务
	 * @throws Exception 系统会忽略抛出的异常
	 */
	void stopService() throws Exception;
}
