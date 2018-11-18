package com.easyeip.jsfboot.admin.help;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.DefaultMenuModel;
import org.primefaces.model.menu.MenuModel;

/**
 * 当前要浏览的页面
 * @author liao
 *
 */
public class HelpViewPage {
	
	private String helpOutcome;
	private HelpCatalogView catalog;
	
	DefaultMenuModel navModel;
	private String viewUrl;
	
	public HelpViewPage(String helpOutcome, HelpCatalogView catalog){
		this.helpOutcome = helpOutcome;
		this.catalog = catalog;
	}
	
	/**
	 * 取得关联对象
	 * @return
	 */
	public HelpCatalogView getRaw(){
		return catalog;
	}

	/**
	 * 取得页面类型
	 * @return
	 */
	public String getType(){
		return catalog.getPage().getType().name();
	}
	
	/**
	 * 取得页面导航
	 * @return
	 */
	public MenuModel getBreadCrumb(){
		
		if (navModel != null)
			return navModel;
		
		navModel = new DefaultMenuModel();
		
		List<DefaultMenuItem> menuList = new ArrayList<DefaultMenuItem>();
		HelpCatalogView cat = catalog;
		while (cat != null){
			if (cat.getParent() == null){
				DefaultMenuItem menuItem = new DefaultMenuItem();
				menuItem.setUrl("#");
				menuList.add(0, menuItem);
			}else{
				DefaultMenuItem menuItem = new DefaultMenuItem(cat.getTitle());
				menuItem.setOutcome(helpOutcome);
				menuItem.setParam("page", cat.getFullPath());
				menuList.add(0, menuItem);
			}

			cat = cat.getParent();
		}
		
		for (DefaultMenuItem mi : menuList){
			navModel.addElement(mi);
		}
		
		return navModel;
	}
	
	/**
	 * 取得要浏览的URL
	 * @return
	 */
	public String getViewUrl(){

		if (viewUrl != null)
			return viewUrl;
		
		viewUrl = getViewUrl (catalog);

		return viewUrl;
	}
	
	private String getViewUrl(HelpCatalogView cat){
		
		if (cat.getPage() == null)
			return null;

		// 如果页面是以/开始就直接返回，否则要加载上级的页面地址
		String srcUrl = cat.getPage().getUrl();
		if (srcUrl.startsWith("/")){
			return srcUrl;
		}
		
		// 加载上级以/开头的页面地址
		HelpCatalogView parent = cat.getParent();
		String parentPath = "";
		while (parent != null){
			if (parent.getPage() != null){
				String pp = parent.getPage().getUrl();
				if (pp.startsWith("/")){
					parentPath = getParentPath(pp);
					break;
				}
			}
			parent = parent.getParent();
		}
		
		return parentPath + "/" + srcUrl;
	}
	
	/**
	 * 取得页面路径的父级
	 * @param pagePath
	 * @return
	 */
	private String getParentPath (String pagePath){
		int index = pagePath.lastIndexOf("/");
		if (index >= 0){
			return pagePath.substring(0, index);
		}
		
		return null;
	}
}
