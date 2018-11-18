package com.easyeip.jsfboot.paradise;

import org.primefaces.model.menu.MenuModel;

public interface ParadiseThemeBean {
	
	public static final String PERSON_MENUBAR = "user";
	public static final String LINK_MENUBAR = "link";
	
	/**
	 * 获取后台配置菜单
	 * @return
	 */
	MenuModel getAdminMenu();
	
	/**
	 * 获取前台配置菜单
	 * @return
	 */
	MenuModel getSiteMenu();
	
	/**
	 * 获取用户个人菜单；
	 * @return
	 */
	MenuModel getPersonMenu();
	
	/**
	 * 获取顶层菜单
	 * @return
	 */
	MenuModel getLinkMenu();
	
	/**
	 * 取得配置值
	 * @return
	 */
	ParadiseConfigValue getConfig();
	
	/**
	 * 取得配置表单
	 * @return
	 */
	ParadiseConfigForm getConfigForm();
	
	/**
	 * 取得当前登录的用户名
	 * @return
	 */
	String getAccountUsername();
	
	/**
	 * 取得帐号第一个角色
	 * @return
	 */
	String getAccountFirstRole();
	
	/**
	 * 保存配置表单
	 */
	void saveConfigForm();
	
	/**
	 * 重置配置表单
	 */
	void resetConfigForm();
}
