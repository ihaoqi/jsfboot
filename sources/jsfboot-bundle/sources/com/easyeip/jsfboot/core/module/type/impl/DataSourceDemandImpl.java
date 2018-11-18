package com.easyeip.jsfboot.core.module.type.impl;

import com.easyeip.jsfboot.core.module.type.DataSourceRequire;
import com.easyeip.jsfboot.core.module.type.schema.DataSourceType;

public class DataSourceDemandImpl implements DataSourceRequire {
	
	DataSourceType src;
	
	public DataSourceDemandImpl(DataSourceType src){
		this.src = src;
	}

	@Override
	public String getExplan() {
		return src.getExplain();
	}

	@Override
	public String getDefault() {
		return src.getDefault();
	}

	@Override
	public boolean isRequired() {
		return src.isRequired();
	}

}
