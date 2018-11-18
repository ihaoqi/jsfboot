package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.UserMenuProvider;
import com.easyeip.jsfboot.core.module.type.ModuleAdminMenu;
import com.easyeip.jsfboot.core.module.type.PageResource;
import com.easyeip.jsfboot.core.module.type.schema.PageResourceType;

public class PageResourceImpl implements PageResource {
	
	private PageResourceType raw;
	private JsfbootModule module;
	
	private UserMenuFactory userMenu;
	private ModuleAdminMenu backMenu;
	private String confPage;
	private List<String> publicPages;

	public PageResourceImpl(JsfbootModule pack, PageResourceType pageResource) {
		this.module = pack;
		this.raw = pageResource;
		
		if (raw != null && !StringKit.isEmpty(raw.getConfPage())){
			confPage = OutcomeUtils.fullOutcome(module, raw.getConfPage());
		}
	}

	@Override
	public UserMenuProvider getSiteMenu() {
		if (raw == null || raw.getSiteMenu() == null)
			return null;
		
		if (userMenu == null){
			String service = raw.getSiteMenu().getUseService();
			if (StringKit.isEmpty(service)){
				userMenu = new UserMenuFactory (new ModuleUserMenuImpl(module, raw.getSiteMenu()));
			}else{
				userMenu = new UserMenuFactory(service);
			}
		}
		
		return userMenu.getProvider();
	}

	@Override
	public ModuleAdminMenu getAdminMenu() {
		if (raw == null || raw.getAdminMenu() == null)
			return null;
		
		if (backMenu == null){
			backMenu = new ModuleAdminMenuImpl(module, raw.getAdminMenu());
		}
		
		return backMenu;
	}

	@Override
	public String getConfPage() {
		return confPage;
	}
	
	@Override
	public List<String> getPublicPages() {
		if (publicPages == null){
			publicPages = new ArrayList<String>();
			if (raw == null)
				return publicPages;
			String pages = raw.getPublicPages();
			for (String p : StringKit.stringSplit(pages, ",")){
				publicPages.add(OutcomeUtils.fullOutcome(module, p));
			}
		}
		return publicPages;
	}
	
	private class UserMenuFactory{
		private UserMenuProvider provider = null;
		private String service;
		
		public UserMenuFactory(String service){
			this.service = service;
		}
		
		public UserMenuFactory(UserMenuProvider provider){
			this.provider = provider;
		}
		
		public UserMenuProvider getProvider(){
			if (provider != null)
				return provider;

			// 取得服务
			if (!StringKit.isEmpty(service)){
				Object obj = JsfbootContext.getDriver().getServiceManager().getService(service);
				if (obj instanceof UserMenuProvider)
					provider = (UserMenuProvider) obj;
			}
			return provider;
		}
	}
}
