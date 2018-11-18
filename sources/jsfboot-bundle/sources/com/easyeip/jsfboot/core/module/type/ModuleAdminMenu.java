package com.easyeip.jsfboot.core.module.type;

import com.easyeip.jsfboot.core.module.UserMenuProvider;

/**
 * 模块后台菜单定义
 * @author ihaoqi
 *
 */
public interface ModuleAdminMenu extends UserMenuProvider {

	String getAnchor();
	
	String getPosition();
}
