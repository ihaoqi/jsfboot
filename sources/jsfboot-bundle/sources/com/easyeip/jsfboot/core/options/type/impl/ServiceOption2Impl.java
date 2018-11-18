package com.easyeip.jsfboot.core.options.type.impl;
import com.easyeip.jsfboot.core.module.type.schema.ServiceOptionType;
import com.easyeip.jsfboot.core.options.type.ServiceParam;
import com.easyeip.jsfboot.core.options.type.schema.ServiceParamType;

public class ServiceOption2Impl implements ServiceParam {
	
	private ServiceOptionType option;
	private ServiceParamType param;
	
	public ServiceOption2Impl(ServiceOptionType option, ServiceParamType param){
		this.param = param;
		this.option = option;
	}

	public ServiceOptionType getRowOption(){
		return option;
	}

	@Override
	public String getName() {
		if (param != null)
			return param.getName();
		return option.getName();
	}

	@Override
	public String getExplain() {
		if (option != null)
			return option.getExplain();
		return "";
	}

	@Override
	public String getValue() {
		if (param != null)
			return param.getValue();
		return option.getValue();
	}

	@Override
	public String getRefService() {
		if (param != null)
			return param.getRefService();
		return option.getRefService();
	}

	@Override
	public String getRefBean() {
		if (param != null)
			return param.getRefBean();
		return null;
	}

}
