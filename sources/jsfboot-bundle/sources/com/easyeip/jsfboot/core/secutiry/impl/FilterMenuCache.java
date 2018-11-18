package com.easyeip.jsfboot.core.secutiry.impl;

import com.easyeip.jsfboot.core.surface.SiteMenuBar;

public class FilterMenuCache {
	private long lastTime;
	private SiteMenuBar menubar;
	
	public long getLastTime() {
		return lastTime;
	}
	public void setLastTime(long lastTime) {
		this.lastTime = lastTime;
	}
	public SiteMenuBar getMenubar() {
		return menubar;
	}
	public void setMenubar(SiteMenuBar menubar) {
		this.menubar = menubar;
	}

}
