package com.easyeip.jsfboot.core.registry.service.sql;

public class ValueUpdateData {
	
	private ValueUpdateType type;
	private String value;
	private RegistryValueEntry entry;
	
	public ValueUpdateData(ValueUpdateType type, String value, RegistryValueEntry entry){
		this.type = type;
		this.value = value;
		this.entry = entry;
	}
	
	public ValueUpdateType getType() {
		return type;
	}

	public String getValue() {
		return value;
	}

	public RegistryValueEntry getEntry() {
		return entry;
	}
}
