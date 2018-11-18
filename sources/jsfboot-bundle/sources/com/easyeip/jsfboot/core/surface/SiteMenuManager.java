package com.easyeip.jsfboot.core.surface;

/**
 * 主题菜单占位配置
 * @author ihaoqi
 *
 */
public interface SiteMenuManager extends MenuDataStorage {
	
	public static final String MAIN_MENUBAR = "main";

	public static final String MAIN_MENUBAR_TITLE = "主菜单";
	public static final String ADMIN_MENUBAR_TITLE = "后台管理";
	
	/**
	 * 取得系统后台配置菜单
	 * @return
	 */
	SiteMenuBar getAdminMenubar();
	
	/**
	 * 克隆一份数据（用于编辑缓存）
	 * @return
	 */
	MenuDataStorage cloneSiteMenuData ();
	
	/**
	 * 应用新数据
	 * @param newData
	 */
	void applySiteMenuData (MenuDataStorage newData);
	
	/**
	 * 最后一次修改的时间
	 * @return
	 */
	long getLastApplyTime();
	
	/**
	 * 更新最后修改时间
	 */
	void updateLastApplyTime();
}
