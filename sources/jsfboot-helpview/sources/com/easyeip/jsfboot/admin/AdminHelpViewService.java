package com.easyeip.jsfboot.admin;

import java.util.List;

import com.easyeip.jsfboot.admin.help.DashboardGadget;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.admin.help.HelpCatalogView;
import com.easyeip.jsfboot.core.surface.PageDomainType;

public interface AdminHelpViewService {
	
	/**
	 * 取得帮助文档构造程序
	 * @return
	 */
	HelpBuilder getHelpBuilder();
	
	/**
	 * 取得根目录
	 * @return
	 */
	HelpCatalogView getRootCatalog(PageDomainType domain);
	
	/**
	 * 添加帮助文档内容
	 * @param catalog
	 * @return
	 */
	HelpCatalog addHelpDocument(PageDomainType domain, HelpCatalog catalog);
	
	/**
	 * 添加公告板小工具
	 * @param title 标题
	 * @param icon 图标
	 * @param url 要跳转的url
	 * @return
	 */
	DashboardGadget addDashboardGadget(String title, String icon);
	
	/**
	 * 取得小部件列表
	 * @return
	 */
	List<DashboardGadget> getDashboardGadgets();
}
