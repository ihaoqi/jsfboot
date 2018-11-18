package com.easyeip.jsfboot.admin.schedule.impl;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.easyeip.jsfboot.admin.schedule.ScheduleFuture;
import com.easyeip.jsfboot.admin.schedule.ScheduleState;

public class ScheduleFutureImpl implements ScheduleFuture {
	
	Future<Exception> future;
	private Runnable task;
	private String explain;
	
	private ScheduleState state = ScheduleState.RUNNING;
	
	public ScheduleFutureImpl(Runnable task, String explain, Future<Exception> future){
		this.future = future;
		this.task = task;
		this.explain = explain;
	}
	
	public boolean waitTask(long timeout){
		try {
			if (timeout < 0){
				future.get(24, TimeUnit.HOURS);
			}else{
				future.get(timeout, TimeUnit.MILLISECONDS);
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	public boolean cancel (){
		return future.cancel(true);
	}

	@Override
	public String getExplain() {
		return explain;
	}

	@Override
	public Runnable getTask() {
		return task;
	}

	@Override
	public boolean isFinish() {
		return future.isDone();
	}

	@Override
	public ScheduleState getState() {
		if (state == ScheduleState.RUNNING && future.isDone()){
			if (future.isCancelled()){
				state = ScheduleState.CANCELED;
			}else if (getException () != null){
				state = ScheduleState.EXCEPTION;
			}else{
				state = ScheduleState.FINISH;
			}
		}
		
		return state;
	}

	@Override
	public Exception getException() {
		if (future.isDone()){
			try {
				return future.get();
			} catch (Exception e) {
				return null;
			}
		}
		return null;
	}
}
