package com.easyeip.jsfboot.core.secutiry;

import javax.servlet.http.HttpServletRequest;

/**
 * jpn请求路径
 * @author ihaoqi
 *
 */
public interface JpnUrlRequestInfo {
	
	String getJpnUrl();
	
	HttpServletRequest getRequest();

}
