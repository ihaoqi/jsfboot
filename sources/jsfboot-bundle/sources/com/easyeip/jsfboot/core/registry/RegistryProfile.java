package com.easyeip.jsfboot.core.registry;

public interface RegistryProfile {

	/**
	 * 取得名称最大字数
	 * @return
	 */
	int getMaxNameLength();
	
	/**
	 * 取得值最大可存的字数
	 * @return
	 */
	int getMaxValueLength();
	
	/**
	 * 取得最大备注字数
	 * @return
	 */
	int getMaxCommentLength();
	
	/**
	 * 取得最大结点深度
	 * @return
	 */
	int getMaxNodeDepth();
}
