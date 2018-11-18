package com.easyeip.jsfboot.core.registry.service;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.services.ServiceContext;

public interface RegistryAdmin {
	/**
	 * 准备好可以使用了
	 * @return
	 */
	void makeReady(ServiceContext callContext) throws RegistryException;
	/**
	 * 关闭注册表，不再使用了
	 */
	void close ();
}
