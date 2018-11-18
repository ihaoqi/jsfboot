package com.easyeip.jsfboot.core.options.type.impl;

import com.easyeip.jsfboot.core.options.type.AppVersion;
import com.easyeip.jsfboot.core.options.type.schema.AppVersionType;

public class AppVersionImpl implements AppVersion {

	private AppVersionType appVersion;
	
	public AppVersionImpl(AppVersionType appVersion) {
		this.appVersion = appVersion;
	}

	@Override
	public String getProductName() {
		return appVersion.getProductName();
	}

	@Override
	public String getProductVersion() {
		return appVersion.getProductVersion();
	}

	@Override
	public String getProductTitle() {
		return appVersion.getProductTitle();
	}

	@Override
	public String getProductDescription() {
		return appVersion.getProductDescription();
	}

	@Override
	public String getCompanyName() {
		return appVersion.getCompanyName();
	}

	@Override
	public String getCompanyDescription() {
		return appVersion.getCompanyDescription();
	}

}
