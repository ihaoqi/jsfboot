package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.utils.StringKit;

public class DefaultVoterMenuDetails implements VoterMenuDetails {
	
	private PageDomainType pageDomain;
	private String menuPosition;
	private SiteMenuItem menuItem;
	private String actionName;
	
	public DefaultVoterMenuDetails(PageDomainType pageDomain, String menuPosition,
									SiteMenuItem menuItem, String actionName){
		this.pageDomain = pageDomain;
		this.menuPosition = menuPosition;
		this.menuItem = menuItem;
		this.actionName = actionName;
	}
	
	public String toString(){
		String val = getPageDomain().toString() + "/" + getMenuName();
		if (StringKit.notEmpty(actionName)){
			val += ":" + getActionName();
		}
		
		return val;
	}

	@Override
	public PageDomainType getPageDomain() {
		return pageDomain;
	}

	@Override
	public String getMenuPosition() {
		return menuPosition;
	}

	@Override
	public String getMenuName() {
		return menuItem.getPermName();
	}

	@Override
	public String getActionName() {
		return actionName;
	}

}
