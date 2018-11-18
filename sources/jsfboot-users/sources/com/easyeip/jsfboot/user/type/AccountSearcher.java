package com.easyeip.jsfboot.user.type;

import java.util.List;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;

/**
 * 用户信息查询接口
 * @author ihaoqi
 *
 */
public interface AccountSearcher {
	
	/**
	 * 获取所有角色的列表
	 * @return
	 */
	List<RoleDetails> queryRoleList();
	
	/**
	 * 查询指定代码的角色
	 * @param code
	 * @return
	 */
	RoleDetails queryRoleOne(String code);
	
	/**
	 * 查询账户分组
	 * @return 返回只读列表
	 */
	List<AccountGroup> queryGroupList();
	
	/**
	 * 查询指定代码分组
	 * @param code
	 * @return
	 */
	AccountGroup queryGroupOne(String code);

	/**
	 * 查询用户列表
	 * @param pageRow	表页行数
	 * @param filter	过滤条件，为空表示不过滤
	 * @return 返回查询结果对象
	 */
	QueryResult queryAccountList(int pageRow, QueryCondition filter);
	
	/**
	 * 查询指定key的账户
	 * @param accountKey
	 * @return
	 */
	AccountEntity queryAccountOne(String accountKey);
}
