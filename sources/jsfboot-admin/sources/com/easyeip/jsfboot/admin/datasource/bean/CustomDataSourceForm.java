package com.easyeip.jsfboot.admin.datasource.bean;

import com.easyeip.jsfboot.admin.datasource.service.DataSourceDefinition;

public interface CustomDataSourceForm extends DataSourceDefinition {

	String getOldJndiName();
	
	void setName (String name);
}
