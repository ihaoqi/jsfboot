package com.easyeip.jsfboot.core.options.impl;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.type.ModuleServicePack;
import com.easyeip.jsfboot.core.options.JsfbootOptions;
import com.easyeip.jsfboot.core.options.type.AppVersion;
import com.easyeip.jsfboot.core.options.type.ServiceConf;
import com.easyeip.jsfboot.core.options.type.impl.AppVersionImpl;
import com.easyeip.jsfboot.core.options.type.impl.ServiceConfImpl;
import com.easyeip.jsfboot.core.options.type.schema.JsfbootConfigureType;
import com.easyeip.jsfboot.core.options.type.schema.ServiceConfType;
import com.easyeip.jsfboot.core.services.GenericService;

/**
 * 控制台实现
 * @author ihaoqi
 *
 */
public class DefaultJsfbootOptions extends GenericService implements JsfbootOptions {

	private JsfbootConfigureType appConf;
	private AppVersion appVar;
	private Map<String,ServiceConf> serviceConfs;
	
	public DefaultJsfbootOptions (JsfbootConfigureType appConf){
		this.appConf = appConf;
		serviceConfs = new HashMap<String,ServiceConf>();
	}

	@Override
	public ServiceConf getServiceConf(Class<?> serviceClazz) {
		return getServiceConf(serviceClazz.getName());
	}

	@Override
	public ServiceConf getServiceConf(String serviceName) {
		
		ServiceConf conf = serviceConfs.get(serviceName);
		if (conf == null){
			
			// 从模块服务中查找服务
			ModuleServicePack findService = null;
			for (ModuleServicePack service : JsfbootContext.getDriver().getModuleManager().getAllService()){
				if (service.getDefinition().getServiceName().equals(serviceName)){
					findService = service;
					break;
				}
			}
			
			if (findService == null)
				return null;
			
			// 从app配置中查找服务
			ServiceConfType serviceConf = null;
			for (ServiceConfType scc : appConf.getServiceConf()){
				if (scc.getName().equals(serviceName)){
					serviceConf = scc;
					break;
				}
			}
			
			conf = new ServiceConfImpl (findService.getDefinition(), serviceConf);
			serviceConfs.put(serviceName, conf);
		}
		return conf;
	}

	@Override
	public AppVersion getAppVersion() {
		
		if (appVar == null){
			
			appVar = new AppVersionImpl (appConf.getAppVersion());
			
		}
		
		return appVar;
	}
}
