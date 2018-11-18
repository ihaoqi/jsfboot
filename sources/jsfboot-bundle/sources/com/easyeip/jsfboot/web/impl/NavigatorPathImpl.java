package com.easyeip.jsfboot.web.impl;

import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public class NavigatorPathImpl implements NavigatorPath {
	
	private String jpnServlet;
	private String requestPath;
	private PageDomainType themeType;
	private String menuPosition;
	private String menuPage;
	private String queryStr;
	private String forwardPath;
	private SiteMenuItem menuItem;
	
	public NavigatorPathImpl(String requestPath, PageDomainType themeType,
			String menuPosition, String menuPage, String queryStr){
		this.requestPath = requestPath;
		this.themeType = themeType;
		this.menuPosition = menuPosition;
		this.menuPage = menuPage;
		this.queryStr = queryStr;
	}

	@Override
	public PageDomainType getPageDomain() {
		return themeType;
	}

	@Override
	public String getMenuPosition() {
		return menuPosition;
	}

	@Override
	public String getMenuPage() {
		return menuPage;
	}

	@Override
	public String getQueryString() {
		return queryStr;
	}

	@Override
	public String getRequestPath() {
		return requestPath;
	}

	@Override
	public String getForwardPath() {
		return forwardPath;
	}
	
	public void setForwardPath(String viewId){
		this.forwardPath = viewId;
	}

	@Override
	public String getJpnServlet() {
		return jpnServlet;
	}
	
	public void setJpnServlet(String path){
		this.jpnServlet = path;
	}

	@Override
	public void setMenuPage(String menuPage) {
		this.menuPage = menuPage;
	}

	@Override
	public SiteMenuItem getMenuItem() {
		return menuItem;
	}

	@Override
	public void setMenuItem(SiteMenuItem menuItem) {
		this.menuItem = menuItem;
	}
}
