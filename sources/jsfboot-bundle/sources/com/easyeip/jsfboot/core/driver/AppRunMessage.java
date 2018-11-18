package com.easyeip.jsfboot.core.driver;

import java.util.Date;

public interface AppRunMessage {

	Date getTime();
	
	String getLevel();
	
	String getMessage();
	
}
