package com.easyeip.jsfboot.core.services.impl;

import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.JsfbootDriver;
import com.easyeip.jsfboot.core.driver.AppMessageService;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.options.type.ServiceConf;
import com.easyeip.jsfboot.core.services.NotifyCallback;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceManager;
import com.easyeip.jsfboot.core.services.ServiceNotification;

public class ServiceContextImpl implements ServiceContext {
	
	JsfbootModule module;
	ServiceManager mgr;
	ServiceConf conf;
	AppMessageService msg;
	ServiceNotification notification;

	public ServiceContextImpl(JsfbootModule module, ServiceManager mgr, ServiceConf conf){
		this.module = module;
		this.mgr = mgr;
		this.msg = (AppMessageService) mgr.getService(AppMessageService.class);
		this.conf = conf;
		
		if (mgr instanceof ServiceNotification){
			notification = (ServiceNotification) mgr;
		}
	}

	@Override
	public String getServiceName() {
		return conf.getName();
	}

	@Override
	public ServiceConf getServiceConf() {
		return conf;
	}

	@Override
	public JsfbootDriver getDriver() {
		return JsfbootContext.getDriver();
	}

	@Override
	public AppMessageService getAppMessage() {
		return msg;
	}

	@Override
	public void sendSyncNotify(String type) {
		DefaultNotification dn = (DefaultNotification) mgr;
		dn.sendNotify(getServiceName(), type, false);
	}

	@Override
	public void sendSyncNotify(String type, Map<String, Object> param) {
		DefaultNotification dn = (DefaultNotification) mgr;
		dn.sendNotify(getServiceName(), type, param, false);
	}
	
	@Override
	public void sendAsyncNotify(String type) {
		DefaultNotification dn = (DefaultNotification) mgr;
		dn.sendNotify(getServiceName(), type, true);
	}

	@Override
	public void sendAsyncNotify(String type, Map<String, Object> param) {
		DefaultNotification dn = (DefaultNotification) mgr;
		dn.sendNotify(getServiceName(), type, param, true);
	}

	@Override
	public NotifyCallback addServiceListener(String serviceName, NotifyCallback notify) {
		return notification.addServiceListener(serviceName, notify);
	}

	@Override
	public NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify) {
		return notification.addServiceListener(serviceClazz, notify);
	}

	@Override
	public NotifyCallback addServiceListener(String serviceName, NotifyCallback notify, String forType) {
		return notification.addServiceListener(serviceName, notify, forType);
	}

	@Override
	public NotifyCallback addServiceListener(Class<?> serviceClazz, NotifyCallback notify, String forType) {
		return notification.addServiceListener(serviceClazz, notify, forType);
	}

	@Override
	public void removeServiceListener(NotifyCallback notify) {
		notification.removeServiceListener(notify);
	}

	@Override
	public JsfbootModule getModule() {
		return module;
	}

}
