package com.easyeip.jsfboot.admin.dictionary.type;

import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.KeyValuePair;

public interface DictDataTable extends SimpleDataTable {
	
	/**
	 * 把指定列转换成列表
	 * @param colIndex
	 * @return
	 */
	List<String> asList(int colIndex);
	
	/**
	 * 把指定列转换成map
	 * @param keyCol
	 * @param valCol
	 * @return
	 */
	Map<String,String> asMap(int keyCol, int valCol);
	
	/**
	 * 转换成键值对列表
	 * @param i
	 * @param j
	 * @return
	 */
	List<KeyValuePair> asKVPair(int keyCol, int valCol);
	
	/**
	 * 转换成行列map
	 * @return
	 */
	List<Map<String, String>> asRecords();
}
