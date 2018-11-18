package com.easyeip.jsfboot.core.module.impl;

import java.net.URL;
import java.util.List;

public interface ModuleManagerAdmin {
	/**
	 * 加载模块配置文件
	 * @param moduleList
	 * @throws Exception
	 */
	void loadModuleList (List<URL> moduleList) throws Exception;
	
	/**
	 * 卸载所有模块
	 * @throws Exception
	 */
	void freeModuleList ();
}
