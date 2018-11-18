package com.easyeip.jsfboot.web.faces;

import org.primefaces.application.exceptionhandler.ExceptionInfo;

public class JsfbootExceptionInfo extends ExceptionInfoWrapper {

	private static final long serialVersionUID = 1L;
	
	private String pageUrl;		// 出错的页面

	JsfbootExceptionInfo(ExceptionInfo wrapper) {
		super(wrapper);
	}

	public String getPageUrl() {
		return pageUrl;
	}

	public void setPageUrl(String pageUrl) {
		this.pageUrl = pageUrl;
	}
}
