package com.easyeip.jsfboot.user.type;

import java.util.List;
import java.util.Map;

/**
 * 账户实体对象
 * @author liao
 *
 */
public interface AccountEntity {
	
	public static final String FIELD_ID = "id";
	public static final String FIELD_GROUPID = "groupId";
	public static final String FIELD_LOGINID = "loginId";
	public static final String FIELD_USERNAME = "userName";
	public static final String FIELD_EXPLAIN = "explain";
	public static final String FIELD_PASSWORD = "password";
	public static final String FIELD_ROLES = "roles";
	public static final String FIELD_ACCOUNTEXPIRED = "accountExpired";
	public static final String FIELD_ACCOUNTLOCKED = "accountLocked";
	public static final String FIELD_PASSWORDEXPIRED = "passwordExpired";
	public static final String FIELD_ENABLED = "enabled";
	
	/**
	 * 取得账户唯一标记（不可修改）
	 * @return
	 */
	String getId();
	
	/**
	 * 取得用于登录的ID（不可修改）
	 * @return
	 */
	String getLoginId();
	
	/**
	 * 取得所在分组ID
	 * @return
	 */
	String getGroupCode();
	
	/**
	 * 设置分组代码
	 * @param groupCode
	 */
	void setGroupCode(String groupCode);
	
	/**
	 * 获取用户的中文名称
	 * @return
	 */
	String getUserName();
	
	/**
	 * 设置账号中文名（不用来登录）
	 * @param name
	 */
	void setUserName(String name);
	
	/**
	 * 获取登录密码
	 * @return
	 */
	public String getPassword();
	
	/**
	 * 取得用户说明
	 * @return
	 */
	public String getExplain();

	/**
	 * 设置用户说明
	 * @param explain
	 */
	public void setExplain(String explain);

	/**
	 * 表示帐户是否已过期，已过期的账户不允许登录。
	 * @return
	 */
	boolean isAccountExpired();
	
	/**
	 * 设置账号是否已过期
	 * @param expired
	 */
	void setAccountExpired(boolean expired);

	/**
	 * 表示帐户是否已锁定，已锁定的账户不允许登录。
	 * @return
	 */
	boolean isAccountLocked();
	
	/**
	 * 设置账号是否已锁定
	 * @param locked
	 */
	void setAccountLocked(boolean locked);

	/**
	 * 表示帐户密码是否已过期，已过期的需要阻止登录或要求修改密码。
	 * @return
	 */
	boolean isPasswordExpired();
	
	/**
	 * 设置帐户密码是否已过期
	 * @param expired
	 */
	void setPasswordExpired(boolean expired);

	/**
	 * 表示帐户是否可用，已禁用的账户不允许登录。
	 * @return
	 */
	boolean isEnabled();
	
	/**
	 * 设置可用性
	 * @param enabled
	 */
	void setEnabled(boolean enabled);
	
	/**
	 * 获取账户所有允许的角色
	 * @return
	 */
	List<String> getRoles();
	
	/**
	 * 设置角色，多个角色用逗号隔开
	 * @param roles
	 */
	void setRoles (String roles);
	
	/**
	 * 取得更多字段（只读）
	 * @return 返回字段map
	 */
	Map<String, String> getMoreFields();
	
	/**
	 * 设置更多字段的值
	 * @param name
	 * @param value
	 */
	void setMoreField (String name, String value);
}
