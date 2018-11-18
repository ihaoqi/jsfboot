package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.admin.dictionary.type.DictDataTable;
import com.easyeip.jsfboot.utils.KeyValuePair;

/**
 * 基于内存的简单数据表对象
 * @author ihaoqi
 *
 */
public class DictDataTableImpl extends SimpleDataTableImpl implements DictDataTable {

	@Override
	public List<String> asList(int colIndex) {
		List<String> list = new ArrayList<String>();
		if (!checkColIndex(colIndex))
			return list;
		
		for (int i = 0; i < getRowCount(); i ++){
			list.add(getValue(i, colIndex));
		}
		
		return list;
	}

	@Override
	public Map<String, String> asMap(int keyCol, int valCol) {
		Map<String,String> ret = new HashMap<String,String>();
		if (!checkColIndex(keyCol) || !checkColIndex(valCol))
			return ret;
		
		for (int i = 0; i < getRowCount(); i ++){
			String key = getValue(i, keyCol);
			// 有重复时保留第一个
			if (ret.get(key) != null)
				continue;
			
			String value = getValue(i, valCol);
			ret.put(key, value);
		}
		
		return ret;
	}
	
	@Override
	public List<KeyValuePair> asKVPair(int keyCol, int valCol) {
		List<KeyValuePair> list = new ArrayList<KeyValuePair>();
		if (!checkColIndex(keyCol) || !checkColIndex(valCol))
			return list;
		
		Map<String,String> map = new HashMap<String,String>();
		
		for (int i = 0; i < getRowCount(); i ++){
			String key = getValue(i, keyCol);
			
			// 有重复时保留第一个
			if (map.get(key) != null)
				continue;
			
			String value = getValue(i, valCol);
			list.add(new KeyValuePair(key, value));
			map.put(key, value);
		}
		return list;
	}
	
	/**
	 * 判断列索引是否有效
	 * @param index
	 * @return
	 */
	boolean checkColIndex(int index){
		return index >= 0 && index < getColumnCount();
	}

	@Override
	public List<Map<String, String>> asRecords() {
		List<Map<String, String>> rows = new ArrayList<Map<String, String>>();
		
		for (int row = 0; row < getRowCount(); row ++){
			
			Map<String, String> colValues = new HashMap<String, String>();
			
			for (int col = 0; col < getColumnCount(); col ++){
				String colName = getColumnNames().get(col).getName();
				colValues.put(colName, getValue(row, col));
			}
			
			rows.add(colValues);
		}
		
		return rows;
	}
}
