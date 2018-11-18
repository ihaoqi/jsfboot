package com.easyeip.jsfboot.admin.schedule.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.easyeip.jsfboot.admin.AdminScheduleService;
import com.easyeip.jsfboot.admin.schedule.ScheduleFuture;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceUtils;

public class AdminScheduleServiceImpl extends GenericService implements AdminScheduleService {

	private ExecutorService fixedThreadPool;
	private int poolPhreads;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		// 创建线程沲（最大为两倍的CPU个数）
		int cpuCount = Runtime.getRuntime().availableProcessors();
		int threads = (int) ServiceUtils.getParamLongValue(context, "threads", cpuCount);
		poolPhreads = Math.max(threads, 2);
		fixedThreadPool = Executors.newFixedThreadPool(poolPhreads);
	}

	@Override
	public void stopService() throws Exception {
		fixedThreadPool.shutdownNow();
	}

	@Override
	public ScheduleFuture schedule(final Runnable task, String explain) {
		Future<Exception> future = fixedThreadPool.submit(new Callable<Exception>(){
			@Override
			public Exception call() throws Exception {
				try{
					task.run();
					return null;
				}catch (Exception e){
					return e;
				}
			}
			
		});
		
		return new ScheduleFutureImpl (task, explain, future);
	}

	@Override
	public ScheduleFuture waitFinish(ScheduleFuture state, long timeout) {
		((ScheduleFutureImpl) state).waitTask(timeout);
		return state;
	}
	
	@Override
	public ScheduleFuture waitFinish(ScheduleFuture state) {
		return waitFinish(state, -1);
	}

	@Override
	public ScheduleFuture cancel(ScheduleFuture state) {
		((ScheduleFutureImpl) state).cancel();
		return state;
	}

	@Override
	public ScheduleFuture schedule(Runnable task) {
		return schedule(task, null);
	}

	@Override
	public int getPoolThreads() {
		return poolPhreads;
	}

}
