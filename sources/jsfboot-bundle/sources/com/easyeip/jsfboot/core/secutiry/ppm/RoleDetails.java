package com.easyeip.jsfboot.core.secutiry.ppm;

import java.util.List;

/**
 * 角色实体
 * @author liao
 *
 */
public interface RoleDetails {
	
	/**
	 * 取得英文名称
	 * @return
	 */
	String getCode();
	
	/**
	 * 设置代码
	 * @param code
	 * @return
	 */
	void setCode (String code);
	
	/**
	 * 取得中文标题
	 * @return
	 */
	String getTitle();
	
	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	void setTitle (String title);
	
	/**
	 * 取得说明
	 * @return
	 */
	String getExplain();
	
	/**
	 * 设置说明
	 */
	void setExplain(String explain);
	
	/**
	 * 取得权限列表
	 * @return
	 */
	List<MenuPermission> getPermissions();
	
	/**
	 * 添加权限，如果存在相同名称的会被替换
	 * @param perm
	 * @throws Exception
	 */
	void addPermission(MenuPermission perm);
	
	/**
	 * 删除权限
	 * @param permKey
	 * @return
	 */
	boolean removePermission (String permKey);
}
