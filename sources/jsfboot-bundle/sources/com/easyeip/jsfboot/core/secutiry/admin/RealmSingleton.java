package com.easyeip.jsfboot.core.secutiry.admin;

import com.easyeip.jsfboot.core.module.type.AccountRealmPack;

/**
 * 用户适配器实例
 * @author ihaoqi
 *
 */
public interface RealmSingleton extends AccountRealmPack {
	
	/**
	 * 取得名称
	 * @return
	 */
	String getName();

	/**
	 * 判断是否已创建用户适配器实现
	 * @return
	 */
	boolean hasBeenCreated();
	
	/**
	 * 获取用户存储实现
	 * @return
	 */
	AccountRealm getInstance ();
	
	/**
	 * 创建实例
	 * @return
	 * @throws Exception
	 */
	AccountRealm createInstance() throws Exception;
}
