package com.easyeip.jsfboot.user.type;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;

/**
 * 用户管理适配器
 * @author ihaoqi
 *
 */
public interface AccountManager extends AccountSearcher {
	
	/**
	 * 添加账户
	 * @param entry 账户实体
	 * @return 返回新账户的key
	 * @throws Exception 出错抛出异常
	 */
	AccountEntity addAccount (AccountEntity form) throws Exception;
	
	/**
	 * 删除指定key的账户
	 * @param accountId
	 * @throws Exception
	 */
	void removeAccount (String accountKey) throws Exception;
	
	/**
	 * 更新账号信息
	 * @param entry
	 * @throws Exception
	 */
	void updateAccount (AccountEntity entry) throws Exception;
	
	/**
	 * 修改密码
	 * @param accountKey
	 * @param newPassword
	 * @throws Exception
	 */
	void modifyPassword (String accountKey, String newPassword) throws Exception;
	
	/**
	 * 修改密码，会验证旧密码
	 * @param accountKey
	 * @param oldPassword
	 * @param newPassword
	 * @throws Exception
	 */
	void modifyPassword (String accountKey, String oldPassword, String newPassword) throws Exception;
	
	/**
	 * 添加一个角色
	 * @param name
	 * @param title
	 * @return
	 * @throws Exception
	 */
	RoleDetails addRole(RoleDetails roleForm) throws Exception;
	
	/**
	 * 移除指定名称的角色
	 * @param roleCode
	 * @throws Exception
	 */
	void removeRole (String roleCode) throws Exception;
	
	/**
	 * 更新角色
	 * @param role
	 * @throws Exception
	 */
	void updateRole (RoleDetails roleForm) throws Exception;
	
	/**
	 * 添加分组
	 * @param form
	 * @param parent
	 * @return
	 * @throws Exception
	 */
	AccountGroup addGroup(AccountGroup groupForm, AccountGroup parent) throws Exception;
	
	/**
	 * 删除分组
	 * @param group
	 * @throws Exception
	 */
	void removeGroup(String groupCode) throws Exception;
	
	/**
	 * 更新分组
	 * @param group
	 * @throws Exception
	 */
	void updateGroup (AccountGroup groupForm) throws Exception;
}
