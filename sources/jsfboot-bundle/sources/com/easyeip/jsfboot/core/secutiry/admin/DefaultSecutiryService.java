package com.easyeip.jsfboot.core.secutiry.admin;

import javax.servlet.http.HttpServletRequest;

import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JsfbootSecurityContext;
import com.easyeip.jsfboot.core.secutiry.JpnUrlRequestInfo;
import com.easyeip.jsfboot.core.secutiry.impl.FilterMenuCache;
import com.easyeip.jsfboot.core.secutiry.impl.RealmManagerImpl;
import com.easyeip.jsfboot.core.secutiry.ppm.DefaultVoterMenuDetails;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuAccessVoter;
import com.easyeip.jsfboot.core.secutiry.ppm.VoterMenuDetails;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.MenuModelUtils;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.core.surface.impl.SiteMenuBarImpl;

public class DefaultSecutiryService extends GenericService implements SecutiryServiceAdmin {

	@UseJsfbootService(SurfaceService.class)
	SurfaceService surface;
	
	private RealmManager manager;
	private JsfbootSecurityContext context;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		manager = new RealmManagerImpl(context.getDriver().getModuleManager());
	}

	@Override
	public RealmManager getRealmManager(){
		return manager;
	}
	
	@Override
	public void registerSecurityContext(JsfbootSecurityContext context) throws Exception {
		if (this.context != null){
			throw new Exception ("已存在安全实现上下文，不能重复注册。");
		}
		
		this.context = context;
	}

	@Override
	public AccountDetails loadAccountByLoginId(String username) throws Exception {
		if (context == null){
			throw new Exception("没有注册安全实现上下文。");
		}
		
		// 遍历所有选择的适配器
		for (RealmSingleton realm : manager.getCurrentRealm()){
			AccountDetails account = realm.getInstance().loadAccountByLoginId(username);
			if (account != null){
				return account;
			}
		}
		return null;
	}

	@Override
	public AccountDetails getAuthentication() {
		if (context == null){
			return null;
		}
		return context.getAuthentication();
	}

	@Override
	public boolean checkPermission(AccountDetails account, JpnUrlRequestInfo page) {
		RealmSingleton realmInst = manager.getCurrentRealm(account.getRealmName());
		if (realmInst == null){
			if (account.isSuperAdmin())
				return true;
			
			return false;
		}
		return realmInst.getInstance().checkPermission(account, page);
	}
	
	@Override
	public boolean checkPermission(AccountDetails account, VoterMenuDetails menuOrAction) {
		// 获得投票器
		MenuAccessVoter mav = manager.getCurrentRealm(account.getRealmName()).getInstance().getMenuAccessVoter();
		return mav.voter(account, menuOrAction);
	}
	
	@Override
	public SiteMenuBar filterAccountMenuBar(PageDomainType pageDomain,
								String menuPosition, HttpServletRequest request) {
		
		AccountDetails account = getAuthentication();
		
		// 获取缓存
		String cacheKey = pageDomain.name() + "_" + menuPosition + "_" + (account==null?"":account.getLoginId());
		FilterMenuCache cache = (FilterMenuCache) request.getSession().getAttribute(cacheKey);
		if (cache != null && surface.getMenuManager().getLastApplyTime() == cache.getLastTime()){
			return cache.getMenubar();
		}
		
		// 查询对应的菜单
		SiteMenuBar menubar = surface.getMenuManager().getThemeMenubar(pageDomain, menuPosition);
		
		// 先创建个空的
		SiteMenuBar userMenu = new SiteMenuBarImpl(menubar == null ? menuPosition : menubar.getName());
		
		// 复制一份，然后没有权限的全部删除
		if (menubar != null && account != null){
			userMenu.copyForm(menubar);
			removeNotPerimMenuBar (pageDomain, menuPosition, userMenu, account);
		}
		
		// 缓存起来
		cache = new FilterMenuCache();
		cache.setLastTime(surface.getMenuManager().getLastApplyTime());
		cache.setMenubar(userMenu);
		request.getSession().setAttribute(cacheKey, cache);
		
		return userMenu;
	}

	/**
	 * 移除没有权限的菜单
	 * @param pageDomain
	 * @param menuPosition
	 * @param menuList
	 * @param account
	 */
	private void removeNotPerimMenuBar (PageDomainType pageDomain, String menuPosition,
									SubMenuContainer menuList, AccountDetails account){
		
		for (int i = 0; i < menuList.getSubMenuCount(); i ++){
			SiteMenuItem menuItem = menuList.getSubMenu(i);
			
			// 先遍历子菜单
			if (menuItem.getType() == MenuItemType.MenuFolder && menuItem.getSubMenuCount() > 0){
				removeNotPerimMenuBar (pageDomain, menuPosition, menuItem, account);
				continue;
			}
			
			// 如果目录是空的则删除
			if (menuItem.getType() == MenuItemType.MenuFolder && menuItem.getSubMenuCount() == 0){
				menuList.removeSubMenu(i);
				i --;
				continue;
			}
			
			// 如果是分隔条则直接放行
			if (MenuModelUtils.isSeparatorItem(menuItem)){
				// 判断上一条是不是分割条，是的则删除本条（不要重复的）
				if (i > 0 && MenuModelUtils.isSeparatorItem(menuList.getSubMenu(i-1))){
					menuList.removeSubMenu(i);
					i --;
				}
				continue;
			}
			
			// 判断权限
			boolean grant = false;
	
			// 从用户的授权适配器
			RealmSingleton single = getRealmManager().getCurrentRealm(account.getRealmName());
			if (single == null){
				// 如果为空则看是否是超级用户
				if (account.isSuperAdmin()){
					grant = true;
				}
			}else{
				// 调用授权适配器的投票器来判断是否有权限
				AccountRealm realm = single.getInstance();
				grant = realm.getMenuAccessVoter().voter(account, 
						new DefaultVoterMenuDetails(pageDomain, menuPosition, menuItem, null));
			}
			
			// 没有权限就移除
			if (grant == false){
				menuList.removeSubMenu(i);
				i --;
				continue;
			}
		}
	}
}
