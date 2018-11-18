package com.easyeip.jsfboot.core.services;

import com.easyeip.jsfboot.core.JsfbootContext;

/**
 * 适用于在srping bean配置中使用jsfboot服务，例如获取注册表服务的bean配置方法如下：
 * 
 * <bean id="registry" class="com.easyeip.jsfboot.core.services.StaticFinder" factory-method="getService">
 *   <constructor-arg value="com.easyeip.jsfboot.core.registry.RegistryService" />
 * </bean>
 * 
 * @author liao
 *
 */
public class StaticFinder {
	
	public static Object getService (String serviceName){
		
		return JsfbootContext.getService(serviceName);
		
	}

}
