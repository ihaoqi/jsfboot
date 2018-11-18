package com.easyeip.jsfboot.admin.schedule;

/**
 * 任务状态
 * @author liao
 *
 */
public interface ScheduleFuture {
	
	/**
	 * 取得任务描述
	 * @return
	 */
	String getExplain();
	
	/**
	 * 取得任务对象
	 * @return
	 */
	Runnable getTask();
	
	/**
	 * 任务是否已结束
	 * @return
	 */
	boolean isFinish ();

	/**
	 * 取得任务结束状态
	 * @return
	 */
	ScheduleState getState();
	
	/**
	 * 获取出错异常
	 * @return
	 */
	Exception getException();
}
