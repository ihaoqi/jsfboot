package com.easyeip.jsfboot.admin.datasource.bean;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.jdbc.ModuleDataSource;
import com.easyeip.jsfboot.core.module.JsfbootModule;

public class JndiSourceBindRow {
	
	DataSourceService service;
	ModuleDataSource source;
	
	public JndiSourceBindRow (DataSourceService service, ModuleDataSource source){
		this.service = service;
		this.source = source;
	}

	public String getExplan() {
		return source.getDefinition().getExplan();
	}

	public String getDefaultJndi() {
		String bind = source.getDefinition().getDefault();
		if (StringKit.isEmpty(bind))
			return null;
		return bind;
	}

	public String getKey() {
		return source.getModuleName();
	}

	public String getModuleTitle() {
		JsfbootModule jm = JsfbootContext.getDriver().getModuleManager().findModuleByName(source.getModuleName());
		return jm.getModuleInfo().getModuleTitle();
	}

	public String getBindJndi() {
		String bind = source.getDataSource();
		if (StringKit.isEmpty(bind))
			return null;
		return bind;
	}
	
	public void setBindJndi(String jndiName){
		service.bindModuleDataSource(getKey(), jndiName);
	}

}
