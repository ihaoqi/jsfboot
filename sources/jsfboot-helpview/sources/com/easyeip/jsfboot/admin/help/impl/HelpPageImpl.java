package com.easyeip.jsfboot.admin.help.impl;

import com.easyeip.jsfboot.admin.help.PageLoadType;
import com.easyeip.jsfboot.admin.help.HelpPage;

public class HelpPageImpl implements HelpPage {
	
	private String url;
	private PageLoadType type;
	
	public HelpPageImpl(String url, PageLoadType type){
		this.url = url;
		this.type = type;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public PageLoadType getType() {
		return type;
	}
}
