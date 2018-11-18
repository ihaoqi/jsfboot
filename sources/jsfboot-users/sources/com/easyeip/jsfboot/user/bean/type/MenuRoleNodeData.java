package com.easyeip.jsfboot.user.bean.type;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public class MenuRoleNodeData {
	
	private MenuPositionDetails menuDetails;
	private SiteMenuItem menuItem;
	private PageAction pageAction;
	private Map<String, MenuRoleRelation> roles;
	
	public MenuRoleNodeData(MenuPositionDetails menuDetails, SiteMenuItem menuItem){
		this.menuDetails = menuDetails;
		this.menuItem = menuItem;
		roles = new HashMap<String, MenuRoleRelation>();
	}
	
	public MenuRoleNodeData(MenuPositionDetails menuDetails,
							SiteMenuItem menuItem, PageAction menuAction){
		this(menuDetails, menuItem);
		this.pageAction = menuAction;
	}
	
	public Map<String, MenuRoleRelation> getRoles(){
		return roles;
	}
	
	public PageDomainType getPageDomain(){
		return this.menuDetails.getDomain();
	}
	
	public MenuPosition getMenuPosition(){
		return this.menuDetails.getPosition();
	}
	
	public SiteMenuItem getMenuItem(){
		return this.menuItem;
	}

	public PageAction getMenuAction() {
		return pageAction;
	}
	
	public String getTitle(){
		if (pageAction != null)
			return "功能: " + pageAction.getTitle();
		if (menuItem != null)
			return menuItem.getTitle();
		
		return menuDetails.getDomain().name() + " - " + menuDetails.getPosition().getTitle();
	}
	
	public String getIcon(){
		if (pageAction != null)
			return "";
		if (menuItem != null)
			return menuItem.getIcon();
		return "";
	}
}
