package com.easyeip.jsfboot.core.surface;

import com.easyeip.jsfboot.core.module.type.MenuPage;

/**
 * 模块菜单源描述
 * @author ihaoqi
 *
 */
public class DefaultPageSource implements MenuPageSource {

	private String moduleName;
	private String menuPath;
	private String menuTitle;
	
	public DefaultPageSource(MenuPage page) {
		if (page != null){
			moduleName = page.getModule().getModuleInfo().getModuleName().trim();
			menuPath = page.getPath().trim();
			menuTitle = page.getTitle().trim();
		}
	}

	@Override
	public String toString() {
		return encode();
	}
	
	/**
	 * 把对象编码成字符串
	 * @return
	 */
	public String encode (){
		String path = menuPath;
		return moduleName + ":" + path + ":" + menuTitle;
	}
	
	/**
	 * 把字符串还原成对象
	 * @param source
	 */
	public static MenuPageSource decode (String source){
	
		DefaultPageSource msi = null;
		String[] items = source.split(":");
		if (items.length >= 2){
			msi = new DefaultPageSource(null);
			msi.setModuleName(items[0]);
			msi.setMenuPath(items[1]);
			if (items.length == 3)
				msi.setMenuTitle(items[2]);
		}
		return msi;
	}

	@Override
	public String getModuleName() {
		return this.moduleName;
	}

	@Override
	public String getMenuPath() {
		return this.menuPath;
	}

	@Override
	public void setModuleName(String name) {
		this.moduleName = name;
	}

	@Override
	public void setMenuPath(String path) {
		this.menuPath = path;
	}

	@Override
	public String getMenuTitle() {
		return menuTitle;
	}

	@Override
	public void setMenuTitle(String title) {
		this.menuTitle = title;
	}

}
