package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.ModuleAdminMenu;
import com.easyeip.jsfboot.core.module.type.schema.BackMenuType;
import com.easyeip.jsfboot.core.module.type.schema.MenuPageType;

public class ModuleAdminMenuImpl implements ModuleAdminMenu {
	
	private JsfbootModule module;
	private BackMenuType backMenu;
	private List<MenuPage> menuList;

	public ModuleAdminMenuImpl(JsfbootModule module, BackMenuType backMenu) {
		this.module = module;
		this.backMenu = backMenu;
	}

	@Override
	public List<MenuPage> getMenuList() {
		if (menuList == null){
			menuList = new ArrayList<MenuPage> ();
			
			for (MenuPageType page : backMenu.getMenuItem()){
				
				menuList.add(new MenuPageImpl (module, page, null));
				
			}
		}
		
		return menuList;
	}

	@Override
	public String getAnchor() {
		return backMenu.getAnchor();
	}

	@Override
	public String getPosition() {
		return backMenu.getPosition();
	}

}
