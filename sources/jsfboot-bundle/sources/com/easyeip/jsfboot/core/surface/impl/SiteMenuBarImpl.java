package com.easyeip.jsfboot.core.surface.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.surface.DefaultPageSource;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.MenuPageSource;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;

/**
 * 菜单条实现
 * @author ihaoqi
 *
 */
public class SiteMenuBarImpl extends SubMenuContainerImpl implements SiteMenuBar{
	
	private String name;
	private String title;
	private boolean isMainMenu;
	
	public SiteMenuBarImpl (String name){
		this.name = name;
		this.isMainMenu = false;
	}
	
	public SiteMenuBarImpl (String name, String title){
		this(name);
		this.setTitle(title);
	}
	
	@Override
	public boolean getHasMain() {
		return isMainMenu;
	}
	
	@Override
	public void setHasMain(boolean hasMain) {
		this.isMainMenu = hasMain;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public void setName(String name) {
		if (RegistryPath.isValidName(name)){
			this.name = name;
		}
	}

	@Override
	public SiteMenuBar copyForm(SiteMenuBar menubar) {
		
		this.clearSubMenu();
		
		name = menubar.getName();
		
		if (menubar.getHasMain()){
			setHasMain (true);
		}
		
		title = menubar.getTitle();
		
		for (SiteMenuItem item : menubar.getSubMenuList()){
			copyMenuItem (this.addSubMenu(item.getType()), item);
		}
		
		return this;
	}
	
	/**
	 * 复制一个菜单，含子菜单
	 * @param desc
	 * @param src
	 */
	private void copyMenuItem (SiteMenuItem desc, SiteMenuItem src){
		
		// 复制单个菜单
		desc.setIcon(src.getIcon());
		desc.setName(src.getName());
		desc.setOutcome(src.getOutcome());
		desc.setTitle(src.getTitle());
		desc.setNewWindow(src.isNewWindow());
		desc.setUrl(src.getUrl());
		desc.setPermName(src.getPermName());
		desc.setVisible(src.isVisible());
		desc.setParams(src.getParams());
		
		MenuPageSource mis = src.getPageSource();
		if (mis != null){
			String miscode = src.getPageSource().encode();
			desc.setPageSource(DefaultPageSource.decode(miscode));
		}

		// 复制子菜单
		for (SiteMenuItem item : src.getSubMenuList()){
			copyMenuItem (desc.addSubMenu(item.getType()), item);
		}
	}

	@Override
	public void updatePermNames() {
		
		// 转换成列表，先把没有权限名的全填上
		List<SiteMenuItem> menus = asList(true);
		
		for (SiteMenuItem item : menus){
			if (StringKit.isEmpty(item.getPermName())){
				item.setPermName(makeNewPermissionName (menus, item.getName()));
			}
		}
		
		// 再对权限名进行排序，然后找到相同的来，改名
		while (true){
			Collections.sort(menus, new Comparator<SiteMenuItem>(){
				public int compare(SiteMenuItem o1, SiteMenuItem o2) {
					String file1 = o1.getPermName();
					String file2 = o2.getPermName();
					return file1.compareTo(file2);
				}
			});
			
			boolean hasModifyName = false;
			for (int i = 0; i < menus.size() - 1; i ++){
				SiteMenuItem item1 = menus.get(i);
				SiteMenuItem item2 = menus.get(i + 1);
				
				if (item1.getPermName().equals(item2.getPermName())){
					
					// 生成一个新名称
					item2.setPermName(makeNewPermissionName (menus, item2.getName()));
					
					hasModifyName = true;
					break;
				}
			}
			
			if (hasModifyName == false)
				break;
		}
	}
	
	/**
	 * 生成一个不存在的权限名
	 * @param menus
	 * @param name
	 * @return
	 */
	private String makeNewPermissionName (List<SiteMenuItem> menus, String name){
		int index = 1;
		String newName = name;
		while (true){
			boolean findEqual = false;
			for (SiteMenuItem menu : menus){
				if (newName.equals(menu.getPermName())){
					findEqual = true;
					break;
				}
			}
			if (findEqual == false)
				break;
			
			newName = name + Integer.valueOf(index).toString();
			index ++;
		}
		return newName;
	}

	@Override
	public List<SiteMenuItem> asList(boolean includeFolder) {
		return asList(this, null, includeFolder);
	}
	
	/**
	 * 把菜单转换成列表
	 * @param list
	 * @return
	 */
	private List<SiteMenuItem> asList(SubMenuContainer cont, List<SiteMenuItem> list, boolean includeFolder){
		
		if (list == null){
			list = new ArrayList<SiteMenuItem>();
		}
		
		for (SiteMenuItem item : cont.getSubMenuList()){

			if (includeFolder || item.getType() != MenuItemType.MenuFolder){
				list.add(item);
			}

			if (item.getSubMenuCount() > 0){
				asList (item, list, includeFolder);
			}
		}
		
		return list;
	}

	@Override
	public SiteMenuBar cloneMy() {
		SiteMenuBar newMenu = new SiteMenuBarImpl (this.getName());
		newMenu.copyForm(this);
		return newMenu;
	}
}
