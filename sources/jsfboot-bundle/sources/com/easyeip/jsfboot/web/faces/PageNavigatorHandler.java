package com.easyeip.jsfboot.web.faces;

import javax.faces.application.ViewHandler;
import javax.faces.application.ViewHandlerWrapper;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewDeclarationLanguage;
import javax.servlet.http.HttpServletRequest;

import org.primefaces.util.Base64;

import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuItemFindUtils;
import com.easyeip.jsfboot.core.secutiry.ppm.PageInMenuItem;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.utils.StringKit;

/**
 * 当使用 JsfbootPageNavigator 导航的xhtml页面时，要处理它的form action属性，不然它默认会生成原始页面地址
 * @author ihaoqi
 *
 */
public class PageNavigatorHandler extends ViewHandlerWrapper {

	private ViewHandler	mDelegate;
	
	public PageNavigatorHandler(ViewHandler handler)
	{
		mDelegate = handler;
	}
	
	@Override
	public ViewHandler getWrapped() {
		return mDelegate;
	}
	
	@Override
	public ViewDeclarationLanguage getViewDeclarationLanguage(FacesContext context, String viewId) {
		return new DebugViewDeclarationLanguage (super.getViewDeclarationLanguage(context, viewId));
	}

	@Override
	public String getActionURL(FacesContext context, String viewId)
	{
		// 取得导航参数
		HttpServletRequest req = (HttpServletRequest) context.getExternalContext().getRequest();
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(req);
		
		if (navPath == null){
			return mDelegate.getActionURL(context, viewId);
		}

		// 返回导航地址
		StringBuilder newAction = new StringBuilder();
		String contextPath = req.getContextPath();
        if (contextPath != null && !(contextPath.length() == 1 && contextPath.charAt(0) == '/') )
        {
        	newAction.append(contextPath);
        }
        
        //newAction.append(navPath.getJpnServlet());
        newAction.append(JsfbootPageNavigator.JpnServlet);
        
		// 如果当前viewid与导航的一样，则返回导航viewId。否则返回编码后的新页面地址
		if (navPath.getForwardPath().startsWith(viewId)){
			String reqPath = navPath.getRequestPath();
			if (!StringKit.equals(reqPath, "/")){
				newAction.append(reqPath);
			}
			
			return newAction.toString();// + paramString;
		}
		
		// 找下viewId页面对应的菜单
		SiteMenuManager menuManager = JsfbootContext.getDriver().getSurfaceService().getMenuManager();
		SiteMenuBar menubar = menuManager.getThemeMenubar(
										navPath.getPageDomain(), navPath.getMenuPosition());
		PageInMenuItem pageInMenu = null;
		if (menubar != null){
			pageInMenu = MenuItemFindUtils.findMenuItemByUrl(navPath.getPageDomain(), menubar, viewId);
		}
		
		// 加上主题类型、菜单位置
		if (navPath.getPageDomain() != PageDomainType.Site){
			newAction.append("/" + navPath.getPageDomain().name().toLowerCase());
		}
		if (!StringKit.equals(navPath.getMenuPosition(), SiteMenuManager.MAIN_MENUBAR)){
			newAction.append("/" + navPath.getMenuPosition());
		}
		
		// 如果是菜单则生成菜单的路径（不支持动作），否则生成原始路径
		if (pageInMenu != null && pageInMenu.getPageAction() == null && !pageInMenu.isPublicPage()){
			newAction.append("/" + pageInMenu.getMenuItem().getPermName());
		}else{
			newAction.append("/" + JsfbootPageNavigator.PagePrefix);
			newAction.append(Base64.encodeToString(viewId.getBytes(), false));
		}
        
        return newAction.toString();// + paramString;
	}
}
