package com.easyeip.jsfboot.core.surface.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;

public class SubMenuContainerImpl implements SubMenuContainer {

	private List<SiteMenuItem> menuList;
	private List<SiteMenuItem> readOnlyList;
	
	public SubMenuContainerImpl(){
		menuList = new ArrayList<SiteMenuItem>();
	}
	
	@Override
	public int getSubMenuCount() {
		return menuList.size();
	}

	@Override
	public List<SiteMenuItem> getSubMenuList() {
		if (readOnlyList == null){
			readOnlyList = Collections.unmodifiableList(menuList);
		}
		return readOnlyList;
	}

	@Override
	public SiteMenuItem getSubMenu(int index) {
		return menuList.get(index);
	}

	@Override
	public SiteMenuItem addSubMenu(MenuItemType type) {
		
		SiteMenuItem menu = new SiteMenuItemImpl (type, this);
		menuList.add(menu);
		readOnlyList = null;
		
		return menu;
	}

	@Override
	public SiteMenuItem insertSubMenu(MenuItemType type, int index) {
		
		return insertSubMenu (new SiteMenuItemImpl (type, this), index);

	}
	
	@Override
	public SiteMenuItem insertSubMenu(SiteMenuItem menu, int index) {
		menu.setContainer(this);
		menuList.add(index, menu);
		readOnlyList = null;
		return menu;
	}


	@Override
	public SiteMenuItem removeSubMenu(int index) {
		readOnlyList = null;
		return menuList.remove(index);
	}

	@Override
	public void clearSubMenu() {
		readOnlyList = null;
		menuList.clear();
	}

	@Override
	public SiteMenuItem findSubMenu(String name, boolean downward) {
		for (SiteMenuItem menu : menuList){
			if (menu.getName().equals(name)){
				return menu;
			}
			
			if (downward == true){
				SiteMenuItem find = menu.findSubMenu(name, downward);
				if (find != null){
					return find;
				}
			}
		}
		return null;
	}
}
