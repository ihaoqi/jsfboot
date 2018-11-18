package com.easyeip.jsfboot.core.module.impl;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.ModuleServicePack;
import com.easyeip.jsfboot.core.module.type.ServiceDefinition;

public class ModuleServicePackImpl implements ModuleServicePack {

	private JsfbootModule pack;
	private ServiceDefinition service;
	
	public ModuleServicePackImpl(JsfbootModule pack, ServiceDefinition service){
		this.pack = pack;
		this.service = service;
	}
	
	@Override
	public String toString() {
		return service.getServiceName();
	}

	@Override
	public JsfbootModule getModule() {
		return pack;
	}

	@Override
	public ServiceDefinition getDefinition() {
		return service;
	}
}
