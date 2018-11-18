package com.easyeip.jsfboot.core.services;

import java.util.Map;

/**
 * 链式服务通知参数
 * @author ihaoqi
 *
 */
public class ParamChain {
	
	public static ParamChain valueOf(String name, Object value){
		return new ParamChain(name, value);
	}
	
	public ParamChain(String name, Object value){
		add(name, value);
	}
	
	public Map<String,Object> asMap(){
		return null;
	}
	
	public ParamChain add(String name, Object value){
		return this;
	}
}
