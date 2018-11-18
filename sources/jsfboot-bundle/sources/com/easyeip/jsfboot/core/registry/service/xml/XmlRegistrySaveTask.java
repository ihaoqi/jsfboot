package com.easyeip.jsfboot.core.registry.service.xml;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 注册表存档任务
 * @author ihaoqi
 *
 */
public class XmlRegistrySaveTask {
	
	private ReentrantLock saveLock;
	
	private long waitDelay;
	private XmlRegistrySaveCallback saveCallback;
	private Thread thread;
	
	private long lastModifyTime = 0;	// 最后一次修改时间，如果0表示没有修改
	private boolean exitTag = false;	// 退出标记
	
	/**
	 * 构造函数
	 * @param waitDelay 保存延时（毫秒）
	 * @param callback	保存回调
	 */
	public XmlRegistrySaveTask(long waitDelay, XmlRegistrySaveCallback callback){
		
		this.waitDelay = waitDelay;
		this.saveCallback = callback;
		
		saveLock = new ReentrantLock ();
	}
	
	/**
	 * 注册表开始修改时调用，完成后要释放锁
	 */
	public void modifyBegin(){
		// 获取锁，在保存的时候不能修改
		saveLock.lock();
	}
	
	public void modifyCancel(){
		saveLock.unlock();
	}
	
	/**
	 * 设置注册修改完成后修改
	 */
	public void modifyComplete (){
		// 还原锁
		saveLock.unlock();
		
		// 启动任务
		synchronized (this){
			// 设置最后一次修改时间
			lastModifyTime = System.currentTimeMillis();
			
			// 创建保存定时器
			if (thread == null){
				startSchedule ();
			}
		}
	}
	
	/**
	 * 判断是否还有任务
	 * @return
	 */
	public boolean haveTask(){
		long last = 0;
		synchronized (this){
			last = lastModifyTime;
    	}
		
		return last != 0;
	}
	
	/**
	 * 注册表服务停止
	 */
	public void close(){
		if (thread == null)
			return;
		
		// 设置退出标记
		synchronized (this){
			exitTag = true;
    	}
		
		// 等待退出
		try {
			thread.join();
		} catch (InterruptedException e) {
			//
		}
		thread = null;
	}
	
	private void startSchedule (){
		Runnable task = new Runnable() {  
            @Override  
            public void run() {
            	
            	while (true){
            		
            		// 判断是否要退出
            		synchronized (this){
            			if (exitTag == true)
            				break;
                	}
            		
            		// 等待延时
            		try {
						Thread.sleep(waitDelay);
					} catch (InterruptedException e) {
						//
					}
            		
            		synchronized (this){
            			if (exitTag == true)
            				break;
                	}
            		
                	// 获取最后一次修改时间，如果为0表示没修改
                	long lastTime = 0;
                	synchronized (this){
                		lastTime = lastModifyTime;
                	}
                	
	            	if (lastTime == 0)
	            		continue;
	            	
	            	// 获取上次执行到现在的时间差，还在延时范围内则不处理
	        		long curTime = System.currentTimeMillis();
	        		long diff = curTime - lastTime;
	        		if (diff > 0 && diff < waitDelay)
	        			continue;
	            	
	    			// 开始保存
	            	saveLock.lock();
	            	try{
	            		saveCallback.SaveRegistry();
	            	}finally{
	            		saveLock.unlock();
	            	}
	            	synchronized (this){
	            		lastModifyTime = 0;
	            	}
            	}
            }  
        };
        
        exitTag = false;
		thread = new Thread(task);
		thread.start();
	}
}
