package com.easyeip.jsfboot.webfile.service;

import com.easyeip.jsfboot.webfile.CustomResourceUrl;

public class CustomResourceUrlImpl implements CustomResourceUrl {
	
	String customUrl;
	String filePath;
	String key;
	
	public CustomResourceUrlImpl(String key, String customUrl, String filePath){
		this.key = key;
		this.customUrl = customUrl;
		this.filePath = filePath;
	}

	@Override
	public String getCustomUrl() {
		return customUrl;
	}

	@Override
	public String getSourcePath() {
		return filePath;
	}

	@Override
	public String getKey() {
		return key;
	}

}
