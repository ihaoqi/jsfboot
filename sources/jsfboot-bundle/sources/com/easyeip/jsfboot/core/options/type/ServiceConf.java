package com.easyeip.jsfboot.core.options.type;

import java.util.Map;

/**
 * 服务配置接口
 * @author ihaoqi
 *
 */
public interface ServiceConf {
	/**
	 * 取得服务名称
	 * @return
	 */
	String getName();
	
	/**
	 * 取得服务配置中的参数
	 * @param name
	 * @return 返回只读的服务参数，不存在就返回null
	 */
	ServiceParam getParam(String name);
	
	/**
	 * 取得服务中所有配置的参数
	 * @return 返回只读的参数MAP
	 */
	Map<String, ServiceParam> getParams ();
}
