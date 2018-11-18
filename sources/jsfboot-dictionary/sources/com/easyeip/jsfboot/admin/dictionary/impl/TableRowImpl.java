package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.admin.dictionary.type.TableRow;

public class TableRowImpl implements TableRow {
	
	List<String> values;

	public TableRowImpl(int size) {
		values = new ArrayList<String>();
		for (int i = 0; i < size; i ++)
			values.add("");
	}

	@Override
	public boolean setValue(int colIndex, String value) {
		values.set(colIndex, value);
		return true;
	}

	@Override
	public String getValue(int colIndex) {
		return values.get(colIndex);
	}

}
