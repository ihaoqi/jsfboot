package com.easyeip.jsfboot.core.module.type;

import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;

public interface JsfbootTheme {
	
	/**
	 * 取得主题名称
	 * @return
	 */
	String getName();

	/**
	 * 取得主题中使用的primefaces theme名称
	 * @return
	 */
	String getPrimefacesTheme();
	
	/**
	 * 取得主题页面模板
	 * @return
	 */
	ThemePageTemplate getPageTemplate();
	
	/**
	 * 取得所在的模块包
	 * @return
	 */
	JsfbootModule getModulePackage();
	
	/**
	 * 获取主题定义的所有菜单
	 * @return 返回只读取的主题菜单定义
	 */
	List<MenuPosition> getMenuPosition();
	
	/**
	 * 取得系统主题配置界面跳转
	 * @return 返回符合jsf导航的跳转，如果不支持配置则返回null
	 */
	String getConfPage();
	
	/**
	 * 是否可在前台使用
	 * @return
	 */
	boolean isUseSite ();
	
	/**
	 * 是否可在后台使用
	 * @return
	 */
	boolean isUseAdmin ();
	
	/**
	 * 取得主题略缩图
	 * @return
	 */
	String getImagePath();
}
