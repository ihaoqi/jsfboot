package com.easyeip.jsfboot.admin.bean.type;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.MenuPage;

public class MenuTreeInfo {

	private String name;
	private String title;
	private JsfbootModule module;
	private MenuPage menu;
	
	public MenuTreeInfo(String title){
		this.title = title;
		this.name = "";
	}

	public MenuTreeInfo (JsfbootModule module, MenuPage menu){
		this.module = module;
		this.menu = menu;
		if (menu != null){
			this.name = menu.getName();
			this.title = menu.getTitle();
		}else if (module != null){
			this.name = module.getModuleInfo().getModuleName();
			this.title = module.getModuleInfo().getModuleTitle();
		}
	}
	
	@Override
	public String toString() {
		return name + ":" + title;
	}
	
	public JsfbootModule getSrcModule (){
		return module;
	}
	
	public MenuPage getSrcMenu(){
		return menu;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
}
