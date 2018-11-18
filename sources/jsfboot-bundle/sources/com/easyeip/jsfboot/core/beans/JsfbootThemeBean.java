package com.easyeip.jsfboot.core.beans;

import com.easyeip.jsfboot.core.module.type.ThemePageTemplate;

/**
 * 取得前后端主题基本信息
 * @author ihaoqi
 *
 */
public interface JsfbootThemeBean {
	
	public static final String JsfbootPrimefacesThemeParam = "jsfboot-primefaces.THEME";

	/**
	 * 取得当前页面请求的主题类型
	 * @return 返回Site、Admin，如果没有使用主题则返回空串(非null)
	 */
	String getPageDomain();
	
	/**
	 * 获取当前页面ViewId
	 * @return
	 */
	String getPageViewId();

	/**
	 * 当前访问页面的Primefaces Theme，本属性供primefaces.THEME使用
	 *	<context-param>
	 *		<param-name>primefaces.THEME</param-name>
	 *		<param-value>#{jsfbootTheme.primefacesTheme}</param-value>
	 *	</context-param>
	 * 如果当前页面使用了jsfboot主题，会优先使用主题中配置的primefaces主题，如果都没有，会以下查找参数：
	 * <context-param>
	 * 	<param-name>jsfboot-primefaces.THEME</param-name>
	 *	<param-value>omega</param-value>
	 * </context-param>
	 * 如果这个参数也没有就会返回none，表示不使用主题
	 * @return
	 */
	String getPrimefacesTheme();
	
	/**
	 * 取得当前主题对应的模板
	 * @return
	 */
	ThemePageTemplate getTemplate();
}
