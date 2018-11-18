package com.easyeip.jsfboot.admin.dictionary.form;

import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.SimpleDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.TableColumn;
import com.easyeip.jsfboot.utils.KeyValuePair;
import com.easyeip.jsfboot.utils.StringKit;

public class EditDictionaryForm {
	
	private DictionaryItem selItem;
	
	private String code;
	private String title;
	private String catalog;
	private String content;
	
	public EditDictionaryForm(DictionaryItem selItem){
		this.selItem = selItem;
		code = selItem.getCode();
		title = selItem.getTitle();
		catalog = selItem.getCatalog();
		content = table2Text (selItem.getTable());
	}

	public DictionaryItem getSelItem() {
		return selItem;
	}
	
	public String getType(){
		return selItem.getType().name();
	}

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
	
	/**
	 * 取得表格的字段名
	 * @return
	 */
	public List<TableColumn> getTableFields(){
		return selItem.getTable().getColumnNames();
	}
	
	/**
	 * 取得表格的字段值
	 * @return
	 */
	public List<Map<String,String>> getTableRecords(){
		return selItem.getTable().asRecords();
	}
	
	/**
	 * 取得单列表的值
	 * @return
	 */
	public List<String> getListValue(){
		return selItem.asList().getList();
	}
	
	/**
	 * 取得Map的值
	 * @return
	 */
	public List<KeyValuePair> getMapValue(){
		return selItem.asMap().getKVPair();
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
	
	public String table2Text (SimpleDataTable table){
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < table.getRowCount(); i ++){
			String line = "";
			for (int j = 0; j < table.getColumnCount(); j ++){
				String val = table.getValue(i, j);
				if (j > 0)
					line += "," + val;
				else
					line += val;
			}
			if (i > 0) sb.append("\r\n");
			sb.append(line);
		}
		
		return sb.toString();
	}
	
	public void text2Table(String text, SimpleDataTable table) {
		
		List<String> lines = StringKit.readCrlfLines(text);
        
        // 再把行转换成列值
        for (String line : lines){
        	
        	if (StringKit.isEmpty(line))
        		continue;
        	
        	int rowIndex = table.addRow();
        	line = line.replace("，", ",");
        	
        	// 分析列
        	String[] cellValues = line.split(",");
        	int colIndex = 0;
        	for (String val : cellValues){
        		if (colIndex >= table.getColumnCount())
        			break;

        		table.setValue(rowIndex, colIndex, val.trim());
        		colIndex ++;
        	}
        }
	}

	public String getCatalog() {
		return catalog;
	}

	public void setCatalog(String catalog) {
		this.catalog = catalog;
	}
}
