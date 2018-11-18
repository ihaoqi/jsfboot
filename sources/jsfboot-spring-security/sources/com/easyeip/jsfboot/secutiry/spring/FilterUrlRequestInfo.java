package com.easyeip.jsfboot.secutiry.spring;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.web.FilterInvocation;

import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;

public class FilterUrlRequestInfo implements JpnUrlRequestInfo {
	
	private FilterInvocation filter;
	
	public FilterUrlRequestInfo(FilterInvocation filter){
		this.filter = filter;
	}

	@Override
	public String getJpnUrl() {
		return filter.getRequestUrl();
	}

	@Override
	public HttpServletRequest getRequest() {
		return filter.getHttpRequest();
	}

}
