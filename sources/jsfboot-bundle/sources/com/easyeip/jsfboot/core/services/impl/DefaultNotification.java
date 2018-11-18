package com.easyeip.jsfboot.core.services.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyeip.jsfboot.core.services.NotifyCallback;
import com.easyeip.jsfboot.core.services.ServiceNotification;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 服务通知管理
 * @author ihaoqi
 *
 */
abstract class DefaultNotification implements ServiceNotification{
	
	static Logger logger = Logger.getLogger(DefaultNotification.class.getName());
	
	private static final String FOR_ALLTYPE = "~";

	private static final Map<String,Object> nullParam = new HashMap<String, Object>();
	
	private Map<String, ServiceNotifyMap> notifyMap;
	private ExecutorService singleThreadExecutor;
		
	public DefaultNotification(){
		notifyMap = new HashMap<String, ServiceNotifyMap>();
	}
	
	private class ServiceNotifyMap extends HashMap<String, NotifyCallback>{
		private static final long serialVersionUID = 1L;
	}

	public void sendNotify(String serviceName, String type, boolean asyncSender) {
		sendNotify (serviceName, type, Collections.unmodifiableMap(nullParam), asyncSender);
	}
	
	void closeNotification(){
		if (singleThreadExecutor != null){
			singleThreadExecutor.shutdownNow();
			singleThreadExecutor = null;
			notifyMap.clear();
		}
	}

	abstract Object getNotifyService(String serviceName);

	/**
	 * 发送服务通知
	 * @param serviceName
	 * @param type
	 * @param param
	 * @param asyncSender
	 */
	public void sendNotify(String serviceName, final String type,
			final Map<String, Object> param, boolean asyncSender) {
		
		final ServiceNotifyMap snm = notifyMap.get(serviceName);
		if (snm == null)
			return;
		
		// 先看看有没有所有类型的监听
		final NotifyCallback callback1 = snm.get(FOR_ALLTYPE);
		final NotifyCallback callback2 = snm.get(type);
		if (callback1 == null && callback2 == null)
			return;
		final Object notifyService = getNotifyService(serviceName);
		
		// 创建任务
		Runnable task = new Runnable() {  
            @Override  
            public void run() {
            	try{
	    			if (callback1 != null){
	    				callback1.notifyCallback(notifyService, type, param);
	    			}
	    			if (callback2 != null){
	    				callback2.notifyCallback(notifyService, type, param);
	    			}
            	}catch (Exception e){
            		logger.log(Level.SEVERE, e.getMessage(), e);
            	}
            }
        };  
        
        // 执行任务
		if (asyncSender == false){
			// 同步
			task.run();
			return;
		}
		
		// 异步
		try{
			// 创建一个用于异步通信的线程
			if (singleThreadExecutor == null){
				singleThreadExecutor = Executors.newSingleThreadExecutor();
			}
			singleThreadExecutor.execute(task);
		}catch (Exception e){
			logger.log(Level.SEVERE, e.getMessage(), e);
		}
	}
	
	@Override
	public NotifyCallback addServiceListener(String serviceName, NotifyCallback notify) {
		return addServiceListener(serviceName, notify, FOR_ALLTYPE);
	}

	@Override
	public NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify) {
		return addServiceListener(serviceClazz.getName(), notify, FOR_ALLTYPE);
	}

	@Override
	public NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify, String forType) {
		return addServiceListener(serviceClazz.getName(), notify, forType);
	}
	
	@Override
	public NotifyCallback addServiceListener(String serviceName, NotifyCallback notify, String forType) {
		ServiceNotifyMap snm = notifyMap.get(serviceName);
		if (snm == null){
			snm = new ServiceNotifyMap();
			notifyMap.put(serviceName, snm);
		}
		
		snm.put(forType, notify);
		return notify;
	}

	@Override
	public void removeServiceListener(NotifyCallback notify) {
		for (Entry<String, ServiceNotifyMap> sermap : notifyMap.entrySet()){
			
			ServiceNotifyMap notifyMap = sermap.getValue();
			
			for(Entry<String, NotifyCallback> notmap : notifyMap.entrySet()){
				
				if (notmap.getValue() == notify){
					notifyMap.remove(notmap.getKey());
					return;
				}
				
			}
			
		}
	}
}
