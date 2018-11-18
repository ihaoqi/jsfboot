package com.easyeip.jsfboot.core.module.type.impl;

import com.easyeip.jsfboot.core.module.type.ModuleVersion;
import com.easyeip.jsfboot.core.module.type.schema.ModuleVersionType;

public class ModuleVersionImpl implements ModuleVersion {
	
	private ModuleVersionType verType;
	
	public ModuleVersionImpl (ModuleVersionType verType){
		this.verType = verType;
	}

	@Override
	public String getModuleName() {
		return verType.getModuleName();
	}

	@Override
	public String getModuleVersion() {
		return verType.getModuleVersion();
	}

	@Override
	public String getModuleTitle() {
		return verType.getModuleTitle();
	}

	@Override
	public String getModuleDescription() {
		return verType.getModuleDescription();
	}

	@Override
	public String getCompanyName() {
		return verType.getCompanyName();
	}

	@Override
	public String getCompanyDescription() {
		return verType.getCompanyDescription();
	}

}
