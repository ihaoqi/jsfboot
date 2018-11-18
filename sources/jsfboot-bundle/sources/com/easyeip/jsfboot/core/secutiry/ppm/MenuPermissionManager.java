package com.easyeip.jsfboot.core.secutiry.ppm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;

/**
 * 管理页面是否可以访问
 * 
 * @author liao
 *
 */
public class MenuPermissionManager implements JpnPermissionManager {
	
	static Logger logger = Logger.getLogger(MenuPermissionManager.class.getName());

	private SiteMenuManager menuManager;

	private List<String> publicPages;
	private MenuAccessVoter voter;
	private Map<String, AccessDecisionMenu> decisionMenus;

	public MenuPermissionManager(SiteMenuManager menuManager) {
		this.menuManager = menuManager;
		decisionMenus = new HashMap<String, AccessDecisionMenu>();
	}

	@Override
	public void initModulePublicPages(ModuleManager moduleManager) {
		publicPages = new ArrayList<String>();

		// 全部转换成小写
		for (JsfbootModule jm : moduleManager.getAllModule()) {
			publicPages.addAll(jm.getPageResource().getPublicPages());
		}

		// 排序
		Collections.sort(publicPages);
	}

	@Override
	public void setMenuAccessVoter(MenuAccessVoter voter) {
		this.voter = voter;
	}
	
	@Override
	public MenuAccessVoter getMenuAccessVoter() {
		return voter;
	}

	@Override
	public void addAccessDecisionMenu(PageDomainType pageDomain, String menuPosition) {

		SiteMenuBar menu = menuManager.getThemeMenubar(pageDomain, menuPosition);
		AccessDecisionMenu adm = new AccessDecisionMenu(pageDomain, menuPosition, menu);
		decisionMenus.put(adm.getKey(), adm);
	}

	@Override
	public void clearAccessDecisionMenu() {
		decisionMenus.clear();
	}

	@Override
	public boolean checkPermission(AccountDetails account, JpnUrlRequestInfo page) {

		// 取得导航路径
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(page.getRequest());
		if (navPath == null){
			navPath = JsfbootPageNavigator.createNavigatorPath(page.getRequest());
		}

		// 看是否是模块公共页面
		String pagePath = (navPath == null ? page.getJpnUrl() : navPath.getForwardPath());
		if (publicPages.contains(pagePath)) {
			return true;
		}

		// 如果导航来的则不授权（可能出BUG了）
		if (navPath == null || StringKit.isEmpty(pagePath))
			return false;

		// 查找对应的菜单条，如果不存在表示不进行权限控制
		String navMenuPos = new AccessDecisionMenu(
								navPath.getPageDomain(), navPath.getMenuPosition()).getKey();
		AccessDecisionMenu decisionMenu = decisionMenus.get(navMenuPos);
		if (decisionMenu == null)
			return true;

		// 如果对应的菜单不存在则不授权
		SiteMenuBar menubar = menuManager.getThemeMenubar(
				decisionMenu.getPageDomain(), decisionMenu.getMenuPosition());
		if (menubar == null){
			logger.log(Level.WARNING, "页面授权失败：" + pagePath);
			return false;
		}

		// 查询菜单
		SiteMenuItem menuItem = navPath.getMenuItem();
		PageAction lookAction = null;
		if (menuItem == null){
			// 根据url查找对应的菜单或动作
			PageInMenuItem pageIn = MenuItemFindUtils.findMenuItemByUrl(
									decisionMenu.getPageDomain(), menubar, navPath.getForwardPath());
			if (pageIn == null){
				logger.log(Level.WARNING, "页面授权失败：" + pagePath);
				return false;
			}
			menuItem = pageIn.getMenuItem();
			lookAction = pageIn.getPageAction();
		}else if (menuItem.getPageSource() != null){
			// 判断是否是动作
			MenuPage menuPage = MenuPageUtils.findMenuPageBySource(
							menuItem.getPageSource(), decisionMenu.getPageDomain(), false);
			if (menuPage != null){
				lookAction = MenuItemFindUtils.findPageActionByUrl(menuPage, pagePath);
			}
		}

		// 权限投票
		VoterMenuDetails vmd = new DefaultVoterMenuDetails(decisionMenu.getPageDomain(), 
									decisionMenu.getMenuPosition(), menuItem, 
									lookAction == null ? null : lookAction.getName());
		boolean result = voter.voter(account, vmd);
		
		if (result == false){
			logger.log(Level.WARNING, "页面授权失败：" + pagePath);
		}else if (menuItem != null && navPath.getMenuItem() == null){
			// 如果是共享页面，则更新下它所在的菜单
			navPath.setMenuItem(menuItem);
		}
		
		return result;
	}
	

}
