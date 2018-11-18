package com.easyeip.jsfboot.admin.bean.form;

import com.easyeip.jsfboot.core.surface.SiteMenuItem;

public class EditMenuItemForm {
	
	private String name;
	private String title;
	private String icon;
	private String url;
	private boolean visible;
	private String target;
	private String params;
	
	public EditMenuItemForm(SiteMenuItem menu){
		this.title = menu.getTitle();
		this.icon = menu.getIcon();
		this.url = menu.getUrl();
		this.name = menu.getName();
		this.target = menu.isNewWindow() ? "new" : "cur";
		this.visible = menu.isVisible();
		this.params = menu.getParams();
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTarget() {
		return target;
	}

	public void setTarget(String target) {
		this.target = target;
	}
	
	public boolean getVisible(){
		return this.visible;
	}
	
	public void setVisible (boolean val){
		visible = val;
	}

	public String getParams() {
		return params;
	}

	public void setParams(String params) {
		this.params = params;
	}
}
