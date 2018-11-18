package com.easyeip.jsfboot.webfile.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.primefaces.webapp.filter.FileUploadFilter;

public class WebfileUploadFilter extends FileUploadFilter {

	public void doFilter(ServletRequest request, ServletResponse response, 
			FilterChain filterChain) throws IOException, ServletException {
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		super.doFilter(request, response, filterChain);
		
	}
}
