package com.easyeip.jsfboot.core;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.easyeip.jsfboot.core.driver.DefaultJsfbootDriver;

/**
 * jsfboot 应用启动程序
 * @author ihaoqi
 *
 */
public class JsfbootInitListener implements ServletContextListener {
	
	static Logger logger = Logger.getLogger(JsfbootInitListener.class.getName());
	
	private static ServletContext servletContext;
	private DefaultJsfbootDriver driver;
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		servletContext = sce.getServletContext();
		
		// 创建驱动器，如果出现异常将停止运行
		DefaultJsfbootDriver tmpDriver = new DefaultJsfbootDriver();
		JsfbootContext.setDriver(tmpDriver);
		try {
			tmpDriver.startDriver(servletContext);
		} catch (Exception e) {
			logger.log(Level.SEVERE, "jsfboot系统启动失败", e);
			throw new RuntimeException(e);
		}
		driver = tmpDriver;
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		if (driver != null){
			driver.stopDriver();
		}
	}
	
	/**
	 * 取得Servlet Context
	 * @return
	 */
	public static ServletContext getServletContext(){
		return servletContext;
	}

}
