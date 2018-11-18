package com.easyeip.jsfboot.admin.bean.impl;

import com.easyeip.jsfboot.admin.bean.type.MenuIcon;

public class MenuIconImpl implements MenuIcon {
	
	String cssClass;
	String iconName;
	
	
	public MenuIconImpl(String iconName, String cssClass){
		this.cssClass = cssClass;
		this.iconName = iconName;
	}

	@Override
	public String getCssClass() {
		return cssClass;
	}

	@Override
	public String getIconName() {
		return iconName;
	}

}
