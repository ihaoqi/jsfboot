package com.easyeip.jsfboot.admin.help.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.AdminHelpViewService;
import com.easyeip.jsfboot.admin.bean.AdminHelpViewBean;
import com.easyeip.jsfboot.admin.help.DashboardGadget;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.admin.help.HelpCatalogView;
import com.easyeip.jsfboot.admin.help.HelpViewPage;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;

public class AdminHelpViewBeanImpl extends JsfbootBean implements AdminHelpViewBean {
	
	@UseJsfbootService(AdminHelpViewService.class)
	AdminHelpViewService service;
	
	private DefaultTreeNode adminTreeRoot;
	private DefaultTreeNode siteTreeRoot;
	private Map<String,HelpViewPage> catalogCache;
	
	public AdminHelpViewBeanImpl(){
		catalogCache = new HashMap<String,HelpViewPage>();
	}
	
	/**
	 * 生成cachekey
	 * @param domain
	 * @param pagePath
	 * @return
	 */
	private String getCacheKey(PageDomainType domain, String pagePath){
		if (domain == null)
			return pagePath;
		return domain.name() + "/" + pagePath;
	}

	@Override
	public String getHelpOutcome() {
		AdminHelpViewServiceImpl serviceImpl = (AdminHelpViewServiceImpl) service;
		return OutcomeUtils.fullOutcome(serviceImpl.getModule(), "/help");
	}

	@Override
	public TreeNode getCatalogTree() {
		
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(FacesUtils.getServletRequest());
		
		if (navPath.getPageDomain() == PageDomainType.Admin){
			if (adminTreeRoot == null){
				adminTreeRoot = new DefaultTreeNode(service.getRootCatalog(PageDomainType.Admin));
				loadCatalogModel (adminTreeRoot, service.getRootCatalog(PageDomainType.Admin));
			}
			return adminTreeRoot;
		}else{
			if (siteTreeRoot == null){
				siteTreeRoot = new DefaultTreeNode(service.getRootCatalog(PageDomainType.Site));
				loadCatalogModel (siteTreeRoot, service.getRootCatalog(PageDomainType.Site));
			}
			return adminTreeRoot;
		}
	}

	@Override
	public HelpViewPage getViewPage() {
		
		// 取得要浏览的目录，如果没有就显示第一个
		HelpCatalogView catalog = null;
		String pagePath = FacesUtils.getFacesRequestParam("page");
		if (pagePath == null){
			pagePath = "";
		}
		
		// 从先cache中读取
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(FacesUtils.getServletRequest());
		String cacheKey = getCacheKey (navPath.getPageDomain(), pagePath);
		HelpViewPage cache = catalogCache.get(cacheKey);
		if (cache != null){
			return cache;
		}
		
		if (StringKit.isEmpty(pagePath)){
			catalog = service.getRootCatalog(navPath.getPageDomain());
		}else{
			catalog = findPageCatalogByPath (navPath.getPageDomain(), pagePath);
		}
		
		if (catalog == null)
			return null;
		
		// 先向上查找可用页面，没找到就向下查找
		HelpCatalogView view = findUpValidPage (catalog);
		if (view == null){
			view = findDownValidPage (catalog);
		}
		
		if (view == null)
			return null;
		
		cache = new HelpViewPage(getHelpOutcome(), view);
		catalogCache.put(cacheKey, cache);
		return cache;
	}
	
	/**
	 * 加载帮助目录到树模型
	 * @param root
	 * @param catalog
	 */
	private void loadCatalogModel(DefaultTreeNode root, HelpCatalogView catalog){
		for (HelpCatalog child : catalog.getChilds()){
			DefaultTreeNode folder = new DefaultTreeNode(child);
			root.getChildren().add(folder);
			loadCatalogModel (folder, child);
			
			if (root.getParent() == null){
				folder.setExpanded(true);
			}
		}
	}

	/**
	 * 按路径查找目录
	 * @param pagePath
	 * @return
	 */
	private HelpCatalogView findPageCatalogByPath(PageDomainType domain, String pagePath) {
		
		HelpCatalogView catalog = service.getRootCatalog(domain);
		for (String pathName : pagePath.split("/")){
			if (StringKit.isEmpty(pathName))
				continue;
			
			// 查找目录
			boolean findOk = false;
			for (HelpCatalog cat : catalog.getChilds()){
				if (StringKit.equals(cat.getName(), pathName)){
					catalog = cat;
					findOk = true;
					break;
				}
			}
			
			if (findOk == false)
				break;
		}

		return catalog;
	}
	
	/**
	 * 向上查找有效果页面
	 * @param root
	 * @return
	 */
	private HelpCatalogView findUpValidPage(HelpCatalogView root){
		
		HelpCatalogView parent = root;
		if (parent != null){
			if (parent.getPage() != null)
				return parent;
			parent = parent.getParent();
		}
		
		return null;
	}
	
	/**
	 * 向下查找第一个可见页面
	 * @param root
	 * @return
	 */
	private HelpCatalogView findDownValidPage(HelpCatalogView root) {
		
		if (root.getPage() != null)
			return root;
		
		for (HelpCatalog cat : root.getChilds()){
			
			HelpCatalogView find = findDownValidPage(cat);
			if (find != null)
				return find;
			
		}
		
		return null;
	}

	@Override
	public List<DashboardGadget> getDashboardGadgets() {
		return service.getDashboardGadgets();
	}
}
