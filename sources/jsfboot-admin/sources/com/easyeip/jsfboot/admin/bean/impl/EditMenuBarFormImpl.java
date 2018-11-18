package com.easyeip.jsfboot.admin.bean.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.admin.bean.form.EditMenuBarForm;
import com.easyeip.jsfboot.admin.bean.form.MenuFolderForm;
import com.easyeip.jsfboot.admin.bean.type.MenuItemRow;
import com.easyeip.jsfboot.admin.bean.type.MenuTreeInfo;
import com.easyeip.jsfboot.admin.datasource.bean.CustomLinkForm;
import com.easyeip.jsfboot.core.module.type.MenuPage;
import com.easyeip.jsfboot.core.surface.DefaultPageSource;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;
import com.easyeip.jsfboot.utils.FacesUtils;

/**
 * 当前正在编辑的菜单条form
 * @author ihaoqi
 *
 */
public class EditMenuBarFormImpl implements EditMenuBarForm {
	
	private SiteMenuBar menubar;
	private String name;
	private String title;
	private MenuItemRow selectedRow;
	private MenuItemRow editRow;
	
	private String newMenuPos = "child";
	
	private List<MenuItemRow> rowList;
	
	public EditMenuBarFormImpl(){
		rowList = new ArrayList<MenuItemRow>();
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#getRaw()
	 */
	@Override
	public SiteMenuBar getRaw() {
		return menubar;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#setName(java.lang.String)
	 */
	@Override
	public void setName(String name) {
		this.name = name;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#update(com.easyeip.jsfboot.core.surface.SiteMenuBar)
	 */
	@Override
	public void update(SiteMenuBar menuBar) {
		this.menubar = menuBar;
		this.name = "";
		this.title = "";
		if (menuBar != null){
			name = menuBar.getName();
			title = menuBar.getTitle();
		}
		updateRowModel (null);
	}
	
	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#update()
	 */
	@Override
	public void update(SiteMenuItem selItem) {
		updateRowModel (selItem);
	}
	
	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#getRows()
	 */
	@Override
	public List<MenuItemRow> getRows(){
		return rowList;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#getSelectedRow()
	 */
	@Override
	public MenuItemRow getSelectedRow() {
		return selectedRow;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#setSelectedRow(com.easyeip.jsfboot.admin.type.MenuItemRow)
	 */
	@Override
	public void setSelectedRow(MenuItemRow selectedRow) {
		this.selectedRow = selectedRow;
	}

	/* (non-Javadoc)
	 * @see com.easyeip.jsfboot.admin.type.EditMenuBarForm#addMenu2CurRow(com.easyeip.jsfboot.admin.type.MenuTreeInfo, boolean)
	 */
	@Override
	public SiteMenuItem addMenu2CurRow(MenuTreeInfo menuInfo, int index) {
		
		// 如果 selectedRow == null表示添加到根结点
		MenuPage srcMenu = menuInfo.getSrcMenu();
		if (srcMenu == null)
			return null;
		
		boolean addChild = newMenuPos.equals("child");
		
		// 把 menuList 中的添加到菜单中
		SiteMenuItem newItem = null;
		MenuItemType menuType = getMenuPageNavType (srcMenu);
		if (selectedRow != null){
			newItem = addSubMenu (selectedRow.getMenu(), menuType, addChild, index);
		}else{
			newItem = menubar.addSubMenu(menuType);
		}
		if (newItem != null){
			copyMenuPage2MenuItem (srcMenu, newItem);
		}

		// 更新权限名称
		menubar.updatePermNames();
		
//		// 更新行
//		updateRowModel();
		
		return newItem;
	}
	
	@Override
	public SiteMenuItem addFolder2CurRow(MenuFolderForm form) {
		SiteMenuItem newItem = null;
		if (selectedRow != null){
			newItem = addSubMenu (selectedRow.getMenu(), MenuItemType.MenuFolder, true, 0);
		}else{
			newItem = menubar.addSubMenu(MenuItemType.MenuFolder);
		}
		
		// 复制内容
		newItem.setName(form.getPermName());
		newItem.setTitle(form.getMenuTitle());
		newItem.setIcon(form.getIconName());
		
		// 更新权限名称
		menubar.updatePermNames();
		
		return newItem;
	}

	@Override
	public SiteMenuItem addLink2CurRow(CustomLinkForm form) {
		SiteMenuItem newItem = null;
		if (selectedRow != null){
			newItem = addSubMenu (selectedRow.getMenu(), MenuItemType.MenuLink, true, 0);
		}else{
			newItem = menubar.addSubMenu(MenuItemType.MenuLink);
		}
		
		// 复制内容
		newItem.setName(form.getPermName());
		newItem.setTitle(form.getMenuTitle());
		newItem.setIcon(form.getIconName());
		newItem.setUrl(form.getLinkUrl());
		newItem.setNewWindow(!form.getTarget().equals("cur"));
		
		// 更新权限名称
		menubar.updatePermNames();
		
		return newItem;
	}
	
	private void copyMenuPage2MenuItem (MenuPage page, SiteMenuItem item){
		
		item.setIcon(page.getIcon());
		item.setName(page.getName());
		item.setTitle(page.getTitle());
		// 设置页面导航路径
		if (item.getType() == MenuItemType.MenuPage){
			item.setPageSource(new DefaultPageSource(page));
			item.setOutcome(page.getOutcome());
		}
		item.setNewWindow(false);
		
		for (MenuPage child : page.getSubMenu()){
			
			copyMenuPage2MenuItem (child, item.addSubMenu(getMenuPageNavType(child)));
			
		}

	}
	
	private MenuItemType getMenuPageNavType (MenuPage page){
		
		if (page.getSubMenu().size() > 0)
			return MenuItemType.MenuFolder;
		return MenuItemType.MenuPage;
	}
	
	private SiteMenuItem addSubMenu (SiteMenuItem item, MenuItemType type, boolean addChild, int index){
		if (addChild && selectedRow != null){
			
			// 结点必须是目录，否则找上级
			SiteMenuItem owner = selectedRow.getMenu();
			if (selectedRow.getMenu().getType() != MenuItemType.MenuFolder){
				// 添加到当前菜单后面
				return addSubMenu(item, type, false, index);
			}
			
			// 添加子菜单
			return owner.addSubMenu(type);
		}
		
		// 查询item的索引
		SubMenuContainer parent = menubar;
		if (item.getParent() != null)
			parent = item.getParent();
		
		int findIndex = -1;
		for (int i = 0; i < parent.getSubMenuCount(); i ++){
			if (parent.getSubMenu(i) == item){
				findIndex = i;
				break;
			}
		}
		
		// 插入到后后面
		if (findIndex >= 0){
			
			return parent.insertSubMenu(type, findIndex + index + 1);
			
		}
		
		return null;
	}
	
	/**
	 * 更新行模型
	 */
	private void updateRowModel(SiteMenuItem selItem){
		
		rowList.clear();
		SiteMenuItem oldSelectItem = selItem != null ? selItem : (selectedRow==null?null:selectedRow.getMenu());
		selectedRow = null;
		
		if (menubar != null){
			List<SiteMenuItem> menuList = menubar.asList(true);
			for (int i = 0; i < menuList.size(); i ++){
				SiteMenuItem item = menuList.get(i);
				MenuItemRowImpl row = new MenuItemRowImpl(item, item.getContainer().getSubMenuList().indexOf(item));
				rowList.add(row);
				if (oldSelectItem == item)
					selectedRow = row;
			}
		}
		
	}

	@Override
	public String getNewMenuPosition() {
		return newMenuPos;
	}

	@Override
	public void setNewMenuPosition(String pos) {
		newMenuPos = pos;
	}

	@Override
	public void doRowMoveLeft(ActionEvent event) {
		MenuItemRow row = (MenuItemRow) event.getComponent().getAttributes().get("row");
		row.doMoveLeft();
		this.update(row.getMenu());
	}

	@Override
	public void doRowMoveTop(ActionEvent event) {
		MenuItemRow row = (MenuItemRow) event.getComponent().getAttributes().get("row");
		row.doMoveTop();
		this.update(row.getMenu());
	}

	@Override
	public void doRowMoveRight(ActionEvent event) {
		MenuItemRow row = (MenuItemRow) event.getComponent().getAttributes().get("row");
		row.doMoveRight();
		this.update(row.getMenu());
	}

	@Override
	public void doRowMoveBottom(ActionEvent event) {
		MenuItemRow row = (MenuItemRow) event.getComponent().getAttributes().get("row");
		row.doMoveBottom();
		this.update(row.getMenu());
	}

	@Override
	public void doRowDelete(ActionEvent event) {
		MenuItemRow row = (MenuItemRow) event.getComponent().getAttributes().get("row");
		// 移除菜单行
		int index = row.getMenu().getContainer().getSubMenuList().indexOf(row.getMenu());
		row.getMenu().getContainer().removeSubMenu(index);
		// 更新选中菜单为空
		this.update((SiteMenuItem) null);
	}
	
	@Override
	public MenuItemRow getEditRow(){
		return editRow;
	}
	
	@Override
	public void beginRowEdit(ActionEvent event){
		// 选中编辑行
		editRow = (MenuItemRow) event.getComponent().getAttributes().get("row");
		this.update(editRow.getMenu());
		FacesUtils.clearInputInvalidState();
	}

	@Override
	public void doRowUpdate(ActionEvent event) {
		if (editRow == null)
			return;
		
		if (editRow.doUpdateEdit()){
			// 更新权限名称
			if (editRow.getMenu().getPermName() == null){
				menubar.updatePermNames();
			}
			
			this.update(editRow.getMenu());
			FacesUtils.setAllowCloseDialog(true);
		}
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
}
