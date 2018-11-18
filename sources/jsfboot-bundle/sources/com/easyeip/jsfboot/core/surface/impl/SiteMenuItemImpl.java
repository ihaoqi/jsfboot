package com.easyeip.jsfboot.core.surface.impl;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.surface.MenuItemType;
import com.easyeip.jsfboot.core.surface.MenuPageSource;
import com.easyeip.jsfboot.core.surface.SiteMenuItem;
import com.easyeip.jsfboot.core.surface.SubMenuContainer;

/**
 * 菜单定义实现
 * @author ihaoqi
 *
 */
public class SiteMenuItemImpl extends SubMenuContainerImpl implements SiteMenuItem {

	private String 		name;	// 菜单名称
	private String 		title;	// 菜单标题
	private String 		icon;	// 菜单图标
	private MenuItemType type;	// 导航类型
	private String 		outcome;	// 模块内页面
	private String 		url;		// 具体url
	private boolean 	newWindow;	// 是否在新窗口中打开
	private boolean		visible;
	private String 		params;		// url参数
	
	private MenuPageSource source;	// outcome 来源，type == MenuPage 时有效
	private SubMenuContainer parent;// 上级菜单
	private String permissionName;	// 权限名称
	
	public SiteMenuItemImpl(MenuItemType type, SubMenuContainer parent){
		this.parent = parent;
		this.type = type;
		newWindow = false;
		visible = true;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		
		if (StringKit.isEmpty(name) == false){
			sb.append("(" + name + ")");
		}
		sb.append(", type:" + type.name());
		
		if (StringKit.isEmpty(outcome) == false){
			sb.append(", outcome:" + outcome);
			if (source != null)
				sb.append(", source:" + source.toString());
		}else if (StringKit.isEmpty(url) == false){
			sb.append(", url:" + url);
		}
		
		return sb.toString();
	}
	
	@Override
	public String getName() {
		return name;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public void setIcon(String icon) {
		this.icon = icon;
	}

	@Override
	public String getOutcome() {
		return outcome;
	}

	@Override
	public void setOutcome(String outcome) {
		this.outcome = outcome;
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
	public SiteMenuItem getParent() {
		if (parent instanceof SiteMenuItem)
			return (SiteMenuItem) parent;
		return null;
	}
	
	@Override
	public SubMenuContainer getContainer() {
		return parent;
	}

	@Override
	public MenuItemType getType() {
//		if (this.getSubMenuCount() > 0)
//			return MenuNavType.Folder;
		return type;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public void setUrl(String url) {
		this.url = url;
	}

	@Override
	public boolean isNewWindow() {
		return newWindow;
	}

	@Override
	public void setNewWindow(boolean newWindow) {
		this.newWindow = newWindow;
	}

	@Override
	public MenuPageSource getPageSource() {
		return source;
	}

	@Override
	public void setPageSource(MenuPageSource source) {
		this.source = source;
	}

	@Override
	public String getPermName() {
		return permissionName;
	}

	@Override
	public void setPermName(String name) {
		this.permissionName = name;
	}

	@Override
	public void setContainer(SubMenuContainer cont) {
		this.parent = cont;
	}

	@Override
	public boolean isVisible() {
		return visible;
	}

	@Override
	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	@Override
	public String getParams() {
		return params;
	}

	@Override
	public void setParams(String param) {
		this.params = param;
	}
}
