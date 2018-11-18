package com.easyeip.jsfboot.admin.dictionary.impl;

import com.easyeip.jsfboot.admin.dictionary.type.TableColumn;

public class TableColumnImpl implements TableColumn {
	
	String name;
	String title;

	public TableColumnImpl(String name, String title) {
		this.name = name;
		this.title = title;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTitle() {
		return title;
	}

}
