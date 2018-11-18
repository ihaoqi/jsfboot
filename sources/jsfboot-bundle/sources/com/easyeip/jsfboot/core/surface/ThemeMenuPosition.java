package com.easyeip.jsfboot.core.surface;

import java.util.Map;

/**
 * 主题中的菜单位置管理
 * @author ihaoqi
 *
 */
public interface ThemeMenuPosition {

	/**
	 * 获取主题菜单与系统菜单的对应表
	 * @return 返回只读的菜单对应表，格式为：Map<主题菜单位置、系统菜单名称>
	 */
	Map<String,String> getPairMap ();
	
	/**
	 * 设置菜单对应关系
	 * @param positionName 主题菜单名称
	 * @param menuBarName 系统菜单名称，从控制台获取，允许为空或null。
	 */
	boolean pairMenuBar (String positionName, String menuBarName);
	
	/**
	 * 复制
	 * @param pos
	 */
	void copyForm(ThemeMenuPosition pos);
}
