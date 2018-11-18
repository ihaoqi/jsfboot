package com.easyeip.jsfboot.core.driver.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyeip.jsfboot.core.driver.AppMessageService;
import com.easyeip.jsfboot.core.driver.AppRunMessage;

public class AppMessageServiceImpl implements AppMessageService {
	
	private ArrayList<AppRunMessage> msgList;
	
	public AppMessageServiceImpl(){
		msgList = new ArrayList<AppRunMessage>();
	}

	@Override
	public void add(Logger logger, Level level, String msg) {
		logger.log(level, msg);
		add (level, msg);
	}
	
	@Override
	public void add(Level level, String msg) {
		addMessage (new AppRunMessageImpl (level, msg));
	}

	@Override
	public Collection<AppRunMessage> getMessages() {
		return msgList;
	}

	@Override
	public void addAll(AppMessageService service) {
		
		// 返转消息
		ArrayList<AppRunMessage> newList = new ArrayList<AppRunMessage>();
		newList.addAll(service.getMessages());
		Collections.reverse(newList);
		
		// 添加到对列
		for (AppRunMessage msg : newList){
			addMessage (msg);
		}
	}
	
	@Override
	public int getMaxCount() {
		return 1000;
	}
	
	private void addMessage (AppRunMessage msg){
		msgList.add(0, msg);
		if (msgList.size() > getMaxCount()){
			msgList.remove(msgList.size() - 1);
		}
	}
}
