package com.easyeip.jsfboot.admin.bean;

import java.util.List;

import com.easyeip.jsfboot.admin.bean.type.MenuIcon;

/**
 * 菜单图标服务
 * @author ihaoqi
 *
 */
public interface AdminMenuIconsBean {
	
	List<MenuIcon> getAwesomeIcons();
	List<MenuIcon> getRioIcons();
}
