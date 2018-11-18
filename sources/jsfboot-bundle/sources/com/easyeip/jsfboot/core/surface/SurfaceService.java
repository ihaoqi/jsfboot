package com.easyeip.jsfboot.core.surface;

import com.easyeip.jsfboot.core.module.type.JsfbootTheme;

/**
 * 系统主题管理
 * @author ihaoqi
 *
 */
public interface SurfaceService {

	/**
	 * 取得当前系统使用的主题
	 * @return
	 */
	JsfbootTheme getCurrentTheme (PageDomainType type);
	
	/**
	 * 重新选择当前系统主题
	 * @param name
	 * @throws Exception
	 */
	void setCurrentTheme (PageDomainType type, String name) throws Exception;
	
	/**
	 * 获取菜单管理
	 * @return
	 */
	SiteMenuManager getMenuManager ();
}
