package com.easyeip.jsfboot.core.registry.service.sql;

import java.io.Serializable;

public class RegistryValueEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long itemId;
	private String name;
	private String value;
	
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
	public long getItemId() {
		return itemId;
	}
	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

}
