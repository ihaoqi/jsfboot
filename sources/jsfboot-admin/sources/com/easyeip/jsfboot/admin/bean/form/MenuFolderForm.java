package com.easyeip.jsfboot.admin.bean.form;

public class MenuFolderForm {
	
	private String menuName;
	private String permName;
	private String iconName;
	
	public MenuFolderForm(){
	}
	
	public String getMenuTitle() {
		return menuName;
	}
	public void setMenuTitle(String menuName) {
		this.menuName = menuName;
	}
	public String getPermName() {
		return permName;
	}
	public void setPermName(String permName) {
		this.permName = permName;
	}
	public String getIconName() {
		return iconName;
	}
	public void setIconName(String iconName) {
		this.iconName = iconName;
	}

}
