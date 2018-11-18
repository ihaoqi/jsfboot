package com.easyeip.jsfboot.core.surface;

import java.util.List;

/**
 * 系统菜单条定义
 * @author ihaoqi
 *
 */
public interface SiteMenuBar extends SubMenuContainer {
	
	/**
	 * 是否是主菜单
	 * @return
	 */
	boolean getHasMain();
	void setHasMain(boolean hasMain);
	
	/**
	 * 取得菜单标题
	 * @return
	 */
	String getTitle();
	void setTitle(String title);

	/**
	 * 取得菜单名称
	 * @return
	 */
	String getName();
	void setName(String name);
	
	/**
	 * 复制一份菜单条
	 * @return
	 */
	SiteMenuBar copyForm(SiteMenuBar menubar);

	/**
	 * 更新菜单的权限名称
	 */
	void updatePermNames();
	
	/**
	 * 转换成列表
	 * @param includeFolder 不要包含目录
	 * @return
	 */
	List<SiteMenuItem> asList(boolean includeFolder);

	/**
	 * 克隆自己
	 * @return
	 */
	SiteMenuBar cloneMy();
}
