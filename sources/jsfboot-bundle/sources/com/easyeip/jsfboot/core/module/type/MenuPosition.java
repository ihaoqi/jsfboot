package com.easyeip.jsfboot.core.module.type;

/**
 * 主题菜单信息
 * @author ihaoqi
 *
 */
public interface MenuPosition {

	/**
	 * 取得菜单名称，如“主菜单”
	 * @return
	 */
	String getName();
	
	/**
	 * 取得菜单说明
	 * @return
	 */
	String getTitle();
	
}
