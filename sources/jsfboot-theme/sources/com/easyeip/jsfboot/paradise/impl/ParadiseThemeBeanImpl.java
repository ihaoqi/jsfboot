package com.easyeip.jsfboot.paradise.impl;

import org.primefaces.model.menu.MenuModel;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.SecutiryService;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.surface.MenuModelUtils;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.paradise.ParadiseConfigForm;
import com.easyeip.jsfboot.paradise.ParadiseConfigValue;
import com.easyeip.jsfboot.paradise.ParadiseThemeBean;

/**
 * 应用级别的jsf bean
 * @author ihaoqi
 *
 */
public class ParadiseThemeBeanImpl extends JsfbootBean implements ParadiseThemeBean {
	
	@UseJsfbootService (SecutiryService.class)
	SecutiryService secutiry;
	
	@UseJsfbootService (SurfaceService.class)
	SurfaceService surface;
		
	private MenuModel adminMenuModel;
	private MenuModel siteMenuModel;
	private MenuModel topMenuModel;
	private MenuModel personMenuModel;
	private long lastChangeTime = 0;
	private String lastAccountId;
	
	private ParadiseConfigValue configValue;
	private ParadiseConfigForm configForm;
	
	public ParadiseThemeBeanImpl(){
		lastChangeTime = surface.getMenuManager().getLastApplyTime();
	}
	
	void clearMenuModelForMenuChanged(){
		if (lastChangeTime != surface.getMenuManager().getLastApplyTime()){
			adminMenuModel = null;
			siteMenuModel = null;
			personMenuModel = null;
			topMenuModel = null;
		}
	}

	@Override
	public MenuModel getAdminMenu() {
		clearMenuModelForMenuChanged();
		
		if (adminMenuModel == null || !StringKit.equals(lastAccountId, getUserName())){
			// 按权限配置过滤后台菜单
			SiteMenuBar menubar = secutiry.filterAccountMenuBar(PageDomainType.Admin, 
								SiteMenuManager.MAIN_MENUBAR, FacesUtils.getServletRequest());
			
			adminMenuModel = MenuModelUtils.convertMenuModel (
					menubar, PageDomainType.Admin,SiteMenuManager.MAIN_MENUBAR);
			lastChangeTime = surface.getMenuManager().getLastApplyTime();
			
			lastAccountId = getUserName();
		}
		return adminMenuModel;
	}
	
	@Override
	public MenuModel getSiteMenu() {
		clearMenuModelForMenuChanged();
		
		if (siteMenuModel == null || !StringKit.equals(lastAccountId, getUserName())){
			// 按权限配置过滤前台菜单
			SiteMenuBar menubar = secutiry.filterAccountMenuBar(PageDomainType.Site,
									SiteMenuManager.MAIN_MENUBAR, FacesUtils.getServletRequest());

			siteMenuModel = MenuModelUtils.convertMenuModel (
							menubar, PageDomainType.Site, SiteMenuManager.MAIN_MENUBAR);
//			if (siteMenuModel.getElements().size() > 0){
//				siteMenuModel.addElement(new DefaultSeparator());
//			}
			lastChangeTime = surface.getMenuManager().getLastApplyTime();
			lastAccountId = getUserName();
		}
		return siteMenuModel;
	}
	
	PageDomainType getPageDomain(PageDomainType defType){
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(FacesUtils.getServletRequest());
		PageDomainType pdType = (navPath != null ? navPath.getPageDomain() : defType);
		return pdType;
	}

	@Override
	public MenuModel getPersonMenu() {
		clearMenuModelForMenuChanged();
		
		if (personMenuModel == null || !StringKit.equals(lastAccountId, getUserName())){
			PageDomainType pageDomain = getPageDomain(PageDomainType.Site);
			SiteMenuBar menubar = secutiry.filterAccountMenuBar(pageDomain,
									PERSON_MENUBAR, FacesUtils.getServletRequest());
			personMenuModel = MenuModelUtils.convertMenuModel(
							menubar, pageDomain, PERSON_MENUBAR, false, false);
			lastChangeTime = surface.getMenuManager().getLastApplyTime();
		}
		return personMenuModel;
	}
	
	@Override
	public MenuModel getLinkMenu() {
		clearMenuModelForMenuChanged();
		
		if (topMenuModel == null || !StringKit.equals(lastAccountId, getUserName())){
			PageDomainType pageDomain = getPageDomain(PageDomainType.Site);
			SiteMenuBar menubar = secutiry.filterAccountMenuBar(pageDomain,
									LINK_MENUBAR, FacesUtils.getServletRequest());
			topMenuModel = MenuModelUtils.convertMenuModel(
							menubar, pageDomain, LINK_MENUBAR, false, false);
			lastChangeTime = surface.getMenuManager().getLastApplyTime();
		}
		return topMenuModel;
	}
	
	public String getUserName(){
		AccountDetails account = secutiry.getAuthentication();
		if (account != null)
			return account.getLoginId();
		return "游客";
	}

	@Override
	public ParadiseConfigValue getConfig() {
		if (configValue == null){
			configValue = new ParadiseConfigValueImpl(JsfbootContext.getDriver().getRegistryService());
		}
		return configValue;
	}

	@Override
	public ParadiseConfigForm getConfigForm() {
		if (configForm == null){
			configForm = new ParadiseConfigFormImpl(JsfbootContext.getDriver().getRegistryService());
		}
		return configForm;
	}

	@Override
	public void saveConfigForm() {
		if (configForm != null){
			try{
				configForm.save();
			}catch (Exception e){
				FacesUtils.addMessageError("保存配置", "保存出错：" + e.getMessage());
				return;
			}
			configForm = null;
			configValue = null;
			FacesUtils.addMessageInfo("保存配置", "配置保存成功。");
		}
	}

	@Override
	public void resetConfigForm() {
		configForm = null;
	}

	@Override
	public String getAccountUsername() {
		AccountDetails acc = secutiry.getAuthentication();
		if (acc != null)
			return acc.getNickname();
		return "游客";
	}

	@Override
	public String getAccountFirstRole() {
		AccountDetails acc = secutiry.getAuthentication();
		if (acc != null && acc.getGrantRoles().size() > 0)
			return acc.getGrantRoles().get(0);
		return "anonymous";
	}
}
