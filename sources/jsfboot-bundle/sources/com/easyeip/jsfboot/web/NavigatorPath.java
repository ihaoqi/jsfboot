package com.easyeip.jsfboot.web;

import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

/**
 * 页面导航信息
 * @author ihaoqi
 *
 */
public interface NavigatorPath {
	
	/**
	 * 取得 /jpn
	 * @return
	 */
	public String getJpnServlet();
	
	/**
	 * 访问的主题类型（前台还是后台）
	 * @return
	 */
	public PageDomainType getPageDomain();
	
	/**
	 * 主题的菜单名称
	 * @return
	 */
	public String getMenuPosition();
	
	/**
	 * 菜单项名称
	 * @return
	 */
	public String getMenuPage();
	
	/**
	 * 更新菜单页面名称
	 * @param menuPage
	 */
	public void setMenuPage(String menuPage);
	
	/**
	 * 取得关联的菜单项
	 * @return
	 */
	public SiteMenuItem getMenuItem();
	
	/**
	 * 设置关联的菜单项
	 * @param menuItem
	 */
	public void setMenuItem (SiteMenuItem menuItem);
	
	/**
	 * 取得原始路径
	 * @return
	 */
	public String getRequestPath();
	
	/**
	 * 查询参数
	 * @return
	 */
	public String getQueryString();

	
	/**
	 * 取得最后跳转的页面
	 * @return
	 */
	public String getForwardPath ();
	public void setForwardPath (String path);
}
