package com.easyeip.jsfboot.web.faces;

import java.io.IOException;
import java.util.Map;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.application.exceptionhandler.ExceptionInfo;
import org.primefaces.application.exceptionhandler.PrimeExceptionHandler;
import org.primefaces.util.Base64;

import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.core.surface.PageDomainType;

public class JsfbootExceptionHandler extends PrimeExceptionHandler {
	
	public static final String JPN_PREFIX = "/jsfboot.jpn/";

	public JsfbootExceptionHandler(ExceptionHandler wrapped) {
		super(wrapped);
	}
	
	@Override
	protected String evaluateErrorPage(Map<String, String> errorPages, Throwable rootCause) {
		String pages = super.evaluateErrorPage(errorPages, rootCause);
		
		// 替换前缀
		if (!pages.startsWith(JPN_PREFIX))
			return pages;
			
		// 取得原页面
		String url = pages.substring(JPN_PREFIX.length()-1);
		NavigatorPath navPath = getNavigatorPath(FacesContext.getCurrentInstance());
		if (navPath == null){
			return url;
		}
		
		// 取得编码后页面
		String jpnPrefix = navPath.getJpnServlet(); 
		if (navPath.getPageDomain() == PageDomainType.Admin){
			jpnPrefix += "/admin/" + JsfbootPageNavigator.PagePrefix;
		}else{
			jpnPrefix += "/" + JsfbootPageNavigator.PagePrefix;
		}

		pages = jpnPrefix + Base64.encodeToString(url.getBytes(), false);
			
		return pages;
	}

	@Override
	protected ExceptionInfo createExceptionInfo(Throwable rootCause) throws IOException {
		// 扩展异常属性
		JsfbootExceptionInfo jei = new JsfbootExceptionInfo (super.createExceptionInfo(rootCause));

		// 修复错误信息
		if (StringKit.isEmpty(jei.getMessage())){
			String msg = jei.getType();
			if (jei.getStackTrace().length > 0){
				msg += "\n" +jei.getStackTrace()[0].toString();
			}
			jei.setMessage(msg);
		}
		
		NavigatorPath navPath = getNavigatorPath(FacesContext.getCurrentInstance());
		if (navPath == null)
			return jei;
		
		//Referer:http://localhost:8080/easyeip-tsms/jpn/admin/data-sources?view=wwwww
		// 取来查询参数
		String referer = FacesUtils.getServletRequest().getHeader("referer");
		String refererQuery = null;
		if (!StringKit.isEmpty(referer) && referer.indexOf("?")>0){
			
			int dotIndex = referer.indexOf("?");
			String refererRequest = referer.substring(0, dotIndex);
			if (refererRequest.endsWith(navPath.getRequestPath())){
				refererQuery = referer.substring(dotIndex+1);
			}
		}
		
		// 生成原完整url
		String requestPath = navPath.getRequestPath();
		String srcPageUrl = navPath.getJpnServlet() + (StringKit.equals(requestPath, "/") ? "" : requestPath);
		if (!StringKit.isEmpty(refererQuery)){
			srcPageUrl += "?" + refererQuery;
		} else if (!StringKit.isEmpty(navPath.getQueryString())){
			srcPageUrl += "?" + navPath.getQueryString();
		}
		jei.setPageUrl(srcPageUrl);

		return jei;
	}
	
	private NavigatorPath getNavigatorPath(FacesContext context){
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		return JsfbootPageNavigator.getNavigatorPath(req);
	}
}
