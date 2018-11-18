package com.easyeip.jsfboot.admin.bean.form;

import com.easyeip.jsfboot.core.registry.RegistryPath;

public class ItemAttributeForm {
	
	private RegistryPath selectPath;
	
	private boolean notName = false;
	private String oldName;
	private String name;
	private String value;
	
	public RegistryPath getSelectPath() {
		return selectPath;
	}
	public void setSelectPath(RegistryPath selectPath) {
		this.selectPath = selectPath;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public boolean isNotName() {
		return notName;
	}
	public void setNotName(boolean notName) {
		this.notName = notName;
	}
	public String getOldName() {
		return oldName;
	}
	public void setOldName(String oldName) {
		this.oldName = oldName;
		setName (oldName);
	}

}
