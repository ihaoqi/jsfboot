package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.module.type.ServiceDefinition;
import com.easyeip.jsfboot.core.module.type.ServiceOption;
import com.easyeip.jsfboot.core.module.type.schema.ServiceItemType;
import com.easyeip.jsfboot.core.module.type.schema.ServiceOptionType;
import com.easyeip.jsfboot.core.options.type.impl.ServiceOption2Impl;

public class ServiceDefinitionImpl implements ServiceDefinition {

	private ServiceItemType serviceType;
	
	private List<ServiceOption> options;
	
	public ServiceDefinitionImpl(ServiceItemType serviceType){
		this.serviceType = serviceType;
	}
	
	@Override
	public String toString() {
		return serviceType.getName();
	}

	@Override
	public String getServiceName() {
		return serviceType.getName();
	}

	@Override
	public String getServiceClass() {
		return serviceType.getClazz();
	}

//	@Override
//	public boolean isOverride() {
//		Boolean boo = serviceType.isOverride();
//		if (boo != null)
//			return boo;
//		// 默认为false
//		return false;
//	}

	@Override
	public String getExplain() {
		return serviceType.getExplain();
	}

	@Override
	public List<ServiceOption> getOptions() {
		if (options == null){
			options = new ArrayList<ServiceOption>();
			for (ServiceOptionType opt : serviceType.getOption()){
				options.add(new ServiceOption2Impl (opt, null));
			}
		}
		return options;
	}

}
