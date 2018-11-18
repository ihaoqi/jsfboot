package com.easyeip.jsfboot.core.services.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.type.ModuleServicePack;
import com.easyeip.jsfboot.core.options.type.ServiceConf;
import com.easyeip.jsfboot.core.services.JsfbootService;
import com.easyeip.jsfboot.core.services.ServiceException;
import com.easyeip.jsfboot.core.services.ServiceManager;

/**
 * jsfboot服务管理器
 * @author ihaoqi
 *
 */
public class DefaultServiceManager extends GenericServiceManager implements ServiceManagerAdmin {
	
	static Logger logger = Logger.getLogger(DefaultServiceManager.class.getName());
	
	private class ServiceEntry{
		public String name;
		public Object service;
		public ModuleServicePack msi;
		
		@Override
		public String toString() {
			return name;
		}
	}
	
	// 正在初使化对列中的服务对列
	private Map<String, ServiceEntry> waitInitQueue;
	// 正在初使化的服务对列
	private Map<String, Object> inInitQueue;
		
	public DefaultServiceManager(){
	}

	@Override
	public void startAllService(ModuleManager modmgr) throws ServiceException{
		
		// 判断服务是否有名字冲突，取出要初使化的服务来
		Map<String, ModuleServicePack> validList = new HashMap<String, ModuleServicePack>();
		
		for (ModuleServicePack msi : modmgr.getAllService()){
			
			String name = msi.getDefinition().getServiceName();
			
			if (super.getService(name) != null)
				continue;
			
			if (validList.get(name) == null){
				validList.put(name, msi);
				continue;
			}
			
			// 判断是否可以覆盖之前的服务
			String old_proudct_name = validList.get(name).getModule().getModuleInfo().getModuleName();
			String new_proudct_name = msi.getModule().getModuleInfo().getModuleName();

			throw new ServiceException(old_proudct_name +
					"与" + new_proudct_name + "中存在同名服务'" + name + "。");
		}
		validList.clear();
		
		// 创建所有服务
		waitInitQueue = new HashMap<String, ServiceEntry>();

		for (ModuleServicePack msi : modmgr.getAllService()){

			try {
				ServiceEntry entry = new ServiceEntry();
				entry.service = createService (msi);
				entry.name = msi.getDefinition().getServiceName();
				entry.msi = msi;
				waitInitQueue.put(msi.getDefinition().getServiceName(), entry);
			}catch(Throwable t){
				throw new ServiceException (longServiceName(msi) + "服务创建失败，" + t.getMessage());
			}
		}
		
		// 解析所有注解，并启动服务
		inInitQueue = new HashMap<String, Object>();
		for (ModuleServicePack msi : modmgr.getAllService()){
			
			ServiceEntry entry = waitInitQueue.get(msi.getDefinition().getServiceName());
			if (entry == null) continue;
			
			// 如果已启动或失败了就跳过
			if (super.getService(entry.name) != null)
				continue;
			
			startServiceAt(entry, waitInitQueue, inInitQueue);
		}
		
		// 发送完成异步通知
		inInitQueue = null;
		waitInitQueue = null;
		this.sendNotify(ServiceManager.class.getName(), ServiceManager.NOTIFY_FINISH, true);
	}
	
	@Override
	public Object getService(String serviceName) {
		Object service = super.getService(serviceName);
		
		// 判断是否还在初使化列表中，如果还在就先初使它
		if (service == null && waitInitQueue != null){
			ServiceEntry entry = waitInitQueue.get(serviceName);
			if (entry != null){
				try {
					service = startServiceAt (entry, waitInitQueue, inInitQueue);
				} catch (ServiceException e) {
					return null;
				}
			}
		}
		
		return service;
	}
	
	/**
	 * 解析所有注解，并启动服务
	 * @param modmgr
	 * @param serviceList 可启动的服务列表
	 */
	private Object startServiceAt(ServiceEntry entry, 
			Map<String, ServiceEntry> serviceList,
			Map<String, Object> startOrder) throws ServiceException{

		// 添加到对列
		startOrder.put(entry.name, entry);
		try{
			
			// 解析注解
			setServiceAnnot (entry, serviceList, startOrder);
	
			// 启动服务
			if (entry.service instanceof JsfbootService){
				
				JsfbootService jsfbootService = (JsfbootService) entry.service;
				
				ServiceConf config = JsfbootContext.getDriver().getSiteOptions().getServiceConf(entry.name);
				jsfbootService.startService(new ServiceContextImpl(entry.msi.getModule(), this, config));
				
			}
			
			// 添加到服务列表
			this.addNewService(entry.name, entry.service);
			
		}catch (NoClassDefFoundError e){
			throw new ServiceException (longServiceName(entry.msi) + "服务启动失败，" + e.getMessage() + " 类不存在。");
		}catch (Throwable e){
			throw new ServiceException (longServiceName(entry.msi) + "服务启动失败，" + e.getMessage());
		}finally{
			// 从对列移除
			startOrder.remove(entry.name);
		}
		
		return entry.service;
	}
	
	/**
	 * 设置服务注解
	 * @param entry
	 * @param serviceList
	 * @param startOrder
	 */
	private void setServiceAnnot(ServiceEntry entry, 
			Map<String, ServiceEntry> serviceList,
			Map<String, Object> startOrder) throws Exception{
		
		AnnotationHelper.setUseJsfbootServiceAnnot(this, entry.service);

	}
	
//	private void setStartStatus(ModuleServicePack msi, boolean status, String error){
//		ModuleServicePackAdmin amsi = (ModuleServicePackAdmin) msi;
//		amsi.setStartStatus(status, error);
//	}
	
	@Override
	public void stopAllService(ModuleManager modmgr) {

		// 关闭通知
		closeNotification();
		
		// 停止服务，从后向前停止
		for (int i = initServiceList.size() - 1; i >= 0; i --){

			Object serviceObject = initServiceList.get(i);
			
			try {

				if (serviceObject instanceof JsfbootService){
					
					JsfbootService jsfbootService = (JsfbootService) serviceObject;
					jsfbootService.stopService();
				
				}
				
			}catch (Exception e){
				logger.log(Level.SEVERE, serviceObject.getClass().getName() + "服务停止失败，", e);
			}
		}
	}
	
	@Override
	Object getNotifyService(String serviceName) {
		if (StringKit.equals(serviceName, ServiceManager.class.getName()))
			return this;
		return this.getService(serviceName);
	}
	
	private String longServiceName(ModuleServicePack msi){
		return msi.getModule().getModuleInfo().getModuleName() + "/" + msi.getDefinition().getServiceName();
	}
	
	/**
	 * 创建单个服务
	 * @param msi
	 * @throws Exception
	 */
	private Object createService (ModuleServicePack msi) throws Exception{
		
		// 创建服务对应的类
		String clazz = msi.getDefinition().getServiceClass();
		
		Class<?> obj;
		try {
			obj = Thread.currentThread().getContextClassLoader().loadClass(clazz);
		}catch (NoClassDefFoundError e){
			throw new Exception(clazz + " 类不存在。");
		} catch (ClassNotFoundException e) {
			throw new Exception(clazz + " 类不存在。");
		}
		
		return obj.newInstance();
	}
}
