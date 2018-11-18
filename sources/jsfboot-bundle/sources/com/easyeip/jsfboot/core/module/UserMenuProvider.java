package com.easyeip.jsfboot.core.module;

import java.util.List;

import com.easyeip.jsfboot.core.module.type.MenuPage;

/**
 * 用户菜单提供器
 * @author ihaoqi
 *
 */
public interface UserMenuProvider {
	
	/**
	 * 取得菜单列表
	 * @return
	 */
	List<MenuPage> getMenuList();
}
