package com.easyeip.jsfboot.core.beans;

import com.easyeip.jsfboot.core.RuntimeInfo;
import com.easyeip.jsfboot.core.options.type.AppVersion;

public interface JsfbootConfigBean {

	/**
	 * 取得app版本信息
	 * @return
	 */
	AppVersion getAppVersion();
	
	/**
	 * 取得运行时信息
	 * @return
	 */
	RuntimeInfo getRuntimeInfo();
}
