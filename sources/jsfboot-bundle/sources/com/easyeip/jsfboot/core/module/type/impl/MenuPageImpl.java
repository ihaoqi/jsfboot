package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.module.type.schema.MenuPageType;
import com.easyeip.jsfboot.core.module.type.schema.PageActionType;
import com.easyeip.jsfboot.utils.StringKit;

public class MenuPageImpl implements MenuPage {
	
	JsfbootModule module;
	
	MenuPageType menu;
	MenuPageImpl parent;
	List<MenuPage> sublist;
	List<PageAction> actionlist;
	List<String> publicPages;

	public MenuPageImpl(JsfbootModule module, MenuPageType userMenu, MenuPageImpl parent) {
		this.module = module;
		this.menu = userMenu;
		this.parent = parent;
	}
	
	public String toString(){
		return getPath();
	}

	@Override
	public String getName() {
		return menu.getName();
	}

	@Override
	public String getTitle() {
		return menu.getTitle();
	}

	@Override
	public String getOutcome() {
		return OutcomeUtils.fullOutcome(module, menu.getOutcome());
	}

	@Override
	public String getIcon() {
		return menu.getIcon();
	}

	@Override
	public List<MenuPage> getSubMenu() {
		if (sublist == null){
			sublist = new ArrayList<MenuPage> ();
			if (menu.getMenuItem() != null){
				for (MenuPageType mpt : menu.getMenuItem()){
					sublist.add(new MenuPageImpl (module, mpt, this));
				}
			}
			sublist = Collections.unmodifiableList(sublist);
		}
		return sublist;
	}

	@Override
	public List<PageAction> getPageActions() {
		if (actionlist == null){
			actionlist = new ArrayList<PageAction> ();
			if (menu.getPageAction() != null){
				for (PageActionType pat : menu.getPageAction()){
					actionlist.add(new PageActionImpl (module, pat));
				}
			}
			actionlist = Collections.unmodifiableList(actionlist);
		}
		return actionlist;
	}

	@Override
	public String getPath() {
		String path = "/" + this.getName();
		MenuPageImpl part = parent;
		while (part != null){
			path = "/" + part.getName() + path;
			part = part.parent;
		}
		return path;
	}

	@Override
	public JsfbootModule getModule() {
		return module;
	}

	@Override
	public List<String> getPublicPages() {
		if (publicPages == null){
			publicPages = new ArrayList<String> ();
			for (String p : StringKit.stringSplit(menu.getPublicPages(), ",")){
				publicPages.add(OutcomeUtils.fullOutcome(module, p));
			}
		}
		return publicPages;
	}

}
