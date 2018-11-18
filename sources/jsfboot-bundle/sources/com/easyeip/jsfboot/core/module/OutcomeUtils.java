package com.easyeip.jsfboot.core.module;

import javax.faces.context.FacesContext;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.surface.MenuPageSource;

public class OutcomeUtils {
	
	public static String fullOutcome (MenuPageSource source, String page){
		
		if (source == null)
			return page;
		
		JsfbootModule module = JsfbootContext.getDriver().getModuleManager().
								findModuleByName(source.getModuleName());
		
		if (module == null)
			return null;
		
		return fullOutcome (module, page);
	}
	
	/**
	 * 返回带有模块名的路径
	 * @param module
	 * @param page
	 * @return
	 */
	public static String fullOutcome (JsfbootModule module, String page){
		if (StringKit.isEmpty(page))
			return null;
		
		String rootPath = "/" + module.getModuleInfo().getModuleName();
		
		if (!(page.startsWith("/") || page.startsWith("\\"))){
			rootPath += "/";
		}
		return rootPath + page;
	}
	
	/**
	 * 返回带有下上下的路径
	 * @param req
	 * @param url
	 * @return
	 */
	public static String fullContextPath(String url){
		
		String contextPath = FacesContext.getCurrentInstance().getExternalContext().getApplicationContextPath();
		if (url.indexOf("://") > 0)
			return url;

		String path = url;
		if (!path.startsWith("/"))
			path = "/" + path;
		
		if (contextPath.equals("/") || contextPath.length() == 0)
			return path;
		
		return contextPath + path;
	}

}
