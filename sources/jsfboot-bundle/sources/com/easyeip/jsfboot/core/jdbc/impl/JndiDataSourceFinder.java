package com.easyeip.jsfboot.core.jdbc.impl;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import com.easyeip.jsfboot.core.jdbc.DataSourceFinder;

public class JndiDataSourceFinder implements DataSourceFinder {
	
	private Context jndiContext;
	
	public JndiDataSourceFinder(){
		try {
			jndiContext = new InitialContext();
		} catch (NamingException e) {
			
		}
	}

	@Override
	public DataSource getDataSourceObject(String dataSourceName) throws NamingException {
		// 创建jndi查询上下文
		// "java:comp/env/jdbc/books"
		if (jndiContext == null)
			return null;
		
		try{
			DataSource source = (DataSource)jndiContext.lookup(dataSourceName);
			return source;
		}catch (NamingException e){
			return null;
		}
	}

}
