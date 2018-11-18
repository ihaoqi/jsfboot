package com.easyeip.jsfboot.admin.bean.type;

import com.easyeip.jsfboot.admin.bean.form.EditMenuItemForm;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public interface MenuItemRow {

	/**
	 * 取得对应的菜单
	 * @return
	 */
	SiteMenuItem getMenu();
	
	EditMenuItemForm getEdit();

	/**
	 * 取得菜单类型
	 * @return
	 */
	String getMenuType();

	int getChildCount();

	/**
	 * 取得所在模块的名称
	 * @return
	 */
	String getSrcModule();

	/**
	 * 原菜单是否还存在
	 * @return
	 */
	boolean isSourceExist();
	
	/**
	 * 取得缩进
	 * @return
	 */
	int getIndent();

	/**
	 * 是否可左移
	 * @return
	 */
	boolean isCanMoveLeft();
	void doMoveLeft();

	/**
	 * 是否可上移
	 * @return
	 */
	boolean isCanMoveTop();
	void doMoveTop();

	/**
	 * 是否可右移
	 * @return
	 */
	boolean isCanMoveRight();
	void doMoveRight();

	/**
	 * 是否可下移
	 * @return
	 */
	boolean isCanMoveBottom();
	void doMoveBottom();

	boolean doUpdateEdit();
}