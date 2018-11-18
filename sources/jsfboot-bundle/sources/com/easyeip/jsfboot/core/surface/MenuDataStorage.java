package com.easyeip.jsfboot.core.surface;

import java.util.List;

public interface MenuDataStorage {
		
	/**
	 * 取得用户菜单定义列表
	 * @param includeConfMenubar 是否包含系统菜单
	 * @return
	 */
	List<SiteMenuBar> allSiteMenubar();
	
	/**
	 * 取得指定名称的菜单定义
	 * @param name
	 * @param create 不存在时是否创建
	 * @return
	 */
	SiteMenuBar getSiteMenubar (String menuName, boolean create);
	
	/**
	 * 添加新的菜单定义
	 * @param name 菜单名称，不能重名，重名将返回原来的定义
	 * @return
	 */
	SiteMenuBar addSiteMenubar (String menuName);
	
	/**
	 * 移除已存在的菜单定义，用户菜单与后台主菜单不能删除
	 * @param name
	 * @return
	 */
	SiteMenuBar removeSiteMenubar (String menuName);
	
	/**
	 * 取得菜单位置管理
	 * @param theme
	 * @return
	 */
	ThemeMenuPosition getPositionPair(PageDomainType theme);
	
	/**
	 * 获取主题菜单位置对应的菜单条
	 * @param positionName
	 * @return
	 */
	SiteMenuBar getThemeMenubar (PageDomainType theme, String positionName);
}
