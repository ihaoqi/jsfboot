package com.easyeip.jsfboot.admin.bean.type;

import com.easyeip.jsfboot.core.module.type.MenuPosition;

/**
 * 菜单条对应的主题配对
 * @author ihaoqi
 *
 */
public class MenuPositionPair implements MenuPosition {
	
	private MenuPosition holder;
	private String menubar;
	
	public MenuPositionPair(MenuPosition holder){
		this.holder = holder;
	}

	@Override
	public String getName() {
		return holder.getName();
	}

	@Override
	public String getTitle() {
		return holder.getTitle();
	}

	public String getMenubar() {
		return menubar;
	}

	public void setMenubar(String menubar) {
		this.menubar = menubar;
	}

}
