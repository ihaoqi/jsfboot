package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.surface.PageDomainType;

/**
 * 要投票的菜单详情
 * @author liao
 *
 */
public interface VoterMenuDetails {
	/**
	 * 取得功能页面域
	 * @return
	 */
	PageDomainType getPageDomain();
	
	/**
	 * 取得关联的菜单名称
	 * @return
	 */
	String getMenuPosition();
	
	/**
	 * 取得菜单名称
	 * @return
	 */
	String getMenuName();
	
	/**
	 * 取得动作名称
	 * @return
	 */
	String getActionName();
}
