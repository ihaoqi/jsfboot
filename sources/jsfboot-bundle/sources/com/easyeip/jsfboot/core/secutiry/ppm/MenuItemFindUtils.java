package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;

public class MenuItemFindUtils {
	/**
	 * 根据url查询菜单项
	 * @param menubar
	 * @param pageUrl
	 * @return
	 */
	public static PageInMenuItem findMenuItemByUrl(
				PageDomainType pageDomain, SubMenuContainer menubar, String pageUrl){
		// 去除参数
		pageUrl = removeUrlQueryParam(pageUrl);
		
		if (StringKit.isEmpty(pageUrl))
			return null;
		
		// 查找url
		for (SiteMenuItem menuItem : menubar.getSubMenuList()){
			
			// 查询子菜单
			if (menuItem.getSubMenuCount() > 0){
				PageInMenuItem result = findMenuItemByUrl (pageDomain, menuItem, pageUrl);
				if (result != null)
					return result;
				continue;
			}
			
			// 查询模块页面
			if (menuItem.getPageSource() != null && StringKit.notEmpty(menuItem.getOutcome())){
				String pagePath = JsfbootPageNavigator.getToViewId(menuItem, pageUrl, pageDomain);
				pagePath = removeUrlQueryParam(pagePath);
				
				if (StringKit.equals(pageUrl, pagePath)){
					return new PageInMenuItem(menuItem, pageUrl, null, false);
				}

				MenuPage menuPage = MenuPageUtils.findMenuPageBySource(
									menuItem.getPageSource(), pageDomain, false);

				// 判断是否菜单公共页面
				if (menuPage != null && menuPage.getPublicPages().contains(pageUrl)){
					return new PageInMenuItem(menuItem, pageUrl, null, true);
				}

				// 查找动作
				PageAction action = findPageActionByUrl (menuPage, pageUrl);
				if (action != null){
					return new PageInMenuItem(menuItem, pageUrl, action,
									action.getPublicPages().contains(pageUrl));
				}
				
			}else if (StringKit.notEmpty(menuItem.getUrl())){
				// 查询连接页面
				String pagePath = removeUrlQueryParam(menuItem.getUrl());
				if (pagePath.toLowerCase().startsWith(pageUrl))
					return new PageInMenuItem(menuItem, pageUrl, null, false);
			}
		}
		
		return null;
	}
	
	/**
	 * 查找有页面的动作
	 * @param menuPage
	 * @param pageUrl
	 * @return
	 */
	public static PageAction findPageActionByUrl(MenuPage menuPage, String pageUrl){
		
		if (menuPage == null)
			return null;
		
		for (PageAction action : menuPage.getPageActions()){
			// 生成动作路径
			if (StringKit.notEmpty(action.getOutcome())){
				String fillOutcome = OutcomeUtils.fullOutcome(menuPage.getModule(), action.getOutcome());
				String actionPagePath = JsfbootPageNavigator.getToViewId(fillOutcome, pageUrl);
				if (StringKit.equals(pageUrl, actionPagePath.toLowerCase())){
					return action;
				}
			}
			
			// 看是否是公共页面
			if (action.getPublicPages().contains(pageUrl)){
				return action;
			}
		}
		
		return null;
	}
	
	/**
	 * 移除url中的查询字符串
	 * @param pageUrl
	 * @return
	 */
	private static String removeUrlQueryParam (String pageUrl){
		if (StringKit.isEmpty(pageUrl))
			return pageUrl;
		
		if (pageUrl.indexOf("?") >= 0){
			pageUrl = pageUrl.substring(0, pageUrl.indexOf("?"));
		}
		return pageUrl;
	}
}
