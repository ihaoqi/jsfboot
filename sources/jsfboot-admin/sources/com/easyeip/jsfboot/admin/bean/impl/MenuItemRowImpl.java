package com.easyeip.jsfboot.admin.bean.impl;

import com.easyeip.jsfboot.admin.bean.form.EditMenuItemForm;
import com.easyeip.jsfboot.admin.bean.type.MenuItemRow;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.MenuPageUtils;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;

public class MenuItemRowImpl implements MenuItemRow {	
	private SiteMenuItem menu;
	private int indent = -1;
	private String moduleTitle = null;
	private int index = -1;
	private EditMenuItemForm editForm;
	private boolean sourceExist = true;
	
	public MenuItemRowImpl(SiteMenuItem menu, int index){
		this.menu = menu;
		this.index = index;
		editForm = new EditMenuItemForm(menu);
	}

	@Override
	public SiteMenuItem getMenu(){
		return this.menu;
	}

	@Override
	public String getMenuType(){
		return menu.getType().name();
	}

	@Override
	public int getChildCount(){
		return menu.getSubMenuCount();
	}

	@Override
	public String getSrcModule(){
		sourceExist = true;
		if (moduleTitle == null){
			moduleTitle = "";
			if (menu.getPageSource() != null){
				MenuPage srcPage = MenuPageUtils.findMenuPageBySource(menu.getPageSource(), false, false);
				sourceExist = srcPage != null;
				if (srcPage != null){
					moduleTitle = srcPage.getModule().getModuleInfo().getModuleTitle();
				}
			}
		}
		return moduleTitle;
	}
	
	@Override
	public boolean isSourceExist() {
		return sourceExist;
	}
	
	@Override
	public int getIndent(){
		
		if (indent == -1){
			SiteMenuItem next = menu;
			while (next != null){
				next = next.getParent();
				indent ++;
			}
		}
		
		return indent;
		
	}

	@Override
	public boolean isCanMoveLeft(){
		return menu.getParent() != null;
	}

	@Override
	public boolean isCanMoveTop(){
		return index > 0;
	}

	@Override
	public boolean isCanMoveRight(){
		// 要求上一级点是目录
		return (menu.getContainer() != null && index > 0 && 
				menu.getContainer().getSubMenu(index-1).getType() == MenuItemType.MenuFolder);
	}

	@Override
	public boolean isCanMoveBottom(){
		// 要下下面还有结点
		return menu.getContainer() != null && menu.getContainer().getSubMenuCount() > index + 1;
		
	}

	/**
	 * 移到父菜单的下面
	 */
	@Override
	public void doMoveLeft() {
		if (!isCanMoveLeft()) return;
		
		// 查找到父菜单的位置
		SiteMenuItem parent = menu.getParent();
		int parentIndex = parent.getContainer().getSubMenuList().indexOf(parent);
		
		// 把自身从父菜单中移除
		int menuIndex = parent.getSubMenuList().indexOf(menu);
		parent.removeSubMenu(menuIndex);
		
		// 再重新插入到父菜单下面
		parent.getContainer().insertSubMenu(menu, parentIndex + 1);
	}

	@Override
	public void doMoveTop() {
		if (!isCanMoveTop()) return;
		SubMenuContainer cont = menu.getContainer();
		int menuIndex = menu.getContainer().getSubMenuList().indexOf(menu);
		cont.removeSubMenu(menuIndex);
		cont.insertSubMenu(menu, menuIndex - 1);
	}

	@Override
	public void doMoveRight() {
		if (!isCanMoveRight()) return;
		
		// 找到上一个菜单
		SubMenuContainer parent = menu.getContainer();
		int menuIndex = menu.getContainer().getSubMenuList().indexOf(menu);
		SiteMenuItem prevMenu = parent.getSubMenu(menuIndex - 1);
		
		if (prevMenu.getType() == MenuItemType.MenuFolder){
			parent.removeSubMenu(menuIndex);
			prevMenu.insertSubMenu(menu, prevMenu.getSubMenuCount());
		}
	}

	@Override
	public void doMoveBottom() {
		if (!isCanMoveBottom()) return;
		SubMenuContainer cont = menu.getContainer();
		int menuIndex = menu.getContainer().getSubMenuList().indexOf(menu);
		cont.removeSubMenu(menuIndex);
		cont.insertSubMenu(menu, menuIndex + 1);
	}

	@Override
	public EditMenuItemForm getEdit() {
		return editForm;
	}

	@Override
	public boolean doUpdateEdit() {
		
		if (StringKit.isEmpty(editForm.getTitle().trim())){
			FacesUtils.addMessageError("更新菜单", "菜单标题不能为空。");
			editForm = new EditMenuItemForm(menu);
			return false;
		}
		
		String newName = editForm.getName().trim();
		if (StringKit.isEmpty(newName)){
			FacesUtils.addMessageError("更新菜单", "菜单名称不能为空。");
			editForm = new EditMenuItemForm(menu);
			return false;
		}
		
		if (NameUtils.isXMLName(newName) == false){
			FacesUtils.addMessageError("更新菜单", newName + " 不符合名称规范。");
			editForm = new EditMenuItemForm(menu);
			return false;
		}
		
		// 更新链接
		if (menu.getType() == MenuItemType.MenuLink){
			if (StringKit.isEmpty(editForm.getUrl().trim())){
				FacesUtils.addMessageError("更新菜单", "链接地址不能为空。");
				editForm = new EditMenuItemForm(menu);
				return false;
			}
			
			menu.setUrl(editForm.getUrl().trim());
			menu.setNewWindow(editForm.getTarget().equals("new"));
		}
				
		// 更新名称
		if (newName.equals(menu.getName()) == false){
			menu.setPermName(null);
			menu.setName(newName);
		}
		
		// 更新其他参数
		menu.setTitle(StringKit.trim(editForm.getTitle()));
		menu.setIcon(StringKit.trim(editForm.getIcon()));
		menu.setParams(StringKit.trim(editForm.getParams()));
		menu.setVisible(editForm.getVisible());
		
		//FacesUtils.addMessageInfo("更新菜单", menu.getTitle() + " 更新成功。");
		
		return true;
	}
}
