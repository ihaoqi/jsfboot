package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.ModuleUserMenu;
import com.easyeip.jsfboot.core.module.type.schema.MenuPageType;
import com.easyeip.jsfboot.core.module.type.schema.UserMenuType;

public class ModuleUserMenuImpl implements ModuleUserMenu {
	
	private JsfbootModule module;
	private UserMenuType frontMenu;
	private List<MenuPage> menuList;

	public ModuleUserMenuImpl(JsfbootModule module, UserMenuType frontMenu) {
		this.module = module;
		this.frontMenu = frontMenu;
	}

	@Override
	public List<MenuPage> getMenuList() {
		
		if (menuList == null){
			menuList = new ArrayList<MenuPage> ();
			
			for (MenuPageType page : frontMenu.getMenuItem()){
				
				menuList.add(new MenuPageImpl (module, page, null));
				
			}
		}
		
		return menuList;
	}

}
