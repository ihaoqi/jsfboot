package com.easyeip.jsfboot.web;

import java.util.HashMap;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.secutiry.SecutiryService;
import com.easyeip.jsfboot.core.secutiry.ppm.DefaultVoterMenuDetails;
import com.easyeip.jsfboot.core.secutiry.ppm.VoterMenuDetails;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;

/**
 * 判断当前页面的动作是否有权限，使用方法：
 * 在页面上使用EL表达式 #{actionAuthorize['add']} 返回true表示可用，返回false表示不可用
 * 如果当前页面不属于菜单或动作不存在，则全部返回false
 * @author liao
 *
 */
public class ActionAuthorizeAttribate extends HashMap<String,Boolean> {

	private static final long serialVersionUID = 1L;
	
	public static final String ATTR_NAME = "actionAuthorize";
	
	private NavigatorPath navPath;
	private SecutiryService secutiry;

	public ActionAuthorizeAttribate(NavigatorPath navPath){
		this.navPath = navPath;
	}
	
	private void initActionEnabled(NavigatorPath navPath){

		// 获取动作列表
		MenuPage menuPage = MenuPageUtils.findMenuPageBySource(
				navPath.getMenuItem().getPageSource(), navPath.getPageDomain(), false);
		if (menuPage == null)
			return;
		
		//判断是否有权限
		for (PageAction action : menuPage.getPageActions()){
			VoterMenuDetails voter = new DefaultVoterMenuDetails(navPath.getPageDomain(), 
								navPath.getMenuPosition(), navPath.getMenuItem(), action.getName());
			boolean ok = secutiry.checkPermission(secutiry.getAuthentication(), voter);
			this.put(action.getName(), Boolean.valueOf(ok));
		}
	}
	
	@Override
	public Boolean get(Object key) {
		
		// 首次使用时初使化
		if (secutiry == null){
			secutiry = JsfbootContext.getDriver().getSecutiryService();
			if (navPath.getMenuItem() != null){
				initActionEnabled(navPath);
			}
		}
		
		// 没有就返回false
		Boolean result = super.get(key);
		if (result == null)
			return false;
		
		return result;
	}
}
