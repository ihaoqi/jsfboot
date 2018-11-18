package com.easyeip.jsfboot.core.module.type;

import java.util.List;

public interface PageAction {
	String getName();
	String getTitle();
	String getIcon();
	String getOutcome();
	
	List<String> getPublicPages();
}
