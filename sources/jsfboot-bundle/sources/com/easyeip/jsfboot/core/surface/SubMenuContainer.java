package com.easyeip.jsfboot.core.surface;

import java.util.List;

/**
 * 子菜单管理
 * @author ihaoqi
 *
 */
public interface SubMenuContainer {

	int getSubMenuCount ();
	List<SiteMenuItem> getSubMenuList();
	SiteMenuItem getSubMenu (int index);
	
	/**
	 * 查找指定名称的子菜单
	 * @param name 菜单名t乐
	 * @param downward 是否下向查找（递归）
	 * @return
	 */
	SiteMenuItem findSubMenu (String name, boolean downward);
	
	SiteMenuItem addSubMenu (MenuItemType type);
	SiteMenuItem insertSubMenu (MenuItemType type, int index);
	SiteMenuItem insertSubMenu (SiteMenuItem menu, int index);
	SiteMenuItem removeSubMenu (int index);
	
	void clearSubMenu();
}
