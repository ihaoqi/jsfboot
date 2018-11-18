package com.easyeip.jsfboot.admin.bean.form;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.admin.bean.type.MenuPositionPair;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.surface.MenuDataStorage;
import com.easyeip.jsfboot.core.surface.ThemeMenuPosition;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.core.surface.PageDomainType;

/**
 * 设置菜单位置
 * @author ihaoqi
 *
 */
public class MenuPositionForm {
	
	private MenuDataStorage menuData;
	private JsfbootTheme siteTheme;
	private JsfbootTheme adminTheme;
	
	private List<MenuPositionPair> siteMenupair;
	private List<MenuPositionPair> adminMenupair;
	
	public MenuPositionForm(MenuDataStorage data){
		this.menuData = data;

		// 取得前端主题和占位
		siteMenupair = new ArrayList<MenuPositionPair>();
		siteTheme = initThemePair (PageDomainType.Site, siteMenupair);
		
		adminMenupair = new ArrayList<MenuPositionPair>();
		adminTheme = initThemePair (PageDomainType.Admin, adminMenupair);
	}
	
	private JsfbootTheme initThemePair(PageDomainType themeType, List<MenuPositionPair> menupair){
		
		SurfaceService surface = JsfbootContext.getDriver().getSurfaceService();
		JsfbootTheme theme = surface.getCurrentTheme(themeType);
		
		Map<String,String> oldPair = menuData.getPositionPair(themeType).getPairMap();
		
		// 初使化当前的菜单配置
		for (MenuPosition mp : theme.getMenuPosition()){
			
			// 去除后台的主菜单（不可配置）
			if (themeType == PageDomainType.Admin &&
					mp.getName().equals(SiteMenuManager.MAIN_MENUBAR))
				continue;
			
			MenuPositionPair pair = new MenuPositionPair(mp);
			String oldMenubar = oldPair.get(pair.getName());
			if (!StringKit.isEmpty(oldMenubar) && checkMenubarExist(oldMenubar)){
				pair.setMenubar(oldMenubar);
//			}else if (checkMenubarExist(pair.getName())){
//				// 如果菜单为空或不存在，并且有同名的，则设置
//				pair.setMenubar(pair.getName());
			}else{
				pair.setMenubar("");
			}
			menupair.add(pair);
		}
		
		return theme;
	}
	
	private boolean checkMenubarExist (String name){
		return menuData.getSiteMenubar(name, false) != null;
	}
	
	/**
	 * 获取前端主题名称
	 * @return
	 */
	public String getSiteThemeName(){
		return siteTheme.getModulePackage().getModuleInfo().getModuleName();
	}
	
	public String getAdminThemeName(){
		return adminTheme.getModulePackage().getModuleInfo().getModuleName();
	}

	/**
	 * 获取前端主题的菜单占位表
	 * @return
	 */
	public List<MenuPositionPair> getSitePosition(){
		return siteMenupair;
	}
	
	public List<MenuPositionPair> getAdminPosition(){
		return adminMenupair;
	}
	
	/**
	 * 保存起来
	 * @return
	 */
	public boolean update(){
		
		SiteMenuManager menuMgr = JsfbootContext.getDriver().getSurfaceService().getMenuManager();
		
		ThemeMenuPosition posMgr = menuData.getPositionPair(PageDomainType.Site);
		for (MenuPositionPair pair : siteMenupair){
			posMgr.pairMenuBar(pair.getName(), pair.getMenubar());
		}
		
		posMgr = menuData.getPositionPair(PageDomainType.Admin);
		for (MenuPositionPair pair : adminMenupair){
			posMgr.pairMenuBar(pair.getName(), pair.getMenubar());
		}
		
		menuMgr.applySiteMenuData(menuData);
		
		return true;
	}
}
