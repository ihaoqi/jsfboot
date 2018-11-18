package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.ThemePageTemplate;
import com.easyeip.jsfboot.core.module.type.schema.DefineThemeType;
import com.easyeip.jsfboot.core.module.type.schema.PositionItemType;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;

public class ModuleThemeImpl implements JsfbootTheme {
	
	private JsfbootModule pack;
	private DefineThemeType themeType;
	private ThemePageTemplate pageTemplate;
	
	private List<MenuPosition> placeholder;
	
	public ModuleThemeImpl (JsfbootModule pack, DefineThemeType themeType){
		this.themeType = themeType;
		this.pack = pack;
	}
	
	@Override
	public String toString(){
		return this.getName();
	}

	@Override
	public String getPrimefacesTheme() {
		return themeType.getPrimefacesTheme();
	}

	@Override
	public ThemePageTemplate getPageTemplate() {
		if (pageTemplate == null){
			pageTemplate = new ThemePageTemplateImpl (pack, themeType.getPageTemplate());
		}
		
		return pageTemplate;
	}

	@Override
	public JsfbootModule getModulePackage() {
		return pack;
	}

	@Override
	public List<MenuPosition> getMenuPosition() {
		
		if (placeholder == null){
			placeholder = new ArrayList<MenuPosition>();
			
			Map<String, String> nameMap = new HashMap<String, String> ();
			
			// 先默认加上主菜单
			placeholder.add(new MenuPositionImpl(SiteMenuManager.MAIN_MENUBAR, SiteMenuManager.MAIN_MENUBAR_TITLE));
			nameMap.put(SiteMenuManager.MAIN_MENUBAR, SiteMenuManager.MAIN_MENUBAR_TITLE);
			
			if (themeType.getMenuPosition() != null){
				for (PositionItemType pi : themeType.getMenuPosition().getPosition()){
					
					if (nameMap.get(pi.getName()) == null){
						placeholder.add(new MenuPositionImpl(pi));
						nameMap.put(pi.getName(), pi.getTitle());
					}else{
						
					}
				}
			}
			
			nameMap.clear();
		}
		return placeholder;
	}

	@Override
	public String getConfPage() {
		return OutcomeUtils.fullOutcome(pack, themeType.getConfPage());
	}

	@Override
	public boolean isUseSite() {
		return themeType.isUseSite();
	}

	@Override
	public boolean isUseAdmin() {
		return themeType.isUseAdmin();
	}

	@Override
	public String getImagePath() {
		return OutcomeUtils.fullOutcome(pack, themeType.getImage());
	}

	@Override
	public String getName() {
		return pack.getModuleInfo().getModuleName();
	}

}
