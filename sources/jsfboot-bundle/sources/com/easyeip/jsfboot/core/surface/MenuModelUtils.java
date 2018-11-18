package com.easyeip.jsfboot.core.surface;

import java.util.List;

import javax.faces.application.ConfigurableNavigationHandler;
import javax.faces.application.NavigationCase;
import javax.faces.context.FacesContext;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.DefaultSeparator;
import org.primefaces.model.menu.DefaultSubMenu;
import org.primefaces.model.menu.MenuElement;
import org.primefaces.model.menu.MenuItem;
import org.primefaces.model.menu.MenuModel;
import org.primefaces.model.menu.Submenu;

import com.easyeip.jsfboot.core.surface.impl.DefaultMenuItemEx;
import com.easyeip.jsfboot.utils.JsfBeanUtils;
import com.easyeip.jsfboot.utils.StringKit;

public class MenuModelUtils {
	
	/**
	 * 把主题类型名称转换成枚举值
	 * @param typeName
	 * @param defType
	 * @return
	 */
	public static PageDomainType themeTypeOf (String typeName, PageDomainType defType){
		if (StringKit.isEmpty(typeName))
			return defType;
		return PageDomainType.valueOf(typeName);
	}
	
	/**
	 * 取得菜单模型中第一个菜单路径
	 * @param model
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static String getFirstMenuViewId (MenuModel model){
		List<MenuElement> list = model.getElements();
		
		MenuItem firstItem = null;
		
		while (list != null && list.size() > 0 && firstItem == null){
			for (MenuElement menu : list){
				if (menu instanceof Submenu){
					list = ((Submenu) menu).getElements();
					break;
				}else{
					firstItem = (MenuItem) menu;
					break;
				}
			}
		}
		if (firstItem == null){
			return null;
		}
		
		if (!StringKit.isEmpty(firstItem.getUrl()))
			return firstItem.getUrl();
		
		// 转换成实际url
		NavigationCase nav = findNavigationCase(FacesContext.getCurrentInstance(), firstItem.getOutcome());
		if (nav != null)
			return nav.getToViewId(FacesContext.getCurrentInstance());
		return null;
	}
	
	/**
	 * 取得菜单模型中第一个菜单路径
	 * @param model
	 * @return
	 */
	public static String getFirstMenuViewId (SiteMenuBar model){
		List<SiteMenuItem> list = model.asList(false);
		for (SiteMenuItem mi : list){
			if (mi.getType() == MenuItemType.MenuLink){
				return mi.getUrl();
			}else if (mi.getType() == MenuItemType.MenuPage){
				NavigationCase nav = findNavigationCase(FacesContext.getCurrentInstance(), mi.getOutcome());
				if (nav != null)
					return nav.getToViewId(FacesContext.getCurrentInstance());
				break;
			}
		}
		
		return null;
	}
	
	public static SiteMenuItem findFirstNavMenu (SiteMenuBar model, boolean onlyPage){
		List<SiteMenuItem> list = model.asList(false);
		for (SiteMenuItem mi : list){
			
			if (onlyPage == true && mi.getType() != MenuItemType.MenuPage)
				continue;
			
			return mi;
		}
		
		return null;
	}
	
	public static SiteMenuItem findMenuByPermName (SiteMenuBar model, String permName, boolean onlyPage){
		
		if (StringKit.isEmpty(permName))
			return null;
		
		List<SiteMenuItem> list = model.asList(true);
		for (SiteMenuItem mi : list){
			
			if (onlyPage == true && mi.getType() != MenuItemType.MenuPage)
				continue;
			
			if (StringKit.isEmpty(mi.getPermName()))
				continue;
			
			if (mi.getPermName().equals(permName))
				return mi;
		}
		
		return null;
	}
	
	/**
	 * 根据一个跳转配置查找对应的导航
	 * @param context
	 * @param outcomeTarget
	 * @return
	 */
    public static NavigationCase findNavigationCase(FacesContext context, String outcome) {
        ConfigurableNavigationHandler navigationHandler = 
        	(ConfigurableNavigationHandler) context.getApplication().getNavigationHandler();
        
        if (outcome == null) {
            outcome = context.getViewRoot().getViewId();
        }
        
        return navigationHandler.getNavigationCase(context, null, outcome);
    }
    
	/**
	 * 把主菜单转换成primefaces的菜单
	 * @param menubar	要转换的菜单条
	 * @param isAdmin	是否是后台菜单
	 * @param menuName	主题菜单占位名称
	 * @return
	 */
	public static MenuModel convertMenuModel(SubMenuContainer menubar,
							PageDomainType pageDomain, String menuPosition){
		return convertMenuModel(menubar, null, null, pageDomain, menuPosition, true, true);
	}
	
	/**
	 * 把主菜单转换成primefaces的菜单
	 * @param menubar	要转换的菜单条
	 * @param isAdmin	是否是后台菜单
	 * @param menuName	主题菜单占位名称
	 * @param includeFolder 包含目录
	 * @param includeSeparator 包含分割条
	 * @return
	 */
	public static MenuModel convertMenuModel(SubMenuContainer menubar,
							PageDomainType pageDomain, String menuPosition,
							boolean includeFolder, boolean includeSeparator){
		return convertMenuModel(menubar, null, null, pageDomain, menuPosition,
								includeFolder, includeSeparator);
	}
	
	/**
	 * 把jsfboot菜单转换成primefaces菜单模型
	 * @param menubar
	 * @param model
	 * @param submenu
	 * @param isAdmin	是否是后台菜单
	 * @param menuPosition	主题菜单占位名称
	 * @param includeFolder 是否生成目录
	 * @param includeSeparator 包含分割条
	 * @return
	 */
	private static MenuModel convertMenuModel (SubMenuContainer menubar,
								MenuModel model, DefaultSubMenu submenu, 
								PageDomainType pageDomain, String menuPosition,
								boolean includeFolder, boolean includeSeparator){
		boolean makeIds = false;
		
		if (model == null){
			model = new DefaultMenuModel();
			model.generateUniqueIds();
			makeIds = true;
		}
		
		if (menubar == null)
			return model;
		
		String prefixPath = (pageDomain == PageDomainType.Admin ? "admin/" : "");
		if (!StringKit.isEmpty(menuPosition) &&
				!StringKit.equals(menuPosition, SiteMenuManager.MAIN_MENUBAR)){
			prefixPath += menuPosition + "/";
		}
		
		// 如果group == null就添加到model下，否则添加到group
		for (SiteMenuItem menuItem : menubar.getSubMenuList()){
			
			// 转换菜单项
			MenuElement newItem = null;
			if (!menuItem.isVisible())
				continue;
			
			if (isSeparatorItem(menuItem)){
				
				if (includeSeparator){
					newItem = new DefaultSeparator();
				}
				
			} else if (menuItem.getSubMenuCount() > 0){
				
				if (includeFolder == true){
					// 生成目录
					DefaultSubMenu item = new DefaultSubMenu();
					item.setIcon(menuItem.getIcon());
					item.setLabel(menuItem.getTitle());
					newItem = item;
					
					// 展开第一个菜单
					if (makeIds == true){
						item.setExpanded(true);
					}
				}
				
			}else if (StringKit.isEmpty(menuItem.getOutcome()) == false
					&& menuItem.getPageSource() != null)
			{			
				// 生成模块页面
				DefaultMenuItem item = new DefaultMenuItemEx ();
				item.setIcon(menuItem.getIcon());
				item.setValue(menuItem.getTitle());
				item.setUrl("/jpn/" + prefixPath + menuItem.getPermName());
				if (menuItem.isNewWindow()){
					item.setTarget("_blank");
				}
				if (!StringKit.isEmpty(menuItem.getParams())){
					item.setUrl(urlAddParams(item.getUrl(), menuItem.getParams()));
				}
				
				newItem = item;

			}else if (StringKit.isEmpty(menuItem.getUrl()) == false){
				// 生成连接
				DefaultMenuItem item = new DefaultMenuItemEx ();
				item.setIcon(menuItem.getIcon());
				item.setValue(menuItem.getTitle());
				
				if (menuItem.isNewWindow()){
					item.setTarget("_blank");
				}
				if (!StringKit.isEmpty(menuItem.getParams())){
					item.setUrl(urlAddParams(menuItem.getUrl(), menuItem.getParams()));
				}else{
					item.setUrl(menuItem.getUrl());
				}
				
				newItem = item;
			}
			
			// 添加到模型
			if (newItem != null){
				newItem.setId(menuItem.getPermName());
				
				if (submenu != null){
					submenu.addElement(newItem);
				}else{
					model.addElement(newItem);
				}
			}
			
			// 继续子菜单
			if (menuItem.getSubMenuCount() > 0){
				convertMenuModel (menuItem, model,
							includeFolder ? (DefaultSubMenu) newItem : null,
							pageDomain, menuPosition, includeFolder, includeSeparator);
			}
		}
		
//		if (makeIds == true){
//			model.generateUniqueIds();
//		}
		
		return model;
	}
	
	/**
	 * 判断是否是连接菜单
	 * @param item
	 * @return
	 */
	public static boolean isSeparatorItem(SiteMenuItem item){
		
		if (item.getType() != MenuItemType.MenuLink)
			return false;
		
		String name = item.getName().trim();
		String code = item.getTitle().trim();
		String url = item.getUrl().trim();
		if (StringKit.equals(name, "separator") && StringKit.equals(name, code) && StringKit.equals(url, "#")){
			return true;
		}
		return false;
	}
	
	/**
	 * 给url添加上参数
	 * @param url
	 * @param params
	 * @return
	 */
	private static String urlAddParams (String url, String params){
		if (url.indexOf("?") >= 0)
			return url + "&" + JsfBeanUtils.evalExprNE(params);
		else
			return url + "?" + JsfBeanUtils.evalExprNE(params);
	}
}
