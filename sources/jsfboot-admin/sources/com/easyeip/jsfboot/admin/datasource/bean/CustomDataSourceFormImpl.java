package com.easyeip.jsfboot.admin.datasource.bean;

import com.easyeip.jsfboot.admin.datasource.service.DataSourceDefinition;
import com.easyeip.jsfboot.admin.datasource.service.DataSourceDefinitionImpl;


public class CustomDataSourceFormImpl extends DataSourceDefinitionImpl implements CustomDataSourceForm {

	String oldJndiName;
	String newName;
	
	public CustomDataSourceFormImpl(){
		super("");
	}

	public CustomDataSourceFormImpl(DataSourceDefinition src){
		super(src.getName(), src);
		
		// 初使化值
		this.oldJndiName = src.getName();
		this.newName = src.getName();
	}
	
	@Override
	public String getOldJndiName() {
		return this.oldJndiName;
	}
	
	@Override
	public String getName() {
		return this.newName;
	}

	@Override
	public void setName(String name) {
		this.newName = name;
	}
}
