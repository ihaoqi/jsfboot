package com.easyeip.jsfboot.core.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.core.services.ServiceException;
import com.easyeip.jsfboot.core.services.ServiceManager;

/**
 * 供普通用户使用的服务管理器
 * @author ihaoqi
 *
 */
public abstract class GenericServiceManager extends DefaultNotification implements ServiceManager {
	
	Map<String, Object> serviceList = new HashMap<String, Object>();
	List<Object> initServiceList = new ArrayList<Object>();
	
	/**
	 * 添加服务到列表
	 * @param name
	 * @param object
	 */
	public void addNewService (String name, Object object){
		serviceList.put(name, object);
		initServiceList.add(object);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T getService(Class<T> serviceClazz) {
		return (T) getService(serviceClazz.getName());
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> T useService(Class<T> serviceClazz) throws ServiceException {
		return (T) useService(serviceClazz.getName());
	}

	@Override
	public Object getService(String serviceName) {
		return serviceList.get(serviceName);
	}

	@Override
	public Object useService(String serviceName) throws ServiceException {
		Object obj = getService(serviceName);
		if (obj == null)
			throw new ServiceException("服务'" + serviceName + "'不存在。");
		
		return obj;
	}

}
