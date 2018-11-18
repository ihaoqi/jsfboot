package com.easyeip.jsfboot.admin.bean.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.admin.bean.AdminMenubarBean;
import com.easyeip.jsfboot.admin.bean.form.AddMenuBarForm;
import com.easyeip.jsfboot.admin.bean.form.EditMenuBarForm;
import com.easyeip.jsfboot.admin.bean.form.MenuFolderForm;
import com.easyeip.jsfboot.admin.bean.form.MenuPositionForm;
import com.easyeip.jsfboot.admin.bean.type.MenuTreeInfo;
import com.easyeip.jsfboot.admin.datasource.bean.CustomLinkForm;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.UserMenuProvider;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.MenuDataStorage;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;

public class AdminMenubarBeanImpl implements AdminMenubarBean {

	private SiteMenuManager menuManager;
	
	private MenuDataStorage siteMenuData;	// 所有菜单条数据
	private TreeNode[] selectedModuleMenus;	// 模块菜单树中的选中结点
	private DefaultTreeNode moduleUserMenus;// 所有模块的用户菜单
	
	private EditMenuBarForm editMenubar;	// 当前选择的菜单条

	private AddMenuBarForm addMenuBarForm;	// 要新建的菜单名称
	
	private MenuFolderForm menuFolderForm;
	private CustomLinkForm customLinkForm;
	
	private MenuPositionForm menuPositionForm;
	
	public AdminMenubarBeanImpl(){
		// 克隆菜单数据，确保编辑的与在用的分开
		menuManager = JsfbootContext.getDriver().getSurfaceService().getMenuManager();
		siteMenuData = menuManager.cloneSiteMenuData();
		editMenubar = new EditMenuBarFormImpl();
	}
	
	@Override
	public List<SiteMenuBar> getMenubarList() {

		return siteMenuData.allSiteMenubar();
	}

	@Override
	public String getSelMenubarName() {
		
		// 看url中是否有菜单名称参数
		if (editMenubar.getRaw() == null){
			String menubarParam = FacesUtils.getFacesRequestParam("menubar");
			
			if (!StringKit.isEmpty(menubarParam)){
				
				try {
					menubarParam = URLDecoder.decode(menubarParam, "utf-8");
				} catch (UnsupportedEncodingException e) {

				}
				
				SiteMenuBar bar = siteMenuData.getSiteMenubar(menubarParam, false);
				if (bar != null){
					editMenubar.update(bar);
				}
			}
		}
		
		// 默认选中第一个
		if (editMenubar.getRaw() == null && siteMenuData.allSiteMenubar().size() > 0){
			editMenubar.update(siteMenuData.allSiteMenubar().get(0));
		}
		
		if (editMenubar.getRaw() == null)
			return "";
		return editMenubar.getRaw().getName();
	}
	
	@Override
	public void setSelMenubarName(String menubar){
		// 先还原菜单
		siteMenuData = menuManager.cloneSiteMenuData();
		// 更新选择项
		editMenubar.update(siteMenuData.getSiteMenubar(menubar, false));
	}
	
	@Override
	public EditMenuBarForm getEditMenubar() {
		return editMenubar;
	}
	
	@Override
	public void doChangeEditMenubar(ActionEvent event) {
		// 清除组件错误状态
		FacesUtils.clearInputInvalidState();
	}

	@Override
	public void doSaveEditMenubar(ActionEvent event) {
		// 更新标题
		editMenubar.getRaw().setTitle(editMenubar.getTitle());
		
		// 保存数据
		menuManager.applySiteMenuData(siteMenuData);
		
		setSelMenubarName (editMenubar.getName());
		
		FacesUtils.addMessageInfo("保存菜单", editMenubar.getTitle() + " 保存成功。");
	}
	
	@Override
	public void doDeleteMenubar(ActionEvent event) {
		SiteMenuBar curBar = editMenubar.getRaw();
		if (curBar == null || curBar.getHasMain())
			return;
		
		siteMenuData.removeSiteMenubar(curBar.getName());
		//menubarNames = null;
		menuManager.applySiteMenuData(siteMenuData);
		
		// 选中第一个菜单
		if (siteMenuData.allSiteMenubar().size() > 0){
			setSelMenubarName(siteMenuData.allSiteMenubar().get(0).getName());
		}else{
			setSelMenubarName(null);
		}

		FacesUtils.addMessageInfo("删除菜单", "成功删除 " + curBar.getTitle());
	}

	@Override
	public TreeNode getModuleUserMenus() {
		if (moduleUserMenus != null)
			return moduleUserMenus;
		
		moduleUserMenus = new DefaultTreeNode ();
		
		// 加载模块用户菜单
		for (JsfbootModule pack : JsfbootContext.getDriver().getModuleManager().getAllModule()){
			// 取用户菜单
			List<MenuPage> menuList = null;
			
			UserMenuProvider userMenuPrv = pack.getPageResource().getSiteMenu();
			if (userMenuPrv == null || userMenuPrv.getMenuList().size() == 0)
				continue;
			menuList = userMenuPrv.getMenuList();

			// 添加到树
			TreeNode root = new DefaultTreeNode(new MenuTreeInfo(pack, null));
			AdminBeanUtils.menuPage2TreeNode(pack, root, menuList);
			moduleUserMenus.getChildren().add(root);
		}

		return moduleUserMenus;
	}
	
	@Override
	public TreeNode[] getSelectedModuleMenus() {
		return selectedModuleMenus;
	}

	@Override
	public void setSelectedModuleMenus(TreeNode[] selectedNodes) {
		selectedModuleMenus = selectedNodes;
	}

	/**
	 * 添加新的菜单
	 * @param event
	 */
	@Override
	public String doAddNewMenubar(){
		
		String newname = this.getAddMenubarForm().getName().trim();
		String newtitle = this.getAddMenubarForm().getTitle().trim();
		
		if (!RegistryPath.isValidName(newname)){
			FacesUtils.addMessageError("添加菜单", newname + " 不符合名称规范。");
			return null;
		}
		
		if (StringKit.isEmpty(newtitle)){
			FacesUtils.addMessageError("添加菜单", "菜单标题不能为空。");
			return null;
		}
			
		if (siteMenuData.getSiteMenubar(newname, false) != null){
			FacesUtils.addMessageError("添加菜单", newname + " 已存在。");
			return null;
		}
		
		// 添加菜单
		siteMenuData.addSiteMenubar(newname).setTitle(newtitle);
		this.addMenuBarForm = null;
		menuManager.applySiteMenuData(siteMenuData);
		
		// 跳回菜单管理页面，并把新菜单名称传过去
		String codeName = newname;
		try {
			codeName = URLEncoder.encode(newname, "utf-8");
		} catch (UnsupportedEncodingException e) {

		}
		return "/jsfboot-admin/nav-menus?faces-redirect=true&menubar=" + codeName;
	}

	/**
	 * 把选中的模块菜单添加到当前菜单栏中
	 */
	@Override
	public void addModuleMenu2MenuBar(ActionEvent event) {
		// 判断是否可操作
		if (getEditMenubar() == null || selectedModuleMenus == null || selectedModuleMenus.length == 0)
			return;
		
		// selectedModuleMenus中的结点是按选择顺序来的，并且不会存在父子结点同时存在的情况
		SiteMenuItem newItem = null;
		for (TreeNode node : cleanSelectedTreeNode(selectedModuleMenus)){
			MenuTreeInfo menuInfo = (MenuTreeInfo) node.getData();

			if (menuInfo.getSrcMenu() == null){
				// 添加模块所有菜单
				int index = 0;
				for (TreeNode child : node.getChildren()){
					menuInfo = (MenuTreeInfo) child.getData();
					newItem = editMenubar.addMenu2CurRow (menuInfo, index);
					index ++;
				}
			}else{
				// 添加模块内单个菜单
				newItem = editMenubar.addMenu2CurRow (menuInfo, 0);
			}
		}
		
		editMenubar.update(newItem);
		selectedModuleMenus = null;
	}
	
	/**
	 * 清理重复的结点
	 * @param srcNodes
	 * @return
	 */
	private List<TreeNode> cleanSelectedTreeNode (TreeNode[] srcNodes){
		
		List<TreeNode> result = new ArrayList<TreeNode> ();
		for (TreeNode node :srcNodes){
			result.add(node);
			node.setSelected(false);
		}
		
		// 如果一个结点的父结点也在里面，则把本结点清除
		for (TreeNode node :srcNodes){
			
			TreeNode parent = node.getParent();
			while (parent != null){
				
				boolean findParent = false;
				for (TreeNode find : srcNodes){
					if (find == parent){
						findParent = true;
						break;
					}
				}
				
				if (findParent){
					result.remove(node);
					break;
				}
				
				parent = parent.getParent();
			}
			
		}
		
		return result;
	}

	@Override
	public void addEmptyMenuFolder(ActionEvent event) {
		MenuFolderForm form = getMenuFolderForm();
		if (!RegistryPath.isValidName(form.getPermName())){
			FacesUtils.addMessageError("添加目录", form.getPermName() + " 不符合名称规范。");
			return;
		}
		
		editMenubar.update(editMenubar.addFolder2CurRow(form));
		menuFolderForm = null;
	}

	@Override
	public void addUserCustomLink(ActionEvent event) {
		CustomLinkForm form = getCustomLinkForm();
		if (!RegistryPath.isValidName(form.getPermName())){
			FacesUtils.addMessageError("添加目录", "权限" + form.getPermName() + " 不符合名称规范。");
			return;
		}
		
		editMenubar.update(editMenubar.addLink2CurRow(form));
		customLinkForm = null;
	}

	@Override
	public MenuFolderForm getMenuFolderForm() {
		if (menuFolderForm == null){
			menuFolderForm = new MenuFolderForm();
		}
		return menuFolderForm;
	}

	@Override
	public CustomLinkForm getCustomLinkForm() {
		if (customLinkForm == null){
			customLinkForm = new CustomLinkForm();
		}
		return customLinkForm;
	}

	@Override
	public AddMenuBarForm getAddMenubarForm() {
		if (addMenuBarForm == null){
			addMenuBarForm = new AddMenuBarForm();
		}
		return addMenuBarForm;
	}

	@Override
	public MenuPositionForm getMenuPositionForm() {
		if (menuPositionForm == null){
			menuPositionForm = new MenuPositionForm(siteMenuData);
		}
		return menuPositionForm;
	}

	@Override
	public void doBeginMenuPosition(ActionEvent event) {
		menuPositionForm = null;
	}

	@Override
	public String doSaveMenuPosition() {
		if (menuPositionForm != null){
			menuPositionForm.update();
			return "/jsfboot-admin/nav-menus";
		}
		
		return null;
	}
}
