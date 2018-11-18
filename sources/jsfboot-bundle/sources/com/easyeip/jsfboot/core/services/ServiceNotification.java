package com.easyeip.jsfboot.core.services;

/**
 * 服务通知管理
 * @author ihaoqi
 *
 */
public interface ServiceNotification {	
	/**
	 * 添加监听器
	 * @param source
	 * @param notify
	 * @return
	 */
	NotifyCallback addServiceListener(String serviceName, NotifyCallback notify);
	NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify);
	
	/**
	 * 添加通知监听
	 * @param source 回调所在的对象
	 * @param notify 监听回调
	 * @param forType 监听的类型，为null 表示监听所有类型
	 */
	NotifyCallback addServiceListener(String serviceName, NotifyCallback notify, String forType);
	NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify, String forType);
	
	/**
	 * 移除相关联的所有监听
	 * @param notify 原监听器
	 */
	void removeServiceListener(NotifyCallback notify);
	
}
