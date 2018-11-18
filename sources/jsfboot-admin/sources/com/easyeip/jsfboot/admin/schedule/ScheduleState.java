package com.easyeip.jsfboot.admin.schedule;

/**
 * 任务结束状态
 * @author liao
 *
 */
public enum ScheduleState {
	RUNNING,	// 正在运行
	FINISH,		// 正常结束
	CANCELED,	// 终止运行
	EXCEPTION	// 出现异常
}
