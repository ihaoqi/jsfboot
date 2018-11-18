package com.easyeip.jsfboot.user.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.user.bean.type.MenuPositionDetails;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountRole;
import com.easyeip.jsfboot.utils.KeyValuePair;

/**
 * 用户角色管理模块
 * @author ihaoqi
 *
 */
public interface RegistryUserRoleBean {
	
	/**
	 * 获取所有角色的列表
	 * @return
	 */
	List<RoleDetails> getRoleList();
	
	/**
	 * 添加角色
	 * @param form
	 */
	String endAddRole ();
	
	/**
	 * 删除角色
	 * @param event
	 */
	void delAccountRole (ActionEvent event);
	
	/**
	 * 保存角色
	 * @param event
	 */
	String endEditRole ();
	
	/**
	 * 取得添加角色表单
	 * @return
	 */
	RegistryAccountRole getAddRoleForm ();

	/**
	 * 获取前后台主题名称
	 * @return
	 */
	KeyValuePair getThemeNames();

	/**
	 * 取得关联菜单
	 * @return
	 */
	DualListModel<MenuPositionDetails> getLinkMenus();
	
	/**
	 * 保存要关联的菜单
	 */
	String saveLinkMenus();
	
	/**
	 * 取得角色权限配置树
	 * @return
	 */
	TreeNode getRoleMenuTree();
	
	/**
	 * 保存角色权限配置树
	 * @param event
	 */
	void saveRoleMenuTree(ActionEvent event);
}
