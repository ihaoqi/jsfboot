package com.easyeip.jsfboot.core.options.type.impl;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.core.module.type.ServiceDefinition;
import com.easyeip.jsfboot.core.module.type.ServiceOption;
import com.easyeip.jsfboot.core.options.type.ServiceConf;
import com.easyeip.jsfboot.core.options.type.ServiceParam;
import com.easyeip.jsfboot.core.options.type.schema.ServiceConfType;
import com.easyeip.jsfboot.core.options.type.schema.ServiceParamType;

public class ServiceConfImpl implements ServiceConf {
	
	private ServiceDefinition serviceInfo;
	private ServiceConfType serviceConf;
	
	private Map<String, ServiceParam> options;

	public ServiceConfImpl(ServiceDefinition serviceInfo, ServiceConfType serviceConf) {
		this.serviceInfo = serviceInfo;
		this.serviceConf = serviceConf;
		options = null;
	}

	@Override
	public String getName() {
		return serviceInfo.getServiceName();
	}

	@Override
	public ServiceParam getParam(String name) {
		return getParams().get(name);
	}

	@Override
	public Map<String, ServiceParam> getParams() {
		
		if (options != null)
			return options;
		
		options = new HashMap<String, ServiceParam> ();
		
		// 聚合参数
		for (ServiceOption opt : serviceInfo.getOptions()){
		
			// 查找 app 中的同名配置
			ServiceParamType appParam = null;
			if (serviceConf != null){
				for (ServiceParamType param : serviceConf.getServiceParam()){
					if (param.getName().equals(opt.getName())){
						appParam = param;
						break;
					}
				}
			}
			
			// 生成新的参数组合
			ServiceOption2Impl rowOption = (ServiceOption2Impl) opt;
			options.put(opt.getName(), new ServiceOption2Impl (rowOption.getRowOption(), appParam));
			
		}
		
		return options;
	}

}
