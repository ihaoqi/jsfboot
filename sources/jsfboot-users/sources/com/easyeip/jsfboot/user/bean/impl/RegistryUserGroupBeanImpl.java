package com.easyeip.jsfboot.user.bean.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.beans.JsfbootBeanException;
import com.easyeip.jsfboot.user.bean.RegistryUserGroupBean;
import com.easyeip.jsfboot.user.bean.type.AccountEntityRow;
import com.easyeip.jsfboot.user.bean.type.EditPasswordForm;
import com.easyeip.jsfboot.user.registry.RegistryUserManager;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountEntity;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountGroup;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.user.type.AccountGroup;
import com.easyeip.jsfboot.user.type.QueryResult;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;

public class RegistryUserGroupBeanImpl extends JsfbootBean implements RegistryUserGroupBean {
	
	@UseJsfbootService(RegistryUserManager.class)
	private RegistryUserManager userManager;
	
	private RegistryAccountGroup addGroupForm;
	private RegistryAccountEntity addUserForm;
	private EditPasswordForm editPasswordForm;

	@Override
	public List<AccountGroup> getGroupList() {
		return userManager.queryGroupList();
	}
	
	@Override
	public RegistryAccountGroup getAddGroupForm() {
		if (addGroupForm == null){
			// 从url参数中取得要编辑的分组
			String code = FacesUtils.getFacesRequestParam("group");
			if (StringKit.notEmpty(code)){
				addGroupForm = (RegistryAccountGroup) userManager.queryGroupOne(code);
				if (addGroupForm == null){
					throw new JsfbootBeanException(code + "分组不存在。");
				}
			}else{
				addGroupForm = new RegistryAccountGroup();
			}
		}
		return addGroupForm;
	}

	@Override
	public String endAddGroup() {
		try {
			userManager.addGroup(addGroupForm, null);
			return "../groupmgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("添加分组", e.getMessage());
			return null;
		}
	}

	@Override
	public String endEditGroup() {
		try {
			userManager.updateGroup(addGroupForm);
			return "../groupmgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("修改分组", e.getMessage());
			return null;
		}
	}

	@Override
	public void delAccountGroup(ActionEvent event) {
		AccountGroup group = (AccountGroup) event.getComponent().getAttributes().get("group");
		try {
			userManager.removeGroup(group.getCode());
		} catch (Exception e) {
			FacesUtils.addMessageError("删除分组", e.getMessage());
		}
	}

	@Override
	public List<AccountEntityRow> getUserList() {
		List<AccountEntityRow> result = new ArrayList<AccountEntityRow>();
		for (AccountEntity entry : userManager.queryAccountList(QueryResult.NOT_PAGINATION, null).getPageAt(0)){
			result.add(new AccountEntityRow(userManager, entry));
		}
		
		return result;
	}

	@Override
	public String endAddUser() {
		try {
			userManager.addAccount(addUserForm);
			return "../usermgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("添加账号", e.getMessage());
			return null;
		}
	}

	@Override
	public void delAccountUser(ActionEvent event) {
		AccountEntity user = (AccountEntity) event.getComponent().getAttributes().get("user");

		try {
			userManager.removeAccount(user.getId());
		} catch (Exception e) {
			FacesUtils.addMessageError("删除账号", e.getMessage());
		}
	}

	@Override
	public String endEditUser() {
		try {
			userManager.updateAccount(addUserForm);
			return "../usermgr?faces-redirect=true";
		} catch (Exception e) {
			FacesUtils.addMessageError("修改账号", e.getMessage());
			return null;
		}
	}
	
	@Override
	public EditPasswordForm getEditPasswordForm() {
		return editPasswordForm;
	}
	
	@Override
	public void beginEditPassword(ActionEvent event) {
		AccountEntity user = (AccountEntity) event.getComponent().getAttributes().get("user");
		editPasswordForm = new EditPasswordForm (user);
		FacesUtils.clearInputInvalidState();
	}
	
	@Override
	public void endEditPassword(ActionEvent event) {

		if (!StringKit.equals(editPasswordForm.getNewPassword1(), editPasswordForm.getNewPassword2())){
			FacesUtils.addMessageWarn("修改密码", "两次输入的新密码不一致，请重新输入。");
			return;
		}
		
		try {
			userManager.modifyPassword(
					editPasswordForm.getAccountEntry().getLoginId(), editPasswordForm.getNewPassword1());
			FacesUtils.setAllowCloseDialog(true);
		} catch (Exception e) {
			FacesUtils.addMessageError("修改密码", e.getMessage());
		}
	}

	@Override
	public RegistryAccountEntity getAddUserForm() {
		if (addUserForm == null){
			// 从url参数中取得要编辑的角色
			String code = FacesUtils.getFacesRequestParam("user");
			if (StringKit.notEmpty(code)){
				addUserForm = (RegistryAccountEntity) userManager.queryAccountOne(code);
				if (addUserForm == null){
					throw new JsfbootBeanException(code + "账号不存在。");
				}
			}else{
				addUserForm = new RegistryAccountEntity();
			}
		}
		
		return addUserForm;
	}
}
