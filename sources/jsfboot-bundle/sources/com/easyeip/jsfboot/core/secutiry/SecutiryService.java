package com.easyeip.jsfboot.core.secutiry;

import javax.servlet.http.HttpServletRequest;

import com.easyeip.jsfboot.core.secutiry.ppm.VoterMenuDetails;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;

/**
 * 用户安全管理（用户授权）
 * @author ihaoqi
 *
 */
public interface SecutiryService {
	
	/**
	 * 登记安全实现，只有登录后才能获取账号
	 * @param context
	 * @throws Exception 如果已登记会抛出异常
	 */
	void registerSecurityContext(JsfbootSecurityContext context) throws Exception;

	/**
	 * 根据用户名查找账户信息
	 * @param loginId
	 * @return
	 * @throws Exception
	 */
	AccountDetails loadAccountByLoginId(String loginId) throws Exception;
	
	/**
	 * 检测该用户是否可以访问这个页面
	 * @param account
	 * @param jpnUrl
	 * @return
	 */
	boolean checkPermission(AccountDetails account, JpnUrlRequestInfo page);
	
	/**
	 * 检测一个菜单或动作是否可以访问
	 * @param account
	 * @param menuOrAction
	 */
	boolean checkPermission(AccountDetails account, VoterMenuDetails menuOrAction);
	
	/**
	 * 取得当前登录的账号，没有登录会返回null
	 * @return
	 */
	AccountDetails getAuthentication();

	/**
	 * 取得指定位置的菜单，并按用户权限过滤
	 * @param pageDomain
	 * @param menuPosition
	 * @param request
	 * @return
	 */
	SiteMenuBar filterAccountMenuBar(PageDomainType pageDomain, String menuPosition, HttpServletRequest request);
}
