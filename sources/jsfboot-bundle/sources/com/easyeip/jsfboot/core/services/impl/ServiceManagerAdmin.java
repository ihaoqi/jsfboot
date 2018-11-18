package com.easyeip.jsfboot.core.services.impl;

import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.services.ServiceException;

public interface ServiceManagerAdmin {

	/**
	 * 启动所有服务
	 * @param modmgr	管理器功能
	 * @return 返回成功启动的服务个数
	 */
	void startAllService (ModuleManager modmgr) throws ServiceException;
	
	/**
	 * 停止所有服务
	 * @param modmgr
	 */
	void stopAllService (ModuleManager modmgr);
}
