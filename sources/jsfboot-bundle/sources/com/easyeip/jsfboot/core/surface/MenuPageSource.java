package com.easyeip.jsfboot.core.surface;

/**
 * 菜单页面来源（只对于模块中的菜单）
 * @author ihaoqi
 *
 */
public interface MenuPageSource {
	String getModuleName();
	void setModuleName(String name);

	String getMenuPath();
	void setMenuPath(String path);
	
	String getMenuTitle();
	void setMenuTitle(String title);
	
	String encode ();
}
