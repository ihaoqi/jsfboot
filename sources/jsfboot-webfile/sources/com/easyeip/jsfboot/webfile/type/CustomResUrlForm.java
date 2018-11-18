package com.easyeip.jsfboot.webfile.type;

import com.easyeip.jsfboot.webfile.CustomResourceUrl;
import com.easyeip.jsfboot.webfile.web.WebfileResourceServlet;

public class CustomResUrlForm implements CustomResourceUrl {
	
	CustomResourceUrl url;
	boolean isInvalid = false;
	
	public CustomResUrlForm(CustomResourceUrl url, boolean isInvalid){
		this.url = url;
		this.isInvalid = isInvalid;
	}

	@Override
	public String getKey() {
		return url.getKey();
	}

	@Override
	public String getCustomUrl() {
		return url.getCustomUrl();
	}

	@Override
	public String getSourcePath() {
		return url.getSourcePath();
	}
	
	public String getDownloadPath(){
		return WebfileResourceServlet.JRESOURCE_SERVLET + url.getSourcePath();
	}

	/**
	 * 判断是否无效
	 * @return
	 */
	public boolean getIsInvalid(){
		return isInvalid;
	}
}
