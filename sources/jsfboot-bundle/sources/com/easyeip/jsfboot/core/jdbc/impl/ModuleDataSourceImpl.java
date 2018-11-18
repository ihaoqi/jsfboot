package com.easyeip.jsfboot.core.jdbc.impl;

import com.easyeip.jsfboot.core.jdbc.ModuleDataSource;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.DataSourceRequire;

public class ModuleDataSourceImpl implements ModuleDataSource {
	
	private JsfbootModule jm;
	private DataSourceRequire dsa;
	
	private String dataSource;

	public ModuleDataSourceImpl(JsfbootModule jm, DataSourceRequire dsa) {
		this.jm = jm;
		this.dsa = dsa;
	}

	@Override
	public String getDataSource() {
		return this.dataSource;
	}
	
	public void bindDataSource(String dataSource){
		this.dataSource = dataSource;
	}

	@Override
	public DataSourceRequire getDefinition() {
		return dsa;
	}

	@Override
	public String getModuleName() {
		return jm.getName();
	}

}
