package com.easyeip.jsfboot.core.secutiry.admin;

import java.util.List;

/**
 * 用户适配器管理
 * @author ihaoqi
 *
 */
public interface RealmManager {

	/**
	 * 取得所有适配器列表
	 * @return
	 */
	List<RealmSingleton> getRealmList();
	
	/**
	 * 添加一个适配器到可用表中
	 * @param name
	 */
	void selectCurrentRealm(String name) throws Exception;
	
	/**
	 * 取得当前适配器对象
	 * @return
	 */
	RealmSingleton getCurrentRealm(String name);
	
	/**
	 * 取得使用的适配列表
	 * @return
	 */
	List<RealmSingleton> getCurrentRealm();
}
