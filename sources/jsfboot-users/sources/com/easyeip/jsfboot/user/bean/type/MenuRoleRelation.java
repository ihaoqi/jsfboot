package com.easyeip.jsfboot.user.bean.type;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;

/**
 * 角色与菜单关联
 * @author liao
 *
 */
public class MenuRoleRelation {
	
	private DefaultTreeNode node;
	private RoleDetails role;
	private boolean selected = false;
	
	public MenuRoleRelation(DefaultTreeNode node, RoleDetails role, boolean selected){
		this.node = node;
		this.role = role;
		this.selected = selected;
	}

	public boolean isSelected() {
		return selected;
	}
	
	public void cancelSelected(){
		this.selected = false;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
		
		// 选择子结点
		selectChildren (node, selected);
		
		MenuRoleNodeData data = (MenuRoleNodeData)node.getData();
		
		if (selected == false){
			cancelParentSelected();
		}else if (data != null && data.getMenuAction() != null){
			// 如果是动作则选中上级
			MenuRoleNodeData pd = (MenuRoleNodeData) node.getParent().getData();
			pd.getRoles().get(role.getCode()).selected = selected;
		}
	}
	
	private void cancelParentSelected(){
		if (node.getParent() == null)
			return;
		
		MenuRoleNodeData data = (MenuRoleNodeData) node.getParent().getData();
		if (data == null)
			return;
		
		MenuRoleRelation rel = data.getRoles().get(role.getCode());
		// 如果当前结点是动作则不取消上级（因为上级是菜单）
		if (((MenuRoleNodeData)node.getData()).getMenuAction() == null){
			rel.cancelSelected();
		}
		rel.cancelParentSelected();
	}
	
	private int selectChildren (DefaultTreeNode node, boolean selected){
		for (TreeNode child : node.getChildren()){
			MenuRoleNodeData data = (MenuRoleNodeData) child.getData();
			MenuRoleRelation rel = data.getRoles().get(role.getCode());
			if (rel != null){
				rel.setSelected(selected);
			}
		}
		
		return node.getChildCount();
	}
}
