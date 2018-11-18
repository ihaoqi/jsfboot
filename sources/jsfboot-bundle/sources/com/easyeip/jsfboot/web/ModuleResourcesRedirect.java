package com.easyeip.jsfboot.web;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * 模块资源重定向到目录
 * @author 成慧
 *
 */
public class ModuleResourcesRedirect extends GenericServlet {

	private static final long serialVersionUID = 1L;
	
	public static final String SERVLET_PATH = "jsfboot-resources";

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

}
