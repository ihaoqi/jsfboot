package com.easyeip.jsfboot.core.surface;

import java.util.List;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.UserMenuProvider;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.ModuleAdminMenu;

public class MenuPageUtils {
	
	/**
	 * 查询页面来源
	 * @param source 页面定义
	 * @param domain 要查找的位置
	 * @param strictMatch 是否严格查找
	 * @return
	 */
	public static MenuPage findMenuPageBySource(MenuPageSource source, PageDomainType domain, boolean strictMatch){
	
		if (source == null) return null;
		
		ModuleManager mm = JsfbootContext.getDriver().getModuleManager();
		JsfbootModule module = mm.findModuleByName(source.getModuleName());
		if (module == null) return null;
		
		MenuPage findPage = null;
		if (domain == PageDomainType.Site){
			UserMenuProvider ump = module.getPageResource().getSiteMenu();
			if (ump != null){
				if (strictMatch){
					findPage = findPageResource(ump.getMenuList(), source.getMenuPath());
				}else{
					findPage = findPageByPageName (ump.getMenuList(), getNameFormPath(source.getMenuPath()));
				}
			}
		}else{
			ModuleAdminMenu mam = module.getPageResource().getAdminMenu();
			if (mam != null){
				if (strictMatch){
					findPage = findPageResource(mam.getMenuList(), source.getMenuPath());
				}else{
					findPage = findPageByPageName (mam.getMenuList(), getNameFormPath(source.getMenuPath()));
				}
			}
		}
		
		return findPage;
	}
	
	/**
	 * 查询页面来源
	 * @param source 页面定义
	 * @param onlySite 是否只查询前端
	 * @param strictMatch 是否严格查找
	 * @return
	 */
	public static MenuPage findMenuPageBySource(MenuPageSource source, boolean onlySite, boolean strictMatch){
		

		MenuPage findPage = findMenuPageBySource(source, PageDomainType.Site, strictMatch);

		if (findPage == null && onlySite == false){
			findPage = findMenuPageBySource(source, PageDomainType.Admin, strictMatch);
		}
		
		return findPage;
	}
	
	/**
	 * 从路径中取得菜单名称
	 * @param path
	 * @return
	 */
	private static String getNameFormPath(String path){
		List<String> pp = StringKit.stringSplit(path, "/");
		if (pp.size() > 0)
			return pp.get(pp.size() - 1);
		return null;
	}
	
	/**
	 * 查询页面是否存在（只查询名称）
	 * @param list
	 * @param name
	 * @return
	 */
	private static MenuPage findPageByPageName (List<MenuPage> list, String name){
		for (MenuPage page : list){
			// 排除目录
			if (page.getSubMenu().size() > 0){
				MenuPage find = findPageByPageName(page.getSubMenu(), name);
				if (find != null)
					return find;
			}else if (StringKit.equals(page.getName(), name)){
				return page;
			}
		}
		
		return null;
	}
	
	/**
	 * 查找页面是否存在（按路径完全匹配）
	 * @param list
	 * @param path
	 * @return
	 */
	private static MenuPage findPageResource (List<MenuPage> list, String path){
		for (MenuPage page : list){
			if (StringKit.equals(page.getPath(), path))
				return page;
			
			MenuPage find = findPageResource(page.getSubMenu(), path);
			if (find != null)
				return find;
		}
		return null;
	}

}
