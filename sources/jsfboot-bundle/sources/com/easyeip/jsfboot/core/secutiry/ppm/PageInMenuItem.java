package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public class PageInMenuItem {
	
	private SiteMenuItem menuItem;
	private String pagePath;
	private PageAction pageAction;
	private boolean isPublicPage;
	
	public PageInMenuItem(SiteMenuItem menuItem, String pagePath, PageAction pageAction, boolean isPublicPage){
		this.menuItem = menuItem;
		this.pagePath = pagePath;
		this.pageAction = pageAction;
		this.isPublicPage = isPublicPage;
	}

	public SiteMenuItem getMenuItem(){
		return this.menuItem;
	}
	
	public String getPagePath(){
		return this.pagePath;
	}
	
	public PageAction getPageAction(){
		return this.pageAction;
	}

	public boolean isPublicPage() {
		return this.isPublicPage;
	}
}
