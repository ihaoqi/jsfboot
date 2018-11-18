package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.admin.dictionary.type.SimpleDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.TableColumn;
import com.easyeip.jsfboot.admin.dictionary.type.TableRow;

public class SimpleDataTableImpl implements SimpleDataTable {
	
	private List<TableColumn> columns;
	private List<TableRow> rows;
	
	public SimpleDataTableImpl(){
		columns = new ArrayList<TableColumn>();
		rows = new ArrayList<TableRow>();
	}

	@Override
	public int addColumn(String name, String title) {
		// 有了行后就不能添加了
		if (rows.size() > 0)
			return -1;
		
		columns.add(new TableColumnImpl(name, title));
		return columns.size() - 1;
	}
	
	@Override
	public int findColumn(String name) {
		for (int i = 0; i < columns.size(); i ++){
			if (columns.get(i).getName().equals(name))
				return i;
		}
		
		return -1;
	}

	@Override
	public boolean setValue(int rowIndex, int colIndex, String value) {
		if (rowIndex < 0 || rowIndex >= rows.size())
			return false;
		
		if (colIndex < 0 || colIndex >= columns.size())
			return false;
		
		TableRow row = rows.get(rowIndex);
		return row.setValue(colIndex, value);
	}

	@Override
	public boolean setValue(int rowIndex, String colName, String value) {
		return setValue (rowIndex, findColumn (colName), value);
	}

	@Override
	public int addRow() {
		return addRow (rows.size());
	}

	@Override
	public int addRow(int index) {
		if (columns.size() == 0)
			return -1;
		
		if (index < 0 || index > rows.size())
			return -1;
		
		rows.add(index, new TableRowImpl (columns.size()));
		
		return index;
	}
	
	@Override
	public int setRowCount(int count) {
		
		if (count > rows.size()){
			// 添加行到后面
			int rowCount = count - rows.size();
			for (int i = 0; i < rowCount; i ++){
				addRow();
			}
		}else if (count > rows.size()){
			// 删除后面多的行
			int rowCount = rows.size() - count;
			for (int i = 0; i < rowCount; i ++){
				deleteRow (rows.size() - 1);
			}
		}
		
		return rows.size();
	}

	@Override
	public boolean deleteRow(int index) {
		if (index < 0 || index >= rows.size())
			return false;
		
		rows.remove(index);
		return true;
	}

	@Override
	public void deleteAllRow() {
		rows.clear();
	}

	@Override
	public int getColumnCount() {
		return columns.size();
	}

	@Override
	public List<TableColumn> getColumnNames() {
		return Collections.unmodifiableList(columns);
	}

	@Override
	public int getRowCount() {
		return rows.size();
	}

	@Override
	public String getValue(int rowIndex, int colIndex) {
		if (rowIndex < 0 || rowIndex >= rows.size())
			return null;
		
		if (colIndex < 0 || colIndex >= columns.size())
			return null;
		
		TableRow row = rows.get(rowIndex);
		return row.getValue(colIndex);
	}

	@Override
	public String getValue(int rowIndex, String colName) {
		return getValue (rowIndex, findColumn (colName));
	}

	@Override
	public void clear() {
		rows.clear();
		columns.clear();
	}

	@Override
	public SimpleDataTable copyFrom(SimpleDataTable src) {
		this.clear();
		
		// 复制列
		for (TableColumn col : src.getColumnNames()){
			this.addColumn(col.getName(), col.getTitle());
		}
		
		// 复制行
		for (int i = 0; i < src.getRowCount(); i ++){
			int idx = this.addRow();
			for (int col = 0; col < src.getColumnCount(); col ++){
				this.setValue(idx, col, src.getValue(i, col));
			}
		}
		
		return this;
	}
}
