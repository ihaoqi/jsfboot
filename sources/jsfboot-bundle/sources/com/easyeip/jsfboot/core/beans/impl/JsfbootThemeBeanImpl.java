package com.easyeip.jsfboot.core.beans.impl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.JsfBeanUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.beans.JsfbootThemeBean;
import com.easyeip.jsfboot.core.module.type.EmptyThemeTemplate;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.ThemePageTemplate;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SurfaceService;

public class JsfbootThemeBeanImpl implements JsfbootThemeBean {

	@Override
	public String getPageDomain() {
		PageDomainType type = getPageThemeType(null);
		if (type == null)
			return "";

		return type.name();
	}

	/**
	 * 取得当前请求页面的primefaces主题名称
	 */
	@Override
	public String getPrimefacesTheme() {

		// 如果当前没有使用模板
		PageDomainType type = getPageThemeType(null);

		if (type != null) {
			// 返回当前jsfboot主题中配置的主题
			JsfbootTheme theme = JsfbootContext.getDriver().getSurfaceService().getCurrentTheme(type);
			String name = theme.getPrimefacesTheme();
			if (!StringKit.isEmpty(name)){
				// 支持el表达式
				return (String) JsfBeanUtils.evalExpr(name);
			}
		}

		// 则返回 JsfbootPrimefacesThemeParam 参数
		return getJsfbootPrimefacesThemeParam();
	}

	/**
	 * 读取web.xml中配置的 jsfboot-primefaces.THEME 参数值
	 * 
	 * @return
	 */
	private String getJsfbootPrimefacesThemeParam() {
		ServletContext sc = JsfbootContext.getDriver().getServletContext();
		String name = sc.getInitParameter(JsfbootPrimefacesThemeParam);
		if (StringKit.isEmpty(name))
			return "none";
		return name;
	}

	/**
	 * 取得本请求最后一次访问的主题
	 * 
	 * @param defType
	 * @return
	 */
	private PageDomainType getPageThemeType(PageDomainType defType) {

		/**
		 * 从当前导航中取得主题类型
		 */
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(FacesUtils.getServletRequest());

		if (navPath == null || navPath.getPageDomain() == null) {
			return defType;
		}

		return navPath.getPageDomain();
	}

	@Override
	public ThemePageTemplate getTemplate() {
		PageDomainType themeType = getPageThemeType(null);
		if (themeType == null) {
			return new EmptyThemeTemplate();
		}
		JsfbootTheme theme = getSurfaceService().getCurrentTheme(themeType);
		return theme.getPageTemplate();
	}

	private SurfaceService getSurfaceService() {
		return JsfbootContext.getDriver().getSurfaceService();
	}

	@Override
	public String getPageViewId() {
		// 取导航的地址
		HttpServletRequest request = FacesUtils.getServletRequest();
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(request);
		if (navPath != null)
			return navPath.getForwardPath();

		// 取出servlet后面的地址
		String viewId = request.getRequestURI();
		String contextPath = request.getContextPath();
		if (!StringKit.isEmpty(contextPath) && viewId.startsWith(contextPath) && !contextPath.equals("/")) {
			viewId = viewId.substring(contextPath.length());
		}
		return viewId;
	}

}
