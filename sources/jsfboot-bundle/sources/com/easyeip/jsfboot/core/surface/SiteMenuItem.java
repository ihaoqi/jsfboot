package com.easyeip.jsfboot.core.surface;

/**
 * 菜单定义
 * @author ihaoqi
 *
 */
public interface SiteMenuItem extends SubMenuContainer {
	
	/**
	 * 取得上级菜单
	 * @return
	 */
	SiteMenuItem getParent();
	
	void setContainer (SubMenuContainer cont);
	SubMenuContainer getContainer();
	
	/**
	 * 菜单项类型
	 * @return
	 */
	MenuItemType getType();
	
	/**
	 * 取得菜单名称（用于权限）
	 * @return
	 */
	String getName();
	void setName (String name);
	
	/**
	 * 取得权限名称
	 * @return
	 */
	String getPermName();
	void setPermName(String name);

	
	/**
	 * 取得菜单标题
	 * @return
	 */
	String getTitle();
	void setTitle (String title);
	
	/**
	 * 取得菜单图标
	 * @return
	 */
	String getIcon ();
	void setIcon (String icon);
	
	/**
	 * 取得要访问的内部页面
	 * @return
	 */
	String getOutcome ();
	void setOutcome (String outcome);
	
	/**
	 * 取得要跳转的URL
	 * @return
	 */
	String getUrl();
	void setUrl(String url);
	
	/**
	 * URL参数
	 * @return
	 */
	String getParams();
	void setParams(String param);
	
	/**
	 * 是否在新窗口中打开
	 * @return
	 */
	boolean isNewWindow();
	void setNewWindow(boolean newWindow);
	
	/**
	 * 取出页面来源
	 * @return
	 */
	MenuPageSource getPageSource();
	void setPageSource (MenuPageSource source);
	
	/**
	 * 是否可见
	 * @return
	 */
	boolean isVisible();
	void setVisible(boolean visible);
}
