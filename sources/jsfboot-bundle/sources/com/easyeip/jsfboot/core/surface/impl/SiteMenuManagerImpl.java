package com.easyeip.jsfboot.core.surface.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map.Entry;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.beans.JsfbootBeanException;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.module.type.ModuleAdminMenu;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.surface.DefaultPageSource;
import com.easyeip.jsfboot.core.surface.MenuDataStorage;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.MenuPageSource;
import com.easyeip.jsfboot.core.surface.ThemeMenuPosition;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;

/**
 * 站点菜单管理实现
 * @author ihaoqi
 *
 */
public class SiteMenuManagerImpl implements SiteMenuManager {

	public static final String USERMENU_REGPATH = "/jsfboot/core/menu-config";
	
	private static final String MENU_PATH_NAME = "menubar";
	private static final String MENU_HOLDER_NAME = "position";
		
	private MenuDataStorage userMenuData;	// 用户配置的菜单（要保存起来）
	private SiteMenuBar backConfMenubar;	// 后台配置菜单（不需要保存）
	
	private long lastApplyTime = 0;
	
	public void init (ModuleManager mmgr, RegistryService regsvr){
		
		// 先创建两个空系统菜单，确保存在并且在前面
		backConfMenubar = new SiteMenuBarImpl (MAIN_MENUBAR, ADMIN_MENUBAR_TITLE);
		
		userMenuData = new SiteMenuDataImpl(backConfMenubar);
		
		// 用户主菜单，禁止删除、改名
		SiteMenuBar mainMenu = addSiteMenubar(MAIN_MENUBAR);
		mainMenu.setHasMain (true);
		mainMenu.setTitle(MAIN_MENUBAR_TITLE);
		
		// 加载模块中配置的要预先加载的菜单
		loadModuleConfMenu (mmgr);
		
		// 加载用户配置的菜单
		loadUserConfMenu (regsvr);
		
		updateLastApplyTime();
	}
	
	private class AnchorMenu{
		public JsfbootModule module;
		public ModuleAdminMenu menu;
	}

	/**
	 * 加载模块中的默认配置菜单，仅限系统配置菜单（模块xml中设置了anthor的）
	 * @param mmgr 模块管理器
	 */
	private void loadModuleConfMenu (ModuleManager mmgr){
		
		// 找出所有要固定的菜单来（只限第一个）
		List<AnchorMenu> anchorList = new ArrayList<AnchorMenu> ();
		for (JsfbootModule mpk : mmgr.getAllModule()){
			ModuleAdminMenu mpp = mpk.getPageResource().getAdminMenu();
			
			if (mpp == null || mpp.getMenuList().size() == 0)
				continue;

			AnchorMenu am = new AnchorMenu();
			am.menu = mpp;
			am.module = mpk;
			anchorList.add(am);
		}
		
		// 添加模块菜单到配置菜单中
		SiteMenuBar confMenu = backConfMenubar;
		while (true){
			int curSize = anchorList.size();
			for (int i = 0; i < anchorList.size(); i ++){
				
				AnchorMenu am = anchorList.get(i);
				
				if (appendModuleConfMenu (confMenu, am)){
					anchorList.remove(i);
					i --;
				}
			}
			
			// 如果对列空了，或没动就退出
			if (anchorList.size() == 0 || anchorList.size() == curSize){
				break;
			}
		}
		
		// 如果还有没有添加的，插入到更多功能菜单下
		if (anchorList.size() > 0){
			
			SiteMenuItem moreMenu = confMenu.addSubMenu(MenuItemType.MenuFolder);
			moreMenu.setName("moremenu");
			moreMenu.setTitle("更多功能");
			moreMenu.setIcon("fa fa-star-o");
			
			for (AnchorMenu am : anchorList){
				for (MenuPage mp : am.menu.getMenuList()){
					copyMenuDefinition (moreMenu.addSubMenu(MenuItemType.MenuPage), mp, am.module);
				}
			}
		}
		
		confMenu.updatePermNames();
	}
	
	/**
	 * 添加一个模块配置菜单到菜单条
	 * @param menubar
	 * @param am
	 * @return
	 */
	boolean appendModuleConfMenu (SiteMenuBar menubar, AnchorMenu am){

		int menu_index = -1;
		for (MenuPage menu_page : am.menu.getMenuList()){
			menu_index ++;
			MenuItemType pageType = menu_page.getSubMenu().size() > 0 ? MenuItemType.MenuFolder : MenuItemType.MenuPage;
			SiteMenuItem anchor_menu = makeNewSiteMenu(menubar,
					am.menu.getAnchor(), am.menu.getPosition(), menu_index, pageType);
			
			if (anchor_menu == null)
				return false;
			
			// 复制子菜单
			copyMenuDefinition (anchor_menu, menu_page, am.module);
		}
		
		return true;
	}
	
	/**
	 * 根据站位生成一个新菜单
	 * @param anchor
	 * @param position
	 * @return
	 */
	private SiteMenuItem makeNewSiteMenu (SiteMenuBar menubar, String anchor,
						String position, int subindex, MenuItemType pageType){
		
		// 如果ahchor 为空，表示加载到主菜单下（忽略position）
		if (StringKit.isEmpty(anchor)){
			return menubar.addSubMenu(pageType);
		}
		
		// 查询指定的菜单
		SiteMenuItem exist = findMenuBarItem (menubar, anchor);
		if (exist == null){
			return null;
		}
		
		// 根据位置添加菜单
		if (StringKit.isEmpty(position)){
			position = "child";
		}
		
		if (position.equals("next")){
			
			// 取得菜单容器
			SiteMenuItem parent = exist.getParent();
			SubMenuContainer container = null;
			if (parent == null){
				container = menubar;

			}else{
				container = parent;
			}
			
			// 在指定位置插入
			int index = -1;
			for (int i = 0; i < container.getSubMenuCount(); i ++){
				SiteMenuItem item = container.getSubMenu(i);
				if (item.getName().equals(exist.getName())){
					index = i; break;
				}
			}
			if (index >= 0){
				return container.insertSubMenu(pageType, index + 1 + subindex);
			}
		}else{
			
			// 添加子菜单
			return exist.addSubMenu(pageType);
			
		}
		
		return null;
	}
	
	/**
	 * 把模块中的菜单克隆到系统菜单项中
	 * @param desc
	 * @param src
	 * @param module
	 */
	private void copyMenuDefinition (SiteMenuItem desc, MenuPage src,  JsfbootModule module){
		desc.setIcon(src.getIcon());
		desc.setName(src.getName());
		desc.setTitle(src.getTitle());

		// 这里保存最完整的导航路径
		desc.setOutcome(src.getOutcome());
		desc.setPageSource(new DefaultPageSource(src));
		
		for (MenuPage sub : src.getSubMenu()){
			MenuItemType pageType = sub.getSubMenu().size() > 0 ? MenuItemType.MenuFolder : MenuItemType.MenuPage;
			copyMenuDefinition (desc.addSubMenu(pageType), sub, module);
		}
	}
	
	/**
	 * 查找指定名称的菜单
	 * @param menuBar
	 * @param menuName
	 * @return
	 */
	private SiteMenuItem findMenuBarItem (SubMenuContainer menuBar, String menuName){
		for (int i = 0; i < menuBar.getSubMenuCount(); i ++){
			SiteMenuItem item = menuBar.getSubMenu(i);
			if (StringKit.isEmpty(item.getName()) == false && item.getName().equals(menuName))
				return item;
			
			if (item.getSubMenuCount() > 0){
				item = findMenuBarItem (item, menuName);
				if (item != null){
					return item;
				}
			}
		}
		return null;
	}
	
	/**
	 * 加载用户手动配置菜单（在注册表中）
	 */
	private void loadUserConfMenu(RegistryService regsvr){
		
		// 遍历所有用户菜单
		RegistryPath rootPath = RegistryPath.valueOfne(USERMENU_REGPATH).makeChild(MENU_PATH_NAME);
		
		for (RegistryItem item : regsvr.listChildren(rootPath)){
			SiteMenuBar menubar = this.addSiteMenubar(item.getName());
			// 如果要添加的菜单已存则覆盖它（删除原先所有）
			if (menubar.getSubMenuCount() > 0){
				menubar.clearSubMenu();
			}
			
			String hasMain = item.getValue("hasMain");
			menubar.setHasMain(StringKit.isTrue(hasMain));
			menubar.setTitle(item.getValue("title"));
			
			loadRegistryMenu (regsvr, menubar, item);
		}
		
		// 读取前后台菜单占位
		rootPath = RegistryPath.valueOfne(USERMENU_REGPATH).makeChild(MENU_HOLDER_NAME);
		loadMenuPosition(regsvr, rootPath, PageDomainType.Site);
		loadMenuPosition(regsvr, rootPath, PageDomainType.Admin);
	}
	
	private void loadMenuPosition(RegistryService regsvr, RegistryPath rootPath, PageDomainType theme){
		RegistryItem holderItem = regsvr.getItem(rootPath.makeChild(PageDomainType.Site.name()));
		if (holderItem != null){
			ThemeMenuPosition mpm = this.getPositionPair(PageDomainType.Site);
			for (String name : holderItem.valueNames()){
				mpm.pairMenuBar(name, holderItem.getValue(name));
			}
		}
	}
	
	private void saveUserConfMenu(RegistryService regsvr) throws RegistryException{
		
		// 先清除原有菜单
		RegistryPath rootPath = RegistryPath.valueOf(USERMENU_REGPATH);

		// 再保存新菜单
		regsvr.removeItem(rootPath);
		rootPath = rootPath.makeChild(MENU_PATH_NAME);
		
		for (SiteMenuBar menubar : userMenuData.allSiteMenubar()){
			RegistryItem item = regsvr.createItem(rootPath.makeChild(menubar.getName()));
			item.setValue("hasMain", menubar.getHasMain()?"true":"false");
			item.setValue("title", menubar.getTitle());
			
			regsvr.updateItem(item);
			
			saveRegistryMenu (regsvr, item, menubar);
		}

		// 保存前后台菜单占位
		rootPath = RegistryPath.valueOf(USERMENU_REGPATH).makeChild(MENU_HOLDER_NAME);
		saveMenuPosition (regsvr, rootPath, PageDomainType.Site, "前台菜单占位");
		saveMenuPosition (regsvr, rootPath, PageDomainType.Admin, "后台菜单占位");
	}
	
	private void saveMenuPosition (RegistryService regsvr, RegistryPath rootPath,
			PageDomainType theme, String comment) throws RegistryException{
		
		rootPath = RegistryPath.valueOf(USERMENU_REGPATH).makeChild(MENU_HOLDER_NAME);

		RegistryItem holderItem = regsvr.createItem(rootPath.makeChild(theme.name()));
		holderItem.setComment(comment);
		ThemeMenuPosition mpm = this.getPositionPair(theme);
		for (Entry<String,String> pair : mpm.getPairMap().entrySet()){
			holderItem.setValue(pair.getKey(), pair.getValue());
		}
		regsvr.updateItem(holderItem);
	}
	
	/**
	 * 解析注册表中的菜单配置
	 * @param regsvr
	 * @param owner
	 * @param item
	 */
	private void loadRegistryMenu (RegistryService regsvr,
				SubMenuContainer owner, RegistryItem item){
		
		for (RegistryItem child : regsvr.allChildren(item.getPath())){
			
			// 获取菜单类型
			MenuItemType type = MenuItemType.valueOf(child.getValue("type"));
			
			// 根据类型添加菜单
			SiteMenuItem menu = owner.addSubMenu(type);

			menu.setPermName(child.getName());
			menu.setName(child.getValue("name"));
			menu.setIcon(child.getValue("icon"));
			menu.setTitle(child.getValue("title"));
			menu.setOutcome(child.getValue("outcome"));
			menu.setUrl(child.getValue("url"));
			menu.setParams(child.getValue("params"));
			menu.setNewWindow(!StringKit.isTrue(child.getValue("local")));
			menu.setVisible(StringKit.isTrue(child.getValue("visible"), true));
			
			String rowSource = child.getValue("source");
			if (!StringKit.isEmpty(rowSource)){
				MenuPageSource source = DefaultPageSource.decode(rowSource);
				if (source != null){
					menu.setPageSource(source);
				}
			}
			
			// 添加子菜单
			loadRegistryMenu (regsvr, menu, child);
		}
	}
	
	/**
	 * 保存用户菜单到注册表
	 * @param regsvr
	 * @param path
	 * @param owner
	 */
	private void saveRegistryMenu(RegistryService regsvr,
			RegistryItem item, SubMenuContainer owner) throws RegistryException{
		
		for (SiteMenuItem menu : owner.getSubMenuList()){
			
			// 保存菜单项
			RegistryPath menuPath = RegistryPath.valueOf(item.getPath()).makeChild(menu.getPermName());
			RegistryItem menuItem = null;
			try {
				menuItem = regsvr.createItem(menuPath);
			} catch (RegistryException e) {
				throw new JsfbootBeanException (e);
			}
			
			menuItem.setValue("name", menu.getName());
			menuItem.setValue("icon", menu.getIcon());
			menuItem.setValue("title", menu.getTitle());
			menuItem.setValue("type", menu.getType().name());
			if (!StringKit.isEmpty(menu.getOutcome())){
				menuItem.setValue("outcome", menu.getOutcome());
			}
			menuItem.setValue("params", menu.getParams());
			if (!StringKit.isEmpty(menu.getUrl())){
				menuItem.setValue("url", menu.getUrl());
			}
			menuItem.setValue("local", menu.isNewWindow()?"false":"true");
			if (menu.getPageSource() != null){
				menuItem.setValue("source", menu.getPageSource().encode());
			}
			
			menuItem.setValue("visible", menu.isVisible()?"true":"false");
			
			regsvr.updateItem(menuItem);
			
			// 保存子菜单
			if (menu.getSubMenuCount() > 0){

				saveRegistryMenu (regsvr, regsvr.getItem(menuPath), menu);
				
			}
			
		}
		
	}

	@Override
	public MenuDataStorage cloneSiteMenuData() {
		if (userMenuData instanceof SiteMenuDataImpl)
			return ((SiteMenuDataImpl)userMenuData).cloneMy();
		throw new RuntimeException("没有实现cloneMy接口。");
	}

	@Override
	public void applySiteMenuData(MenuDataStorage newData) {
		if (newData instanceof SiteMenuDataImpl){
			userMenuData = ((SiteMenuDataImpl)newData).cloneMy();
			try {
				saveUserConfMenu (JsfbootContext.getDriver().getRegistryService());
			} catch (RegistryException e) {
				 throw new JsfbootBeanException (e);
			}
			
			updateLastApplyTime();
		}
		else{
			throw new JsfbootBeanException("没有实现cloneMy接口。");
		}
	}
	
	@Override
	public List<SiteMenuBar> allSiteMenubar() {
		return userMenuData.allSiteMenubar();
	}

	@Override
	public SiteMenuBar getSiteMenubar(String menuName, boolean create) {
		return userMenuData.getSiteMenubar(menuName, create);
	}

	@Override
	public SiteMenuBar addSiteMenubar(String menuName) {
		return userMenuData.addSiteMenubar(menuName);
	}

	@Override
	public SiteMenuBar removeSiteMenubar(String menuName) {
		return userMenuData.removeSiteMenubar(menuName);
	}
	
	@Override
	public ThemeMenuPosition getPositionPair(PageDomainType theme) {
		return userMenuData.getPositionPair(theme);
	}

	@Override
	public SiteMenuBar getThemeMenubar(PageDomainType theme, String placeholdeName) {
		return userMenuData.getThemeMenubar(theme, placeholdeName);
	}
	
	@Override
	public SiteMenuBar getAdminMenubar() {
		return backConfMenubar;
	}

	@Override
	public long getLastApplyTime() {
		return lastApplyTime;
	}

	@Override
	public void updateLastApplyTime() {
		lastApplyTime = System.currentTimeMillis();
	}
}
