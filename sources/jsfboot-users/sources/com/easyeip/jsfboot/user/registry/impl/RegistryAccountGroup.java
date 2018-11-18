package com.easyeip.jsfboot.user.registry.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.user.type.AccountGroup;
import com.easyeip.jsfboot.utils.StringKit;

public class RegistryAccountGroup implements AccountGroup {

	private static List<AccountGroup> emptyList = new ArrayList<AccountGroup>();
	
	RegistryItem regItem;
	
	private String id;
	private String code;
	private String title;
	private String explain;
	private boolean enabled;
	
	public RegistryAccountGroup(){
		regItem = null;
		id = null;
		enabled = true;
	}
	
	public RegistryAccountGroup(RegistryItem regItem) {
		this.regItem = regItem;
		
		this.id = regItem.getName();
		this.code = regItem.getName();
		this.title = regItem.getDefaultValue();
		this.explain = regItem.getValue("explain");
		this.enabled = StringKit.isTrue(regItem.getValue("enabled"), true);
	}
	
	public RegistryItem getRegistryItem() {
		return regItem;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public AccountGroup getParent() {
		return null;
	}

	@Override
	public List<AccountGroup> getChildren() {
		return emptyList;
	}

	@Override
	public String getExplain() {
		return explain;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}
	
	public void addChild (AccountGroup child){
		
	}
	
	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public void setExplain(String explain) {
		this.explain = explain;
	}
	
	@Override
	public void setEnabled (boolean enabled){
		this.enabled = enabled;
	}
	
	public static void save(RegistryService registry,RegistryItem regItem,
						AccountGroup group) throws RegistryException{
		
		regItem.setDefaultValue(group.getTitle());
		regItem.setValue("explain", group.getExplain());
		regItem.setValue("enabled", Boolean.valueOf(group.isEnabled()).toString());
		registry.updateItem(regItem);
	}
}
