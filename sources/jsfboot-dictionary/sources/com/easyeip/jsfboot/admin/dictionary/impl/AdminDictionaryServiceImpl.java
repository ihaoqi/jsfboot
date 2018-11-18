package com.easyeip.jsfboot.admin.dictionary.impl;

import java.util.Iterator;

import com.easyeip.jsfboot.admin.AdminHelpViewService;
import com.easyeip.jsfboot.admin.dictionary.AdminDictionaryService;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryException;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;
import com.easyeip.jsfboot.admin.dictionary.type.SimpleDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.TableColumn;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.PageDomainType;

/**
 * jsfboot管理服务
 * @author ihaoqi
 *
 */
public class AdminDictionaryServiceImpl extends GenericService
								implements AdminDictionaryService {
	
	private static final String ADMINDICT_REGPATH = "/jsfboot/module/jsfboot-admin/dictionary";
	private static final String ROW_PREFIX = "row_";
		
	@UseJsfbootService(RegistryService.class)
	RegistryService registry;

	@UseJsfbootService (AdminHelpViewService.class)
	private AdminHelpViewService helpView;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		JsfbootModule module = context.getModule();
		
		// 添加帮助页面
		HelpBuilder hb = helpView.getHelpBuilder();
		helpView.addHelpDocument(PageDomainType.Admin,
				hb.newCatalog(module.getName(), module.getModuleInfo().getModuleTitle(),
				hb.includePage(hb.fullModulePath(module, "/help.xhtml"))));
	}

	@Override
	public void stopService() throws Exception {

	}

	@Override
	public Iterable<String> listDictionary() {
		final AdminDictionaryServiceImpl This = this;
		return new Iterable<String>(){
			@Override
			public Iterator<String> iterator() {
				return new DictionaryIterator(This);
			}
		};
	}

	@Override
	public DictionaryItem createDictionary(String code, DictionaryType type) throws DictionaryException {
		
		if (!NameUtils.isXMLName(code)){
			throw new DictionaryException(code + "代码不符合名称规范。");
		}
		
		if (loadDictionary(code) != null)
			throw new DictionaryException(code + " 已存在。");

		DictionaryItem item = new DictionaryItemImpl(code, type);
		try {
			saveDictionaryItem (code, item);
		} catch (RegistryException e) {
			throw new DictionaryException (e.getMessage());
		}
				
		return item;
	}
	
	@Override
	public DictionaryItem loadDictionary(String code) {
		RegistryItem regItem = registry.getItem(makeDictPath(code));
		if (regItem == null)
			return null;

		return loadDictionaryItem(regItem);
	}

	@Override
	public void updateDictionary(DictionaryItem dict) throws DictionaryException {

		String code = dict.getCode();
		
		if (loadDictionary(code) == null)
			throw new DictionaryException(code + " 不存在。");
		
		// 判断代码是否重复
		if (!code.equals(dict.getCode())){
			if (loadDictionary(dict.getCode()) != null)
				throw new DictionaryException(dict.getCode() + " 已存在。");
		}
		
		// 保存
		try {
			saveDictionaryItem (code, dict);
		} catch (RegistryException e) {
			throw new DictionaryException (e.getMessage());
		}
	}

	@Override
	public void removeDictionary(String code) throws DictionaryException {
		try {
			registry.removeItem(makeDictPath(code));
		} catch (RegistryException e) {
			throw new DictionaryException (e.getMessage());
		}
	}

	/**
	 * 从注册表结点中加载一个字典
	 * @param item
	 * @return
	 */
	private DictionaryItem loadDictionaryItem (RegistryItem regItem){

		// 生成字典对象
		DictionaryItem dict = new DictionaryItemImpl(regItem.getName());
		String dictTypeText = regItem.getValue("dictType");
		if (!StringKit.isEmpty(dictTypeText)){
			dict = dict.convertType(DictionaryType.valueOf(dictTypeText));
		}
		
		dict.setTitle(regItem.getValue("dictTitle"));
		dict.setCatalog(regItem.getValue("dictCatalog"));
		
		// 加载数据表字段
		SimpleDataTable table = dict.getTable();
		
		String fieldNames = regItem.getValue("fieldName");
		if (StringKit.isEmpty(fieldNames))
			return dict;

		String[] colNames = fieldNames.split(",");
		String[] colTitles = regItem.getValue("fieldTitle").split(",");

		for (int i = 0; i < colNames.length; i ++){
			String name = colNames[i].trim();
			String title = (i < colTitles.length)?colTitles[i].trim():"";
			table.addColumn(name, title);
		}
		
		// 初使化数据表行
		String rowCountText = regItem.getValue("rowCount");
		if (StringKit.isEmpty(rowCountText))
			return dict;
		
		int rowCount = Integer.valueOf(rowCountText);
		table.setRowCount(rowCount);
		
		// 读取行数据
		for (RegistryItem child : registry.allChildren(regItem.getPath())){
			String rowName = child.getName();
			if (!rowName.startsWith(ROW_PREFIX))
				continue;
			
			int index = Integer.valueOf(rowName.substring(ROW_PREFIX.length()));
			if (index < 0 || index >= rowCount)
				continue;
			
			// 设置字段值
			for (int i = 0; i < colNames.length; i ++){
				String val = child.getValue(colNames[i].trim());
				if (!StringKit.isEmpty(val)){
					table.setValue(index, i, val);
				}
			}
		}
		
		return dict;
	}
	
	/**
	 * 保存一个字典
	 * @param item
	 */
	private void saveDictionaryItem (String code, DictionaryItem dict) throws RegistryException{
		
		// 如果改了代码，先删除原来的
		if (!code.equals(dict.getCode())){
			registry.removeItem(makeDictPath(code));
		}
		
		// 创建新结点
		RegistryItem regItem = registry.createItem(makeDictPath(dict.getCode()));
		regItem.setValue("dictType", dict.getType().name());
		regItem.setValue("dictTitle", dict.getTitle());
		regItem.setValue("dictCatalog", dict.getCatalog());
		
		// 写入行数据
		SimpleDataTable table = dict.getTable();
		String fieldNames = "";
		String fieldTitles = "";
		
		for (TableColumn col : table.getColumnNames()){
			if (fieldNames.length() > 0){
				fieldNames += ", ";
				fieldTitles += ", ";
			}
			fieldNames += col.getName ();
			fieldTitles += col.getTitle();
		}

		regItem.setValue("fieldName", fieldNames);
		regItem.setValue("fieldTitle", fieldTitles);
		regItem.setValue("rowCount", table.getRowCount());
		
		registry.updateItem(regItem);
		
		// 先删除所有行
		for (RegistryItem child : registry.allChildren(regItem.getPath())){
			registry.removeItem(child.getPath());
		}

		for (int i = 0; i < table.getRowCount(); i ++){
			// 创建行索引
			String rowName = ROW_PREFIX + Integer.valueOf(i).toString();
			RegistryPath rowPath = RegistryPath.valueOf(regItem.getPath()).addChild(rowName);

			// 保存单元值
			RegistryItem rowItem = registry.createItem(rowPath);
			for (int j = 0; j < table.getColumnCount(); j ++){
				String colName = table.getColumnNames().get(j).getName();
				rowItem.setValue(colName, table.getValue(i, j));
			}
			registry.updateItem(rowItem);
		}
		
	}
	
	/**
	 * 生成一个注册表路径
	 * @param code
	 * @return
	 */
	private RegistryPath makeDictPath (String code){
		return RegistryPath.valueOfne(ADMINDICT_REGPATH).addChild(code);
	}
	
	/**
	 * 迭代器
	 * @author ihaoqi
	 *
	 */
	class DictionaryIterator implements Iterator<String>{
		
		AdminDictionaryServiceImpl service;
		Iterator<RegistryItem> regIter;
		
		public DictionaryIterator(AdminDictionaryServiceImpl service){
			this.service = service;
			this.regIter = registry.allChildren(RegistryPath.valueOfne(ADMINDICT_REGPATH)).iterator();
		}

		@Override
		public boolean hasNext() {
			return regIter.hasNext();
		}

		@Override
		public String next() {
			return regIter.next().getName();
		}

		@Override
		public void remove() {

		}
	}
}
