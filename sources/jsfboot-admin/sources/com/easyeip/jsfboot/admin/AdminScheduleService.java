package com.easyeip.jsfboot.admin;

import com.easyeip.jsfboot.admin.schedule.ScheduleFuture;

/**
 * 多线程任务管理器
 * @author liao
 *
 */
public interface AdminScheduleService {
	
	/**
	 * 取得沲中的线程数量
	 * @return
	 */
	int getPoolThreads();
	
	/**
	 * 立即启动一个任务，只执行一次
	 * @param task 要执行的任务
	 * @param explain 任务描述
	 * @return 返回任务执行状态
	 */
	ScheduleFuture schedule (Runnable task, String explain);
	ScheduleFuture schedule (Runnable task);

	/**
	 * 等待任务结束
	 * @param state 要等待的任务
	 * @param timeout 等待时间（毫秒）
	 * @return 返回等待的任务状态
	 */
	ScheduleFuture waitFinish (ScheduleFuture state, long timeout);
	
	/**
	 * 等待任务结束，直到出错或结束
	 * @param state 要等待的任务
	 * @return 返回等待的任务状态
	 */
	ScheduleFuture waitFinish (ScheduleFuture state);

	/**
	 * 取消一个任务
	 * @param state 要终止的任务
	 * @return 返回等待的任务状态
	 */
	ScheduleFuture cancel (ScheduleFuture state);
}
