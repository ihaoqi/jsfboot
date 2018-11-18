package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;

public class AccessDecisionMenu {
	
	private PageDomainType pageDomain;
	private String menuPosition;
	private SiteMenuBar menubar;
	
	public AccessDecisionMenu(PageDomainType pageDomain, String menuPosition){
		this.pageDomain = pageDomain;
		this.menuPosition = menuPosition;
	}
	
	public AccessDecisionMenu(PageDomainType pageDomain, String menuPosition, SiteMenuBar menubar){
		this(pageDomain, menuPosition);
		this.menubar = menubar;
	}
	
	public String getKey(){
		return pageDomain.name() + "_" + menuPosition;
	}
	
	public SiteMenuBar getMenubar() {
		return menubar;
	}

	public String getMenuPosition() {
		return menuPosition;
	}

	public PageDomainType getPageDomain() {
		return pageDomain;
	}

}
