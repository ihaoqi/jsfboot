package com.easyeip.jsfboot.webfile.web;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.CustomResourceUrl;
import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

/**
 * 自定义文件路径的过滤器
 * @author liao
 *
 */
public class WebfileCustomPathFilter implements Filter {
	
	String tempDir;
	WebfileResourceService service;

	@Override
	public void init(FilterConfig config) throws ServletException {
		tempDir = System.getProperty("java.io.tmpdir");
	}
	
	@Override
	public void destroy() {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String reqPath = req.getServletPath();
		
		if (service == null){
			service = (WebfileResourceService)JsfbootContext.getService(WebfileResourceService.class);
		}

		if (service != null && StringKit.notEmpty(reqPath)){
			
			CustomResourceUrl custom = service.getCustomUrlManager().lookupUrlResource(reqPath);
			if (custom != null){
				WebFileResource res = WebfileResourceServlet.findResource (custom.getSourcePath());
				if (res != null){
					WebfileResourceServlet.outputResource (res, tempDir, req, resp);
					return;
				}
			}
		}
		
		chain.doFilter(request, response);
	}

}
