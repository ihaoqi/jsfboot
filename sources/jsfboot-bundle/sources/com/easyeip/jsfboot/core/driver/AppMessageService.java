package com.easyeip.jsfboot.core.driver;

import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * APP运行时日志
 * @author ihaoqi
 *
 */
public interface AppMessageService {
	
	/**
	 * 添加消息
	 * @param level
	 * @param msg
	 */
	void add (Level level, String msg);
	
	void add (Logger logger, Level level, String msg);
	
	/**
	 * 添加消息服务中所有内容
	 * @param service
	 */
	void addAll (AppMessageService service);
	
	/**
	 * 取得消息列表
	 * @return
	 */
	Collection<AppRunMessage> getMessages();

	/**
	 * 允许的最大条数
	 * @return
	 */
	int getMaxCount ();
}
