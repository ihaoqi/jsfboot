package com.easyeip.jsfboot.web;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.util.Base64;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.impl.NavigatorPathImpl;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.MenuModelUtils;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;

/**
 * 前台后页面导航器 前台导航 /前台菜单名称，无名和默认为第一个菜单 /admin/后台菜单名称，无名和默认为第一个菜单
 * 
 * @author ihaoqi
 *
 */
public class JsfbootPageNavigator extends GenericServlet {
	public static final String JpnServlet = "/jpn";
	public static final String PagePrefix = "_D:";

	private static final long serialVersionUID = 1L;
	private static Logger logger = Logger.getLogger(JsfbootPageNavigator.class.getName());
	private static final String JsfbootNavigatorParam = "JsfbootNavigatorPath";

	private static String SITE_PREFIX;
	private static String ADMIN_PREFIX;

	private static String jsf_suffix = ".xhtml"; // 页面后缀，默认为.xhtml

	@Override
	public void init() throws ServletException {
		// 初使化JSF页面后缀
		String suffix = this.getServletContext().getInitParameter("javax.faces.DEFAULT_SUFFIX");
		if (!StringKit.isEmpty(suffix)) {
			jsf_suffix = suffix;
		}

		SITE_PREFIX = PageDomainType.Site.name().toLowerCase();
		ADMIN_PREFIX = PageDomainType.Admin.name().toLowerCase();
	}

	@Override
	public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) res;

		// 取得导航参数（在权限过滤器中可能会先创建），没有就创建
		NavigatorPath navPage = getNavigatorPath(request);
		if (navPage == null){
			navPage = createNavigatorPath(request);
		}
		
		if (StringKit.isEmpty(navPage.getForwardPath())) {
			logger.log(Level.WARNING, navPage.getRequestPath() + "页面不存在。");
			response.sendError(404, navPage.getRequestPath() + "页面不存在。");
			return;
		}

		// 如果是外部url则直接跳转，如果是内部的则渲染
		String pagePath = navPage.getForwardPath();
		if (pagePath.indexOf("://") > 0) {
			response.sendRedirect(pagePath);
			return;
		}

		// 设置当前页面的动作权限
		request.getSession().setAttribute(ActionAuthorizeAttribate.ATTR_NAME,
											new ActionAuthorizeAttribate(navPage));
		
		request.getRequestDispatcher(navPage.getForwardPath()).forward(req, res);
	}

	/**
	 * 取得请求中的导航页面
	 * 
	 * @param req
	 * @return
	 */
	public static NavigatorPath getNavigatorPath(ServletRequest req) {
		Object obj = req.getAttribute(JsfbootNavigatorParam);
		if (obj instanceof NavigatorPath)
			return (NavigatorPath) obj;

		return null;
	}

	/**
	 * 创建一相新的导航路径
	 * 
	 * @param request
	 * @param onlyCreate
	 *            只创建，不做任何其他操作
	 * @return
	 */
	public static NavigatorPath createNavigatorPath(HttpServletRequest request) {
		// 取出viewid和参数
		String viewId = request.getRequestURI();
		String qeuryStr = request.getQueryString();
		String contextPath = request.getContextPath();
		String servletPath = request.getServletPath();

		if (!StringKit.isEmpty(contextPath) && viewId.startsWith(contextPath)) {
			viewId = viewId.substring(contextPath.length());
		}
		if (!StringKit.isEmpty(servletPath) && viewId.startsWith(servletPath)) {
			viewId = viewId.substring(servletPath.length());
		}

		// 判断句viewId是否有两级，第一级为前台或后台，第二级为菜单名称，如果没有第一级，默认为前台
		// 如 /site/page1和/page1等同，/admin/page2为后台页面page2
		NavigatorPath navPage = parseNavigatorPath(request, viewId, qeuryStr);

		// 判断是否是直接访问的url，如果是则不查询菜单
		String pagePath = null;
		if (StringKit.notEmpty(navPage.getMenuPage()) && navPage.getMenuPage().startsWith(PagePrefix)) {
			pagePath = navPage.getMenuPage().substring(PagePrefix.length());
			pagePath = new String(Base64.decode(pagePath));
		} else {
			// 取得对应主题的菜单
			SiteMenuBar menubar = null;
			if (navPage.getPageDomain() == PageDomainType.Site) {
				menubar = getAccountMenubar(PageDomainType.Site, navPage.getMenuPosition(), request);
			} else if (navPage.getPageDomain() == PageDomainType.Admin) {
				menubar = getAccountMenubar(PageDomainType.Admin, navPage.getMenuPosition(), request);
			}
			// 把菜单项转换成页面路径
			if (menubar != null) {
				pagePath = convertMenuPage(request, menubar, navPage);
			}
		}

		if (pagePath != null) {
			navPage.setForwardPath(pagePath);
		}
		
		// 保存进请求
		request.setAttribute(JsfbootNavigatorParam, navPage);

		return navPage;
	}

	/**
	 * 解析当前导航的菜单地址，没到的返回null
	 * 
	 * @param request
	 * @param response
	 * @param menubar
	 * @param navPath
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	private static String convertMenuPage(HttpServletRequest request, SiteMenuBar menubar, NavigatorPath navPath) {

		boolean isAdmin = navPath.getPageDomain() == PageDomainType.Admin;

		// 查找菜单
		SiteMenuItem navMenu = null;
		boolean isFirstPage = StringKit.isEmpty(navPath.getMenuPage());
		if (isFirstPage)
			navMenu = MenuModelUtils.findFirstNavMenu(menubar, false);
		else
			navMenu = MenuModelUtils.findMenuByPermName(menubar, navPath.getMenuPage(), false);

		if (navMenu == null) {
			if (isFirstPage) {
				String dispPage = isAdmin ? "/admin-access-denied.xhtml" : "/site-access-denied.xhtml";
				return "/jsfboot-bundle" + dispPage;
			}
			return null;
		}

		// 更新菜单属性
		navPath.setMenuItem(navMenu);
		if (StringKit.isEmpty(navPath.getMenuPage())) {
			navPath.setMenuPage(navMenu.getPermName());
		}

		// 把菜单id取出来转换成路径
		String pagePath;
		if (navMenu.getType() == MenuItemType.MenuLink)
			pagePath = navMenu.getUrl();
		else
			pagePath = getToViewId(navMenu, request.getServletPath(), navPath.getPageDomain());

		// 加上查询路径
		if (pagePath != null && !StringKit.isEmpty(navPath.getQueryString())) {
			pagePath += "?" + navPath.getQueryString();
		}

		return pagePath;
	}

	public static String getToViewId(SiteMenuItem menu, String requestPath, PageDomainType domain) {

		if (menu.getType() == MenuItemType.MenuLink) {
			return menu.getUrl();
		}
		
		// 查找页面的路径
		String outcome = null;
		MenuPage srcPage = MenuPageUtils.findMenuPageBySource(menu.getPageSource(), domain, false);
		if (srcPage != null)
			outcome = srcPage.getOutcome();
		else
			outcome = menu.getOutcome();
		
		return getToViewId(outcome, requestPath);
	}
	
	/**
	 * 
	 * @param menuPage 页面定义
	 * @param requestPath
	 * @return
	 */
	public static String getToViewId(String outcome, String requestPath){
		// 如果已存在后缀则直接返回
		if (StringKit.isEmpty(outcome) || outcome.lastIndexOf(".") > 0)
			return outcome;

		// 给outcome加上后缀
		return outcome + parseSuffix(requestPath, jsf_suffix);
	}

	/**
	 * 取得后缀名
	 * 
	 * @param servletPath
	 * @param defSuffix
	 * @return
	 */
	private static String parseSuffix(String servletPath, String defSuffix) {
		int dotIndex = servletPath.lastIndexOf(".");
		if (dotIndex >= 0) {
			return servletPath.substring(dotIndex);
		}
		return defSuffix;
	}

	/**
	 * 分离菜单位置、菜单条、菜单名称
	 * 
	 * @param viewId
	 *            标准格式：/admin/top/console 简写1：/top/console，默认为/site/top/console
	 *            简写2：/console，默认为/site/main/console
	 *            简写3：/admin/console，默认为/admin/main/console
	 * @return
	 */
	private static NavigatorPath parseNavigatorPath(HttpServletRequest request, String viewId, String queryStr) {
		String site = SITE_PREFIX;
		String menubar = SiteMenuManager.MAIN_MENUBAR;
		String page = "";

		// 去除逗号后面的，防止出现下面的url
		// http://localhost:8080/easyeip-tsms/jpn/admin/nav-menus;jsessionid=F0EDD77D9CE4869FDD815764AA3E1954
		if (viewId.indexOf(";") >= 0) {
			viewId = viewId.substring(0, viewId.indexOf(";"));
		}

		int index = 0;
		String[] paths = viewId.split("/");
		for (String path : paths) {
			if (StringKit.isEmpty(path))
				continue;
			if (index == 0) {
				site = path;
				index++;
			} else if (index == 1) {
				menubar = path;
				index++;
			} else if (index == 2) {
				page = path;
				index++;
				break;
			}
		}

		// 设置缺省值
		if (index == 1) {
			if (!site.equals(SITE_PREFIX) && !site.equals(ADMIN_PREFIX)) {
				page = site;
				site = SITE_PREFIX;
				menubar = SiteMenuManager.MAIN_MENUBAR;
			}
		} else if (index == 2) {
			if (site.equals(SITE_PREFIX) || site.equals(ADMIN_PREFIX)) {
				page = menubar;
				menubar = SiteMenuManager.MAIN_MENUBAR;
			} else {
				page = menubar;
				menubar = site;
				site = SITE_PREFIX;
			}
		}

		// 获取主题类型
		PageDomainType themeType = null;
		if (StringKit.equals(site, SITE_PREFIX))
			themeType = PageDomainType.Site;
		else if (StringKit.equals(site, ADMIN_PREFIX))
			themeType = PageDomainType.Admin;

		NavigatorPathImpl nav = new NavigatorPathImpl(viewId, themeType, menubar, page, queryStr);
		nav.setJpnServlet(request.getServletPath());
		return nav;
	}

	/**
	 * 取得主题对应的主菜单条
	 * 
	 * @param pageDomain
	 * @param menuPosition
	 *            主题菜单的占位名称
	 * @return
	 */
	private static SiteMenuBar getAccountMenubar(PageDomainType pageDomain,
							String menuPosition, HttpServletRequest request) {

		// 按权限过滤菜单
		return JsfbootContext.getDriver().getSecutiryService().
						filterAccountMenuBar(pageDomain, menuPosition, request);
	}
}
