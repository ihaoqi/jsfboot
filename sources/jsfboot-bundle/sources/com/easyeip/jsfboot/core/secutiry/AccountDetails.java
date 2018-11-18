package com.easyeip.jsfboot.core.secutiry;

import java.util.List;

/**
 * 账户详情
 * @author liao
 *
 */
public interface AccountDetails {
	
	/**
	 * 取得账号提供者名称（Realm）
	 * @return
	 */
	String getRealmName();
	
	/**
	 * 获取账户所有允许的角色，不允许返回null
	 * @return
	 */
	List<String> getGrantRoles();
	
	/**
	 * 获取密码
	 * @return
	 */
	String getPassword();

	/**
	 * 获取用户登录ID
	 * @return
	 */
	String getLoginId();
	
	/**
	 * 取得用户名称
	 * @return
	 */
	String getNickname();
	
	/**
	 * 是否是超级管理员
	 * @return
	 */
	boolean isSuperAdmin();

	/**
	 * 表示帐户是否已过期，已过期的账户不允许登录。
	 * @return
	 */
	boolean isAccountExpired();

	/**
	 * 表示帐户是否已锁定，已锁定的账户不允许登录。
	 * @return
	 */
	boolean isAccountLocked();

	/**
	 * 表示帐户密码是否已过期，已过期的需要阻止登录或要求修改密码。
	 * @return
	 */
	boolean isPasswordExpired();

	/**
	 * 表示帐户是否已禁用，已禁用的账户不允许登录。
	 * @return
	 */
	boolean isEnabled();

}
