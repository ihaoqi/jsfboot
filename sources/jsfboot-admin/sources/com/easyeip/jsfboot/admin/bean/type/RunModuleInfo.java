package com.easyeip.jsfboot.admin.bean.type;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.bean.impl.AdminBeanUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.JsfbootModule;

public class RunModuleInfo {
	
	private JsfbootModule pack;
	private TreeNode resTree;
	
	public RunModuleInfo(JsfbootModule pack){
		this.pack = pack;
	}
	
	/**
	 * 取得模块包对象
	 * @return
	 */
	public JsfbootModule getRow(){
		return pack;
	}
	
	/**
	 * 取得资源树
	 * @return
	 */
	public TreeNode getResourceTree(){
		
		if (resTree == null){
			resTree = new DefaultTreeNode();
			
			// 添加前台菜单
			if (pack.getPageResource().getSiteMenu() != null){
				TreeNode root = new DefaultTreeNode(new MenuTreeInfo("前台功能页面"));
				root.setExpanded(true);
				AdminBeanUtils.menuPage2TreeNode(pack, root, pack.getPageResource().getSiteMenu().getMenuList());
				resTree.getChildren().add(root);
			}
			// 添加后台菜单
			if (pack.getPageResource().getAdminMenu() != null){
				TreeNode root = new DefaultTreeNode(new MenuTreeInfo("后台管理页面"));
				root.setExpanded(true);
				AdminBeanUtils.menuPage2TreeNode(pack, root, pack.getPageResource().getAdminMenu().getMenuList());
				resTree.getChildren().add(root);
			}
		}
		
		return resTree;
	}
	
	/**
	 * 取得模块配置页面
	 * @return
	 */
	public String getConfpage(){
		String page = pack.getPageResource().getConfPage();
		if (StringKit.isEmpty(page))
			return null;
		return page;
	}

	public boolean isInJarFile(){
		return pack.getModuleFile().isInJarFile();
	}
	
	public boolean isHaveMenuBar(){
		if (pack.getPageResource() == null)
			return false;
		return pack.getPageResource().getAdminMenu() != null || pack.getPageResource().getSiteMenu() != null;
	}
	
	public boolean isHaveService(){
		return pack.getServiceList().size() > 0;
	}
	
	public boolean isHaveUserAdaptive(){
		return pack.getAccountRealm() != null;
	}
	
	public boolean isHaveTheme(){
		return pack.getModuleTheme() != null;
	}
}
