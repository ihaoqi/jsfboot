package com.easyeip.jsfboot.user.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.user.bean.type.AccountEntityRow;
import com.easyeip.jsfboot.user.bean.type.EditPasswordForm;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountEntity;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountGroup;
import com.easyeip.jsfboot.user.type.AccountGroup;

/**
 * 用户管理模块
 * @author ihaoqi
 *
 */
public interface RegistryUserGroupBean {
	
	/**
	 * 查询账户分组
	 * @return 返回只读列表
	 */
	List<AccountGroup> getGroupList();

	/**
	 * 添加分组
	 * @param form
	 */
	String endAddGroup ();
	
	/**
	 * 删除分组
	 * @param event
	 */
	void delAccountGroup (ActionEvent event);

	/**
	 * 保存分组
	 * @param event
	 */
	String endEditGroup ();

	/**
	 * 获取用户列表
	 * @return
	 */
	List<AccountEntityRow> getUserList();

	/**
	 * 添加用户
	 * @param form
	 */
	String endAddUser ();
	
	/**
	 * 保存用户
	 * @param event
	 */
	String endEditUser ();
	
	/**
	 * 删除用户
	 * @param event
	 */
	void delAccountUser (ActionEvent event);
	
	/**
	 * 取得修改密码表单
	 * @return
	 */
	EditPasswordForm getEditPasswordForm();
	
	/**
	 * 开始修改密码
	 * @param event
	 */
	void beginEditPassword (ActionEvent event);

	/**
	 * 修改密码
	 * @return
	 */
	void endEditPassword (ActionEvent event);
	
	/**
	 * 取得添加分组表单
	 * @return
	 */
	RegistryAccountGroup getAddGroupForm ();
	
	/**
	 * 取得添加用户表单
	 * @return
	 */
	RegistryAccountEntity getAddUserForm ();
}
