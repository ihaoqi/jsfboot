package com.easyeip.jsfboot.core.services;

import java.util.Map;

public interface NotifyCallback {
	
	/**
	 * 通知回调处理
	 * @param sender 通知源，通常是服务本身
	 * @param type 通知类型
	 * @param param 通知参数，可能为null
	 */
	void notifyCallback(Object sender, String type, Map<String,Object> param);
}
