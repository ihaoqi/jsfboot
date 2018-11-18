package com.easyeip.jsfboot.core.beans.impl;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.RuntimeInfo;
import com.easyeip.jsfboot.core.beans.JsfbootConfigBean;
import com.easyeip.jsfboot.core.options.type.AppVersion;

public class JsfbootConfigBeanImpl implements JsfbootConfigBean {

	@Override
	public AppVersion getAppVersion() {
		return JsfbootContext.getDriver().getSiteOptions().getAppVersion();
	}

	@Override
	public RuntimeInfo getRuntimeInfo() {
		return JsfbootContext.getDriver().getRuntimeInfo();
	}

}
