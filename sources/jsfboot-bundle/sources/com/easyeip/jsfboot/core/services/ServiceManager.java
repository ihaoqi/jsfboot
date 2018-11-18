package com.easyeip.jsfboot.core.services;

/**
 * jsfboot服务管理
 * @author ihaoqi
 *
 */
public interface ServiceManager {
	
	// 服务全部初使化完成通知
	static final String NOTIFY_FINISH = "finish";

	/**
	 * 按接口名称取得服务
	 * @param serviceClazz 接口类
	 * @return 成功返回服务，失败返回Null
	 */
	<T> T getService (Class<T> serviceClazz);
	
	/**
	 * 按接口名称取得服务
	 * @param serviceClazz 接口类
	 * @return 成功返回服务，失败抛异常
	 * @throws ServiceException 如果服务不存在会抛异常
	 */
	<T> T useService (Class<T> serviceClazz) throws ServiceException;
	
	/**
	 * 按服务名称取得服务
	 * @param serviceName 服务名称
	 * @return 成功返回服务，失败返回Null
	 */
	Object getService (String serviceName);
	
	/**
	 * 按服务名称取得服务
	 * @param serviceName 服务名称
	 * @return 成功返回服务，失败返回Null
	 * @throws ServiceException 如果服务不存在会抛异常
	 */
	Object useService (String serviceName) throws ServiceException;
}
