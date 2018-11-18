package com.easyeip.jsfboot.core.secutiry;

/**
 * 安全实现上下文
 * @author ihaoqi
 *
 */
public interface JsfbootSecurityContext {
	
	/**
	 * 取得安全实现服务名称
	 * @return
	 */
	String getProviderName();
	
	/**
	 * 取得当前登录的账号
	 * @return
	 */
	AccountDetails getAuthentication();
}
