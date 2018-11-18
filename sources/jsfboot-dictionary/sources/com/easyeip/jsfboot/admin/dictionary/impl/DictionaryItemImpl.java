package com.easyeip.jsfboot.admin.dictionary.impl;

import com.easyeip.jsfboot.admin.dictionary.type.DictDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;
import com.easyeip.jsfboot.admin.dictionary.type.ListDictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.MapDictionaryItem;

public class DictionaryItemImpl implements DictionaryItem{
	
	String code;
	DictionaryType type;
	String title;
	String catalog;
	DictDataTable data;
	
	public DictionaryItemImpl(String code){
		this.code = code;
		this.type = DictionaryType.Table;
		data = new DictDataTableImpl ();
	}
	
	public DictionaryItemImpl(String code, DictionaryType type){
		this.code = code;
		this.type = type;
		data = new DictDataTableImpl ();
	}
	
	@Override
	public DictionaryType getType() {
		return type;
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
	public void setTitle(String title) {
		this.title = title;
	}

	@Override
	public DictDataTable getTable() {
		return data;
	}

	@Override
	public int getSize() {
		return data.getRowCount();
	}

	@Override
	public ListDictionaryItem asList() {
		if (this instanceof ListDictionaryItem)
			return (ListDictionaryItem) this;
		return null;
	}

	@Override
	public MapDictionaryItem asMap() {
		if (this instanceof MapDictionaryItem)
			return (MapDictionaryItem) this;
		return null;
	}

	@Override
	public DictionaryItem copyFrom(DictionaryItem src) {
		this.code = src.getCode();
		this.title = src.getTitle();
		this.catalog = src.getCatalog();
		data.copyFrom(src.getTable());
		return this;
	}
	
	@Override
	public DictionaryItem convertType(DictionaryType type) {
		DictionaryItem newItem = null;
		if (type == DictionaryType.Table)
			newItem = new DictionaryItemImpl(this.getCode());
		else if (type == DictionaryType.List)
			newItem = new ListDictionaryItemImpl(this.getCode());
		else if (type == DictionaryType.Map)
			newItem = new MapDictionaryItemImpl(this.getCode());
		
		newItem.copyFrom(this);
		
		return newItem;
	}

	@Override
	public String getCatalog() {
		return catalog;
	}

	@Override
	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
