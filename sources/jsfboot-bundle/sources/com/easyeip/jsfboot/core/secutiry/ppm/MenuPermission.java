package com.easyeip.jsfboot.core.secutiry.ppm;

import java.util.List;

import com.easyeip.jsfboot.core.surface.PageDomainType;

/**
 * 菜单权限实体
 * @author liao
 *
 */
public interface MenuPermission {
	
	/**
	 * 取得本权限的唯一名称
	 * @return
	 */
	String getKey();
	
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
	 * 是否允许所有动作
	 * @return
	 */
	boolean isGrantAllAction();
	
	/**
	 * 取得允许的动作名称列表，多个动作间用逗号隔开
	 * @return 返回动作名称列表
	 */
	List<String> getGrantActions();
}
