package com.easyeip.jsfboot.core.driver.impl;

import java.util.Date;
import java.util.logging.Level;

import com.easyeip.jsfboot.core.driver.AppRunMessage;

public class AppRunMessageImpl implements AppRunMessage {
	
	private Date date;
	private Level level;
	private String msg;
	
	public AppRunMessageImpl(Level level, String msg){
		date = new Date ();
		this.level = level;
		this.msg = msg;
	}

	@Override
	public Date getTime() {
		return date;
	}

	@Override
	public String getLevel() {
		return level.getName();
	}

	@Override
	public String getMessage() {
		return msg;
	}

}
