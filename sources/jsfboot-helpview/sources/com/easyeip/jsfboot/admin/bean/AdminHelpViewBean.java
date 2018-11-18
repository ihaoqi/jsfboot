package com.easyeip.jsfboot.admin.bean;

import java.util.List;

import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.help.DashboardGadget;
import com.easyeip.jsfboot.admin.help.HelpViewPage;

public interface AdminHelpViewBean {
	
	/**
	 * 取得帮助页面，这页面支持两个参数：
	 * @return
	 */
	String getHelpOutcome();

	/**
	 * 取得帮助目录
	 * @return
	 */
	TreeNode getCatalogTree();
	
	/**
	 * 取得要浏览的页面
	 * @return
	 */
	HelpViewPage getViewPage();
	
	/**
	 * 取得公告板上的小工具列表
	 * @return
	 */
	List<DashboardGadget> getDashboardGadgets();
}
