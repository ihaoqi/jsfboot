package com.easyeip.jsfboot.core.module.type;

import java.util.List;

import com.easyeip.jsfboot.core.module.UserMenuProvider;

/**
 * 模块菜单资源定义
 * @author ihaoqi
 *
 */
public interface PageResource {

	/**
	 * 获取用户菜单
	 * @return
	 */
	UserMenuProvider getSiteMenu();
	
	/**
	 * 获取后台菜单
	 * @return
	 */
	ModuleAdminMenu getAdminMenu();
	
	/**
	 * 取得模块配置页面
	 * @return
	 */
	String getConfPage();
	
	/**
	 * 取得公共的页面
	 * @return
	 */
	List<String> getPublicPages();
}
