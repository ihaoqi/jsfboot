package com.easyeip.jsfboot.core.module.type;

import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;

public interface MenuPage {
	
	JsfbootModule getModule();
	
	String getName();
	String getTitle();
	String getOutcome();
	String getIcon();
	
	List<String> getPublicPages();

	List<MenuPage> getSubMenu ();
	List<PageAction> getPageActions();
	
	/**
	 * 取得菜单路径，格式为 /xx/xx
	 * @return
	 */
	String getPath();
}
