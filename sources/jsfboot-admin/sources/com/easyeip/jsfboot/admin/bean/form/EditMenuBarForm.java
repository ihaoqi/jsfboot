package com.easyeip.jsfboot.admin.bean.form;

import java.util.List;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.admin.bean.type.MenuItemRow;
import com.easyeip.jsfboot.admin.bean.type.MenuTreeInfo;
import com.easyeip.jsfboot.admin.datasource.bean.CustomLinkForm;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public interface EditMenuBarForm {

	SiteMenuBar getRaw();

	/**
	 * 设置菜单新的名称
	 * @return
	 */
	String getName();
	void setName(String name);
	
	/**
	 * 设置菜单新的标题
	 * @return
	 */
	String getTitle();
	void setTitle(String title);
	
	/**
	 * 设置新菜单的添加位置：child或next
	 * @return
	 */
	String getNewMenuPosition();
	void setNewMenuPosition(String pos);

	/**
	 * 更新当前修改
	 * @param menuBar
	 */
	void update(SiteMenuBar menuBar);
	void update(SiteMenuItem selItem);

	/**
	 * 取得菜单列表
	 * @return
	 */
	List<MenuItemRow> getRows();

	/**
	 * 取得当前选中行
	 * @return
	 */
	MenuItemRow getSelectedRow();
	void setSelectedRow(MenuItemRow selectedRow);

	/**
	 * 添加菜单到当前选中的行
	 * @param menuInfo
	 */
	SiteMenuItem addMenu2CurRow(MenuTreeInfo menuInfo, int index);
	SiteMenuItem addFolder2CurRow(MenuFolderForm form);
	SiteMenuItem addLink2CurRow(CustomLinkForm form);

	// 移动行
	void doRowMoveLeft(ActionEvent event);
	void doRowMoveTop(ActionEvent event);
	void doRowMoveRight(ActionEvent event);
	void doRowMoveBottom(ActionEvent event);
	void doRowDelete(ActionEvent event);
	
	// 更新行
	MenuItemRow getEditRow();
	void beginRowEdit(ActionEvent event);
	void doRowUpdate(ActionEvent event);
}