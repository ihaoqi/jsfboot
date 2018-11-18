package com.easyeip.jsfboot.admin.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.bean.form.AddMenuBarForm;
import com.easyeip.jsfboot.admin.bean.form.EditMenuBarForm;
import com.easyeip.jsfboot.admin.bean.form.MenuFolderForm;
import com.easyeip.jsfboot.admin.bean.form.MenuPositionForm;
import com.easyeip.jsfboot.admin.datasource.bean.CustomLinkForm;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;

public interface AdminMenubarBean {
	
	/**
	 * 取得可配置导航菜单列表
	 * @return
	 */
	List<SiteMenuBar> getMenubarList();
	
	/**
	 * 取得当前正在编辑的菜单条
	 * @return
	 */
	String getSelMenubarName();
	void setSelMenubarName(String menubar);
	
	/**
	 * 取得添加菜单条表单
	 * @return
	 */
	AddMenuBarForm getAddMenubarForm();
	
	/**
	 * 取得当前选择的菜单条对象
	 * @return
	 */
	EditMenuBarForm getEditMenubar();
	
	/**
	 * 选择新的编辑菜单
	 * @param event
	 */
	void doChangeEditMenubar(ActionEvent event);
	
	/**
	 * 取得所有模块的用户菜单
	 * @return
	 */
	TreeNode getModuleUserMenus();
	
	/**
	 * 模块菜单树中的选中结点列表
	 * @return
	 */
	TreeNode[] getSelectedModuleMenus();
    void setSelectedModuleMenus(TreeNode[] selectedNodes);
    
    /**
     * 添加选中模块菜单到菜单条
     * @param event
     */
    void addModuleMenu2MenuBar(ActionEvent event);
    
    /**
     * 添加空的菜单目录
     * @param event
     */
    void addEmptyMenuFolder(ActionEvent event);
    
    MenuFolderForm getMenuFolderForm();
    
    /**
     * 添加用户自定义链接
     * @param event
     */
    void addUserCustomLink(ActionEvent event);
    
    CustomLinkForm getCustomLinkForm();
	
	/**
	 * 添加新的菜单
	 * @param event
	 */
	String doAddNewMenubar();
	
	/**
	 * 保存当前编辑菜单
	 * @param event
	 */
	void doSaveEditMenubar(ActionEvent event);
	
	/**
	 * 删除当前菜单条
	 * @param event
	 */
	void doDeleteMenubar(ActionEvent event);
	
	/**
	 * 取得菜单位置管理表单
	 * @return
	 */
	MenuPositionForm getMenuPositionForm();
	
	/**
	 * 开始修改菜单位置
	 * @param event
	 */
	void doBeginMenuPosition(ActionEvent event);
	
	/**
	 * 保存修改的菜单
	 * @param event
	 */
	String doSaveMenuPosition();
}
