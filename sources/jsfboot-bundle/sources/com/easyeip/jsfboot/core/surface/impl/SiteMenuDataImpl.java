package com.easyeip.jsfboot.core.surface.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.surface.MenuDataStorage;
import com.easyeip.jsfboot.core.surface.ThemeMenuPosition;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;

public class SiteMenuDataImpl implements MenuDataStorage {

	private List<SiteMenuBar> menubarList;
	private List<SiteMenuBar> menubarListReadOnly;	// 只读的的menubarList
	private Map<PageDomainType, ThemeMenuPosition> menuPosition;
	
	private SiteMenuBar adminMenubar;
	
	public SiteMenuDataImpl(SiteMenuBar adminMenubar){
		
		this.adminMenubar = adminMenubar;
		
		menubarList = new ArrayList<SiteMenuBar>();
		
		menuPosition = new HashMap<PageDomainType, ThemeMenuPosition>();
	}
	
	SiteMenuDataImpl cloneMy(){
		
		// 复制菜单
		SiteMenuDataImpl newData = new SiteMenuDataImpl(adminMenubar);
		if (menubarList != null){
			for (SiteMenuBar bar : menubarList){
				newData.getSiteMenubar(bar.getName(), true).copyForm(bar);
			}
		}
		
		// 复制占位
		newData.getPositionPair(PageDomainType.Site).copyForm(
							getPositionPair(PageDomainType.Site));
		newData.getPositionPair(PageDomainType.Admin).copyForm(
							getPositionPair(PageDomainType.Admin));

		return newData;
	}
	
	@Override
	public List<SiteMenuBar> allSiteMenubar() {
		if (menubarListReadOnly == null){
			menubarListReadOnly = Collections.unmodifiableList(menubarList);
		}
		return menubarListReadOnly;
	}

	@Override
	public SiteMenuBar getSiteMenubar(String menuName, boolean create) {
		SiteMenuBar menuBar = findMenuBar(menuName);
		if (menuBar == null && create){
			menuBar = addSiteMenubar(menuName);
		}
		return menuBar;
	}

	@Override
	public SiteMenuBar addSiteMenubar(String menuName) {
		SiteMenuBar exist = findMenuBar(menuName);
		if (exist != null)
			return exist;
		
		SiteMenuBar newMenuBar = new SiteMenuBarImpl (menuName); 
		
		menubarList.add(newMenuBar);
		
		menubarListReadOnly = null;
		return newMenuBar;
	}

	@Override
	public SiteMenuBar removeSiteMenubar(String menuName) {
		SiteMenuBar exist = findMenuBar(menuName);
		if (exist != null){
			menubarList.remove(exist);
			menubarListReadOnly = null;
		}
		
		return exist;
	}
	
	@Override
	public SiteMenuBar getThemeMenubar(PageDomainType theme, String positionName) {
		
		// 查找配对的菜单
		String menuName = getPositionPair(theme).getPairMap().get(positionName);
		
		// 没有配对的话就查找同名菜单
		if (StringKit.isEmpty(menuName)){
			menuName = positionName;
		}
		
		// 返回后台管理菜单
		if (theme == PageDomainType.Admin && StringKit.equals(SiteMenuManager.MAIN_MENUBAR, menuName)){
			return adminMenubar;
		}
		
		SiteMenuBar bar = null;
		if (!StringKit.isEmpty(menuName)){
			bar = this.getSiteMenubar(menuName, false);
		}

		return bar;
	}

	@Override
	public ThemeMenuPosition getPositionPair(PageDomainType theme) {
		ThemeMenuPosition pos = menuPosition.get(theme);
		if (pos == null){
			pos = new ThemeMenuPositionImpl();
			menuPosition.put(theme, pos);
		}
		return pos;
	}
	
	private SiteMenuBar findMenuBar (String name){
		for (SiteMenuBar bar : menubarList){
			if (bar.getName().equals(name))
				return bar;
		}
		
		return null;
	}
}
