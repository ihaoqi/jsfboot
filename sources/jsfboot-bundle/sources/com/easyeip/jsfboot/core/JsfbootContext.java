package com.easyeip.jsfboot.core;

/**
 * 系统入口
 * @author ihaoqi
 *
 */
public class JsfbootContext {
	
	private static JsfbootDriver driver;
	
	protected static void setDriver (JsfbootDriver driver){
		JsfbootContext.driver = driver;
	}
	
	/**
	 * 获取驱动
	 * @return
	 */
	public static JsfbootDriver getDriver(){
		return driver;
	}
	
	/**
	 * 使用服务类来取得服务
	 * @param serviceClazz
	 * @return
	 */
	public static <T> T getService (Class<T> serviceClazz){
		if (driver == null)
			return null;
		
		return driver.getServiceManager().getService(serviceClazz);
	}
	
	/**
	 * 使用服务名称来取得服务
	 * @param serviceName
	 * @return
	 */
	public static Object getService (String serviceName){
		if (driver == null)
			return null;
		
		return driver.getServiceManager().getService(serviceName);
	}
}
