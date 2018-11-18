package com.easyeip.jsfboot.user.bean.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.faces.event.ActionEvent;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.beans.JsfbootBeanException;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.core.secutiry.ppm.DefaultPermission;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuPermission;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.MenuModelUtils;
import com.easyeip.jsfboot.core.surface.MenuPageSource;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.user.bean.RegistryUserRoleBean;
import com.easyeip.jsfboot.user.bean.type.MenuPositionDetails;
import com.easyeip.jsfboot.user.bean.type.MenuRoleNodeData;
import com.easyeip.jsfboot.user.bean.type.MenuRoleRelation;
import com.easyeip.jsfboot.user.registry.RegistryUserManager;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountRole;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.KeyValuePair;
import com.easyeip.jsfboot.utils.StringKit;

public class RegistryUserRoleBeanImpl extends JsfbootBean implements RegistryUserRoleBean {
	
	@UseJsfbootService(RegistryUserManager.class)
	private RegistryUserManager userManager;
	
	private RegistryAccountRole addRoleForm;
	
	private DualListModel<MenuPositionDetails> linkMenus;
	private DefaultTreeNode roleMenuTree;
	
	private List<RoleDetails> roleList;

	@Override
	public List<RoleDetails> getRoleList() {
		if (roleList == null){
			roleList = userManager.queryRoleList();
		}
		return roleList;
	}
	
	@Override
	public RegistryAccountRole getAddRoleForm() {
		if (addRoleForm == null){
			// 从url参数中取得要编辑的角色
			String code = FacesUtils.getFacesRequestParam("role");
			if (StringKit.notEmpty(code)){
				addRoleForm = (RegistryAccountRole) userManager.queryRoleOne(code);
				if (addRoleForm == null){
					throw new JsfbootBeanException(code + "角色不存在。");
				}
			}else{
				addRoleForm = new RegistryAccountRole();
			}
		}
		return addRoleForm;
	}

	@Override
	public String endAddRole() {
		try {
			userManager.addRole(addRoleForm);
			roleList = null;
			return "../rolemgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("添加角色", e.getMessage());
			return null;
		}
	}

	@Override
	public String endEditRole() {
		try {
			userManager.updateRole(addRoleForm);
			roleList = null;
			return "../rolemgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("修改分组", e.getMessage());
			return null;
		}
	}

	@Override
	public void delAccountRole(ActionEvent event) {
		RoleDetails role = (RoleDetails) event.getComponent().getAttributes().get("role1");

		try {
			userManager.removeRole(role.getCode());
			roleList = null;
		} catch (Exception e) {
			FacesUtils.addMessageError("删除分组", e.getMessage());
		}
		
	}

	@Override
	public DualListModel<MenuPositionDetails> getLinkMenus() {
		if (linkMenus == null){
			
			List<MenuPositionDetails> siteList = new ArrayList<MenuPositionDetails>();
			List<MenuPositionDetails> adminList = new ArrayList<MenuPositionDetails>();
			SurfaceService ss = JsfbootContext.getDriver().getSurfaceService();
			
			Map<String, MenuPositionDetails> confMenus = userManager.getConfMenus();
			
			// 添加前台菜单
			JsfbootTheme st = ss.getCurrentTheme(PageDomainType.Site);
			for (MenuPosition mp : st.getMenuPosition()){
				SiteMenuBar bar = ss.getMenuManager().getThemeMenubar(PageDomainType.Site, mp.getName());
				if (bar != null){
					MenuPositionDetails mpd = new MenuPositionDetails(PageDomainType.Site, st, mp, bar);
					mpd.setSelected(confMenus.containsKey(mpd.encode()));
					siteList.add(mpd);
				}
			}
			
			// 添加后台菜单
			st = ss.getCurrentTheme(PageDomainType.Admin);
			for (MenuPosition mp : st.getMenuPosition()){
				SiteMenuBar bar = ss.getMenuManager().getThemeMenubar(PageDomainType.Admin, mp.getName());
				if (bar != null){
					MenuPositionDetails mpd = new MenuPositionDetails(PageDomainType.Admin, st, mp, bar);
					mpd.setSelected(confMenus.containsKey(mpd.encode()));
					adminList.add(mpd);
				}
			}
			
			linkMenus = new DualListModel<MenuPositionDetails>(siteList, adminList);
		}
		return linkMenus;
	}

	@Override
	public String saveLinkMenus() {
		
		if (linkMenus == null) return null;
		
		List<MenuPositionDetails> menus = new ArrayList<MenuPositionDetails>();
		
		for (MenuPositionDetails mpd : linkMenus.getSource()){
			if (mpd.isSelected()){
				menus.add(mpd);
			}
		}
		
		for (MenuPositionDetails mpd : linkMenus.getTarget()){
			if (mpd.isSelected()){
				menus.add(mpd);
			}
		}
		
		try {
			userManager.saveConfMenus(menus);
			return "../permmgr?faces-redirect=true";
		} catch (RegistryException e) {
			FacesUtils.addMessageError("选择菜单", e.getMessage());
		}
		
		return null;
	}

	@Override
	public KeyValuePair getThemeNames() {
		String n1 = JsfbootContext.getDriver().getSurfaceService().getCurrentTheme(PageDomainType.Site).getName();
		String n2 = JsfbootContext.getDriver().getSurfaceService().getCurrentTheme(PageDomainType.Admin).getName();
		return new KeyValuePair(n1, n2);
	}

	@Override
	public TreeNode getRoleMenuTree() {
		
		if (roleMenuTree != null)
			return roleMenuTree;
		
		roleMenuTree = new DefaultTreeNode ();
		
		// 获取菜单列表、角色列表
		Map<String, MenuPositionDetails> confMenus = userManager.getConfMenus();
		List<RoleDetails> roleList = userManager.queryRoleList();
		
		for (MenuPositionDetails mpd : confMenus.values()){
			DefaultTreeNode root = new DefaultTreeNode (new MenuRoleNodeData(mpd, null));

			loadMenuRoleToTree (root, mpd, mpd.getMenuBar(), roleList);
			if (root.getChildCount() > 0){
				loadMenuRoleRelation (root, roleList);
				roleMenuTree.getChildren().add(root);
			}
		}

		return roleMenuTree;
	}

	@Override
	public void saveRoleMenuTree(ActionEvent event) {
		try {
			for (RoleDetails role : userManager.queryRoleList()){
				role.getPermissions().clear();
				saveRoleMenuPermConf (roleMenuTree, role);
				userManager.updateRole(role);
			}
			FacesUtils.addMessageError("保存权限", "保存成功。");
		} catch (Exception e) {
			FacesUtils.addMessageError("保存权限", e.getMessage());
		}
	}
	
	/**
	 * 保存一个角色的权限
	 * @param role
	 */
	private void saveRoleMenuPermConf (TreeNode root, RoleDetails role){
		
		MenuRoleNodeData data = (MenuRoleNodeData) root.getData();
		
		// 不保存目录与动作
		if (data != null && data.getMenuItem() != null &&
				data.getMenuItem().getType() != MenuItemType.MenuFolder &&
				data.getMenuAction() == null){
			
			MenuRoleRelation roleRel = data.getRoles().get(role.getCode());
			
			// 不处理动作
			if (roleRel != null && roleRel.isSelected() == true){
				
				// 判断它的动作是否全部被选中
				boolean allActionSelected = false;
				String selActions = null;
				if (data.getMenuItem().getPageSource() != null && root.getChildCount() > 0){
					allActionSelected = checkAllActionSelected(root, role.getCode());
					selActions = listActionSelected(root, role.getCode());
				}
				
				// 保存菜单
				DefaultPermission permission = new DefaultPermission ();
				permission.setGrantAllAction(allActionSelected);
				permission.setMenuName(data.getMenuItem().getPermName());
				permission.setMenuPosition(data.getMenuPosition().getName());
				permission.setPageDomain(data.getPageDomain());

				if (allActionSelected == false && StringKit.notEmpty(selActions)){
					permission.setGrantActions(selActions);
				}
				
				role.addPermission(permission);
				
				// 继续处理向下子结点
				if (StringKit.notEmpty(selActions) || allActionSelected)
					return;
			}
		}
		
		// 处理子结点
		if (root.getChildCount() > 0){
			for (TreeNode child : root.getChildren()){
				saveRoleMenuPermConf (child, role);
			}
		}
	}
	
	/**
	 * 判断所有子动作是否被选中
	 * @param root
	 * @return
	 */
	private boolean checkAllActionSelected(TreeNode root, String roleCode){

		for (TreeNode child : root.getChildren()){
			MenuRoleNodeData data = (MenuRoleNodeData) child.getData();

			MenuRoleRelation rel = data.getRoles().get(roleCode);
			if (data.getMenuAction() == null || rel == null || rel.isSelected() == false){
				return false;
			}
		}
		
		return true;
	}
	
	/**
	 * 获取菜单下选中的动作列表，一个都没选中返回空串
	 * @param root
	 * @param roleCode
	 * @return
	 */
	private String listActionSelected (TreeNode root, String roleCode){
		String actions = "";
		for (TreeNode child : root.getChildren()){
			MenuRoleNodeData data = (MenuRoleNodeData) child.getData();

			MenuRoleRelation rel = data.getRoles().get(roleCode);
			if (data.getMenuAction() != null && rel != null && rel.isSelected()){
				if (actions.length() != 0)
					actions += ",";
				actions += data.getMenuAction().getName();
			}
		}
		
		return actions;
	}

	private void loadMenuRoleToTree (DefaultTreeNode root, MenuPositionDetails mpd,
								SubMenuContainer menus, List<RoleDetails> roles){
		
		for (SiteMenuItem subMenu : menus.getSubMenuList()){
			
			// 跳过分割条
			if (MenuModelUtils.isSeparatorItem(subMenu))
				continue;
			
			// 处理子结点
			DefaultTreeNode node = new DefaultTreeNode (new MenuRoleNodeData(mpd, subMenu));
			if (subMenu.getSubMenuCount() > 0 || subMenu.getType() == MenuItemType.MenuFolder){
				loadMenuRoleToTree (node, mpd, subMenu, roles);
				
				// 如果这个目录下有菜单才加入
				if (node.getChildCount() > 0){
					loadMenuRoleRelation (node, roles);
					root.getChildren().add(node);
				}
				continue;
			}
			
			// 加载权限
			loadMenuRoleRelation (node, roles);
			
			// 加载动作
			MenuPageSource pageSource = subMenu.getPageSource();
			if (pageSource != null){
				MenuPage page = MenuPageUtils.findMenuPageBySource(pageSource, mpd.getDomain(), false);
				if (page != null && page.getPageActions().size() > 0){
					for (PageAction action : page.getPageActions()){
						// 生成动作项
						DefaultTreeNode child = new DefaultTreeNode (
													new MenuRoleNodeData(mpd, subMenu, action));
						loadMenuRoleRelation (child, roles);
						node.getChildren().add(child);
					}
				}
			}
			
			root.getChildren().add(node);
		}
		
	}
	
	/**
	 * 加载菜单或动作对应的角色权限
	 * @param data
	 * @param roles
	 */
	private void loadMenuRoleRelation(DefaultTreeNode node, List<RoleDetails> roles){
		
		MenuRoleNodeData data = (MenuRoleNodeData) node.getData();
		
		for (RoleDetails role : roles){
			
			// 如果是目录，则判断其子结点是否都选中
			if (node.getChildCount() > 0){
				data.getRoles().put(role.getCode(), new MenuRoleRelation(node, role,
									checkChildAllSelected (node, role.getCode())));
				continue;
			}
			
			// 先找下角色中是否有这菜单权限
			MenuPermission equalValue = null;
			for (MenuPermission perm : role.getPermissions()){
				
				// 先看菜单是否相同，如果相同再看是否是动作
				if (!equalMenuRolePerm(data, perm))
					continue;
				
				if (data.getMenuAction() == null){
					equalValue = perm;
				}else if (perm.isGrantAllAction() ||
						perm.getGrantActions().contains(data.getMenuAction().getName())){
					equalValue = perm;
				}
				break;
			}
			
			data.getRoles().put(role.getCode(), new MenuRoleRelation(node, role, equalValue != null));
			
		}
	}
	
	private boolean checkChildAllSelected (DefaultTreeNode node, String roleCode){
		
		for (TreeNode child : node.getChildren()){
			MenuRoleNodeData data = (MenuRoleNodeData) child.getData();
			if (data == null) return false;
			MenuRoleRelation roleRel = data.getRoles().get(roleCode);
			if (roleRel == null || roleRel.isSelected() == false)
				return false;
		}
		
		return true;
	}
	
	private boolean equalMenuRolePerm (MenuRoleNodeData data, MenuPermission perm){
		if (data.getMenuItem() == null || data.getMenuPosition() == null)
			return false;
		
		return data.getPageDomain() == perm.getPageDomain() &&
				StringKit.equals(data.getMenuPosition().getName(), perm.getMenuPosition()) &&
				StringKit.equals(data.getMenuItem().getPermName(), perm.getMenuName());
	}
}
