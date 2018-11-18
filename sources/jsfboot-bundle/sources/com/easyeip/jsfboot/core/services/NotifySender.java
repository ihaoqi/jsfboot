package com.easyeip.jsfboot.core.services;

import java.util.Map;

public interface NotifySender {
	/**
	 * 发送不带参数的通知
	 * @param type 通知类型（名称）
	 */
	void sendSyncNotify (String type);
	void sendAsyncNotify(String type);
	
	/**
	 * 发送服务通知
	 * @param type 通知类型（名称）
	 * @param param 通知参数，可能makeParam()生成，ServiceParams.asMap()
	 */
	void sendSyncNotify (String type, Map<String,Object> param);
	void sendAsyncNotify (String type, Map<String,Object> param);
}
