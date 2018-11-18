package com.easyeip.jsfboot.core.registry.service.sql;

import java.io.Serializable;

public class RegistryItemEntry implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private long itemId;
	private long sortNum;
	private long itemLevel;
	private String idChain;
	private String itemPath;
	private String itemName;
	private String comment;
	private String defaultValue;
	
	public RegistryItemEntry(){
		itemId = 0;
	}

	public long getItemId() {
		return itemId;
	}

	public void setItemId(long itemId) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	public String getComment() {
		return comment;
	}

	public void setComment(String comment) {
		this.comment = comment;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public void setDefaultValue(String itemValue) {
		this.defaultValue = itemValue;
	}

	public long getSortNum() {
		return sortNum;
	}

	public void setSortNum(long sortNum) {
		this.sortNum = sortNum;
	}

	public long getItemLevel() {
		return itemLevel;
	}

	public void setItemLevel(long itemLevel) {
		this.itemLevel = itemLevel;
	}

	public String getIdChain() {
		return idChain;
	}

	public void setIdChain(String idChain) {
		this.idChain = idChain;
	}

	public String getItemPath() {
		return itemPath;
	}

	public void setItemPath(String itemPath) {
		this.itemPath = itemPath;
	}
}
