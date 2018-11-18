package com.easyeip.jsfboot.admin.dictionary.type;

import java.util.List;

public interface SimpleDataTable {

	/**
	 * 创建称
	 * @param colCount
	 */
	int addColumn (String name, String title);
	
	/**
	 * 查找列，不存在返回-1
	 * @param name
	 * @return
	 */
	int findColumn (String name);
	
	/**
	 * 取得列数量
	 * @return
	 */
	int getColumnCount ();
	
	/**
	 * 取得列名
	 * @return
	 */
	List<TableColumn> getColumnNames ();
	
	/**
	 * 取得行数量
	 * @return
	 */
	int getRowCount ();
	
	/**
	 * 设置行数
	 * @param count
	 * @return
	 */
	int setRowCount (int count);
	
	/**
	 * 取得单元格值
	 * @param row
	 * @param col
	 * @return
	 */
	String getValue (int rowIndex, int colIndex);
	String getValue (int rowIndex, String colName);
	
	/**
	 * 设置单元格值
	 * @param row
	 * @param col
	 * @param value
	 */
	boolean setValue (int rowIndex, int colIndex, String value);
	boolean setValue (int rowIndex, String colName, String value);
	
	/**
	 * 在最后添加行
	 * @return
	 */
	int addRow ();
	
	/**
	 * 在指定位置插入行
	 * @param index
	 * @return
	 */
	int addRow (int index);
	
	/**
	 * 删除指定行
	 * @param index
	 */
	boolean deleteRow (int index);
	
	/**
	 * 删除所有行
	 */
	void deleteAllRow ();
	
	/**
	 * 清除所有行与列
	 */
	void clear();
	
	/**
	 * 复制另一个表
	 * @param src
	 * @return
	 */
	SimpleDataTable copyFrom (SimpleDataTable src);
}
