package com.easyeip.jsfboot.user.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;
import com.easyeip.jsfboot.core.secutiry.admin.AccountRealm;
import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuAccessVoter;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuPermission;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuPermissionManager;
import com.easyeip.jsfboot.core.secutiry.ppm.VoterMenuDetails;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.NotifyCallback;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.user.bean.type.MenuPositionDetails;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.utils.StringKit;

/**
 * 账号查询与权限检测服务
 * @author liao
 *
 */
public class AccountRealmService extends GenericService implements AccountRealm, NotifyCallback, MenuAccessVoter {
	
	@UseJsfbootService(RegistryUserManager.class)
	RegistryUserManager userManager;
	
	private MenuPermissionManager permManager;
	private long menuLastModify = 0;
	private ServiceContext context;
	
	private List<String> decisionMenuBars;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		this.context = context;
		decisionMenuBars = new ArrayList<String>();
	}

	@Override
	public String getRealmName() {
		return context.getModule().getName();
	}
	
	@Override
	public AccountDetails loadAccountByLoginId(String loginId) throws Exception {
		AccountEntity entry = userManager.queryAccountOne (loginId);
		if (entry == null)
			return null;
		return new RegistryUserDetailsImpl(this, entry);
	}
	
	@Override
	public boolean checkPermission(AccountDetails account, JpnUrlRequestInfo page) {
		
		return getMenuPermissionManager().checkPermission(account, page);

	}

	@Override
	public void notifyCallback(Object sender, String type, Map<String, Object> param) {
		if (StringKit.equals(type, RegistryUserManager.NOTIFY_CONFMENUS) && permManager != null){
			initAccessDecisionMenu();
		}
	}

	/**
	 * 初使化权限管理器
	 * @return
	 */
	private MenuPermissionManager getMenuPermissionManager(){
		
		SiteMenuManager menuManager = context.getDriver().getSurfaceService().getMenuManager();
		
		if (permManager != null){
			
			// 更新菜单
			long newModifyTime = menuManager.getLastApplyTime();
			if (menuLastModify != newModifyTime){
				menuLastModify = newModifyTime;
				initAccessDecisionMenu();
			}
			
			return permManager;
		}
		
		// 初使化公共页面
		permManager = new MenuPermissionManager(menuManager);
		permManager.initModulePublicPages(context.getDriver().getModuleManager());
		
		// 取得菜单最后修改时间
		menuLastModify = context.getDriver().getSurfaceService().getMenuManager().getLastApplyTime();
		
		// 设置菜单投票器
		permManager.setMenuAccessVoter(this);

		// 添加菜单改变通知
		context.addServiceListener(RegistryUserManager.class, this, RegistryUserManager.NOTIFY_CONFMENUS);
		
		// 初使化菜单
		initAccessDecisionMenu();
		
		return permManager;
	}
	
	/**
	 * 初使化要管理权限的菜单
	 */
	private void initAccessDecisionMenu(){
		permManager.clearAccessDecisionMenu();
		decisionMenuBars.clear();
		for (MenuPositionDetails mpd : userManager.getConfMenus().values()){
			permManager.addAccessDecisionMenu(mpd.getDomain(), mpd.getPosition().getName());
			String decMenuName = mpd.getDomain().toString() + "_" + mpd.getPosition().getName();
			decisionMenuBars.add(decMenuName);
		}
	}

	@Override
	public boolean voter(AccountDetails account, VoterMenuDetails menu) {
		
		// 放行超级管理员
		if (account.isSuperAdmin()){
			return true;
		}
		
		// 如果菜单位置不受权限控制则放行
		String decMenuName = menu.getPageDomain().toString() + "_" + menu.getMenuPosition();
		if (!decisionMenuBars.contains(decMenuName)){
			return true;
		}
		
		// 判断用户的角色是否可以访问这个菜单或动作
		for (String roleCode : account.getGrantRoles()){
			RoleDetails roleEntity = userManager.queryRoleOne(roleCode);
			if (roleEntity == null)
				continue;
			for (MenuPermission mp : roleEntity.getPermissions()){
				// 比较菜单是否一致
				if (!permissionEqual(mp, menu)){
					continue;
				}
				
				// 如果没有动作，表示这个菜单可用，返回true
				if (mp.isGrantAllAction() || StringKit.isEmpty(menu.getActionName())){
					return true;
				}
				
				// 再比较动作是否允许
				return mp.getGrantActions().contains(menu.getActionName());
			}
		}
		return false;
	}
	
	/**
	 * 判断投票菜单与权限是否一样
	 * @param mp
	 * @param vmd
	 * @return
	 */
	private boolean permissionEqual(MenuPermission mp, VoterMenuDetails vmd){
		return mp.getPageDomain() == vmd.getPageDomain() &&
				StringKit.equals(mp.getMenuName(), vmd.getMenuName()) &&
				StringKit.equals(mp.getMenuPosition(), vmd.getMenuPosition());
	}

	@Override
	public MenuAccessVoter getMenuAccessVoter() {
		return getMenuPermissionManager().getMenuAccessVoter();
	}
}
