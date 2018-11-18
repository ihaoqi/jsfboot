package com.easyeip.jsfboot.admin.dictionary.form;

import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;

public class AddDictionaryForm {
	
	private String code;
	private String title;
	private String catalog;
	private String type = DictionaryType.Map.name();
	
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getCatalog() {
		return catalog;
	}
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}

}
