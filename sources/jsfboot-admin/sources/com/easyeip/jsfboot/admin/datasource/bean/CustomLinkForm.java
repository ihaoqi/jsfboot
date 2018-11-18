package com.easyeip.jsfboot.admin.datasource.bean;

public class CustomLinkForm {
	private String menuName;
	private String permName;
	private String iconName;
	private String linkUrl;
	
	private String target;
	
	public CustomLinkForm(){
		target = "cur";
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

	public String getLinkUrl() {
		return linkUrl;
	}

	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
}
