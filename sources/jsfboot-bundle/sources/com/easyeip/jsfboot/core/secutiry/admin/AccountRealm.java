package com.easyeip.jsfboot.core.secutiry.admin;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuAccessVoter;

/**
 * 用户域适配器
 * @author ihaoqi
 *
 */
public interface AccountRealm {
	
	/**
	 * 取得适配器名称
	 * @return
	 */
	String getRealmName();
	
	/**
	 * 根据用户名查找账户信息
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	AccountDetails loadAccountByLoginId(String loginId) throws Exception;
	
	
	/**
	 * 检测该用户是否可以访问这个页面
	 * @param jpnUrl
	 * @param request
	 * @return
	 */
	boolean checkPermission(AccountDetails account, JpnUrlRequestInfo page);
	
	/**
	 * 获取菜单可用投票器
	 * @return
	 */
	MenuAccessVoter getMenuAccessVoter();
}
