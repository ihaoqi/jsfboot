package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.admin.dictionary.type.DictDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;
import com.easyeip.jsfboot.admin.dictionary.type.MapDictionaryItem;
import com.easyeip.jsfboot.utils.KeyValuePair;

public class MapDictionaryItemImpl extends DictionaryItemImpl implements MapDictionaryItem{

	public MapDictionaryItemImpl(String code) {
		super(code, DictionaryType.Map);
	}

	@Override
	public Map<String, String> getMap() {
		return getTable().asMap(0, 1);
	}

	@Override
	public void setMap(Map<String, String> map) {
		
		DictDataTable table = getTable();
		
		table.deleteAllRow();
		if (table.getColumnCount() < 1)
			return;
		
		table.setRowCount(map.size());
		int row = 0;
		for (Entry<String,String> entry : map.entrySet()){
			table.setValue(row, 0, entry.getKey());
			if (table.getColumnCount() > 1){
				table.setValue(row, 1, entry.getValue());
			}
			row ++;
		}
	}

	@Override
	public List<KeyValuePair> getKVPair() {
		return getTable().asKVPair(0, 1);
	}

	@Override
	public void setKVPair(List<KeyValuePair> list) {
		DictDataTable table = getTable();
		
		table.deleteAllRow();
		if (table.getColumnCount() < 1)
			return;
		
		table.setRowCount(list.size());
		for (int row = 0; row < list.size(); row ++){
			KeyValuePair entry = list.get(row);
			table.setValue(row, 0, entry.getKey());
			if (table.getColumnCount() > 1){
				table.setValue(row, 1, entry.getValue());
			}
		}
	}

}
