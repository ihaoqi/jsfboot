package com.easyeip.jsfboot.core.module.type;

/**
 * 主题对应的模板页面地址
 * @author ihaoqi
 *
	main		// 完整页面
	content		// 仅内容区
	blank		// 一点都没有
 */
public interface ThemePageTemplate {

	/**
	 * 完整页面模板
	 * @return
	 */
	String getMain();
	
	/**
	 * 仅内容区模板
	 * @return
	 */
	String getContent();
	
	/**
	 * 一点都没有
	 * @return
	 */
	String getBlank();
}
