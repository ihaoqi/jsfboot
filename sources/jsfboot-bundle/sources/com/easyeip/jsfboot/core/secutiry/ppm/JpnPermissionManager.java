package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;
import com.easyeip.jsfboot.core.surface.PageDomainType;

/**
 * 页面权限管理器
 * @author liao
 *
 */
public interface JpnPermissionManager {
	
	/**
	 * 初使化模块公共页面
	 */
	void initModulePublicPages(ModuleManager moduleManager);
	
	/**
	 * 添加要管理权限的主题菜单
	 * @param pageDomain	主题类型
	 * @param menuPosition	主题菜单位置
	 */
	void addAccessDecisionMenu(PageDomainType pageDomain, String menuPosition);
	
	/**
	 * 清除所有要决策的菜单
	 */
	void clearAccessDecisionMenu();
	
	/**
	 * 设置菜单存储投票器
	 * @param voter
	 */
	void setMenuAccessVoter (MenuAccessVoter voter);
	
	/**
	 * 获取投票器
	 * @return
	 */
	MenuAccessVoter getMenuAccessVoter();
	
	/**
	 * 检测页面是否可以访问
	 * @param account
	 * @param jpnUrl
	 * @return
	 */
	boolean checkPermission (AccountDetails account, JpnUrlRequestInfo page);
}
