package com.easyeip.jsfboot.admin.datasource.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.GetConnectionTimeoutException;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;

/**
 * 数据连接检测任务
 * @author ihaoqi
 *
 */
public class DataSourceCheckTask implements Runnable {
	
	private DataSourceService service;
	private DataSourceDefinition definition;
	private String moduleDataSource;
	
	private String name;
	
	private List<String> messages = new ArrayList<String>();
	private Exception exception;
	
	public DataSourceCheckTask(DataSourceService service, String moduleDataSource){
		this.service = service;
		this.moduleDataSource = moduleDataSource;
		name = moduleDataSource;
		addMessage(name + " 正在连接，请稍后 ...");
	}
	
	public DataSourceCheckTask(DataSourceDefinition definition){
		this.definition = definition;
		name = definition.getName();
		addMessage(name + " 正在连接，请稍后 ...");
	}
	
	public List<String> getMessage (){
		synchronized(messages){
			return Collections.unmodifiableList(messages);
		}
	}

	public Exception getException(){
		synchronized(messages){
			return exception;
		}
	}
	
	@Override
	public void run() {
		
		DataSource dataSource = null;
		DruidDataSource druidSource = null;
		int timeout = 3;
		Connection conn = null;
		try {
			// 取得数据源对象
			if (definition != null)
				dataSource = CustomDataSourceFactory.create(definition);
			else if (StringKit.notEmpty(moduleDataSource))
				dataSource = service.getDataSourceObject(moduleDataSource);
			
			// 连接数据库
			if (dataSource != null){
				
				if (dataSource instanceof DruidDataSource){
					druidSource = (DruidDataSource) dataSource;
					conn = druidSource.getConnection(timeout*1000);
				}else{
					dataSource.setLoginTimeout(timeout);
					conn = dataSource.getConnection();
				}
				closeConnection (conn);
				if (definition != null && druidSource != null){
					druidSource.close();
				}
				
				addMessage(name + " 连接成功。");
			}else{
				addMessage(name + " 无法创建数据源。");
			}
			
		} catch (Exception e) {
			closeConnection (conn);
			
			if (definition != null && druidSource != null){
				druidSource.close();
			}
			if (e instanceof GetConnectionTimeoutException)
				addMessage(name + " 连接超时 " + Long.valueOf(timeout).toString() + " 秒。");
			else if (e instanceof SQLException && e.getCause() instanceof ClassNotFoundException)
				addMessage(name + " 类不存在 " + e.getMessage());
			else
				addMessage(name + " " + e.getMessage());
			synchronized(messages){ 
				exception = e;
			}
		}
		
		addMessage(name + " 测试结束。");
	}
	
	private void closeConnection (Connection conn){
		if (conn == null)
			return;
		
		try {
			conn.close();
		} catch (SQLException e) {
		}
	}

	private void addMessage(String msg){
		// 格式化当前时间
		Date date=new Date();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(date);
		
		// 添加到消息对列
		synchronized(messages){
			messages.add(time + " " + msg);
		}
	}
}
