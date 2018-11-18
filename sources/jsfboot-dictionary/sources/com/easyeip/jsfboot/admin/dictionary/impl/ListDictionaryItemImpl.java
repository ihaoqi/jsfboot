package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.List;

import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;
import com.easyeip.jsfboot.admin.dictionary.type.ListDictionaryItem;

public class ListDictionaryItemImpl extends DictionaryItemImpl implements ListDictionaryItem{

	public ListDictionaryItemImpl(String code) {
		super(code, DictionaryType.List);
	}

	@Override
	public List<String> getList() {
		return getTable().asList(0);
	}

	@Override
	public void setList(List<String> list) {
		getTable().deleteAllRow();
		if (getTable().getColumnCount() < 1)
			return;
		
		getTable().setRowCount(list.size());
		for (int i = 0; i < list.size(); i ++){
			getTable().setValue(i, 0, list.get(i));
		}
	}

}
