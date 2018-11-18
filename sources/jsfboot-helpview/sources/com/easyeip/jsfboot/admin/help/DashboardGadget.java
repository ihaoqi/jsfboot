package com.easyeip.jsfboot.admin.help;

import com.easyeip.jsfboot.utils.KeyValuePair;

public interface DashboardGadget {
	
	int getPosition();
	
	String getTitle();
	
	String getIcon();

	String getUrl();
	DashboardGadget setUrl(String url);
	
	String getOutcome();
	DashboardGadget setOutcome(String outcome);
	
	KeyValuePair getParam();
	
	DashboardGadget setParam (String name, String value);
}
