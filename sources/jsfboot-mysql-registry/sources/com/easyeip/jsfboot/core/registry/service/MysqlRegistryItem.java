package com.easyeip.jsfboot.core.registry.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryItemEntry;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryValueDao;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryValueEntry;
import com.easyeip.jsfboot.core.registry.service.sql.ValueCacheManager;
import com.easyeip.jsfboot.core.registry.service.sql.ValueUpdateData;
import com.easyeip.jsfboot.core.registry.service.sql.ValueUpdateType;

public class MysqlRegistryItem implements RegistryItem {
	
	private RegistryValueDao valueDao;
	
	private RegistryItemEntry entry;
	private RegistryPath path;
	
	private String newName;
	private String defaultValue;
	private String itemComment;
	
	ValueCacheManager cacheMgr;
	private Map<String, ValueUpdateData> attrValues;
	
	public MysqlRegistryItem(ValueCacheManager cacheMgr, 
			RegistryValueDao valueDao, RegistryItemEntry entry, RegistryPath path){
		this.cacheMgr = cacheMgr;
		this.valueDao = valueDao;
		this.entry = entry;
		this.path = path;
		newName = entry.getItemName();
		defaultValue = entry.getDefaultValue();
		itemComment = entry.getComment();
	}
	
	public void reset(){
		newName = entry.getItemName();
		defaultValue = entry.getDefaultValue();
		itemComment = entry.getComment();
		
		attrValues = null;
	}
	
	/**
	 * 取得新值
	 * @return
	 */
	public Map<String, ValueUpdateData> getValueData(boolean loader){
		if (attrValues == null && loader == true){
			attrValues = new HashMap<String,ValueUpdateData> ();
			
			// 读取cahce或db
			List<RegistryValueEntry> list =  cacheMgr.getCache(entry.getItemId());
			if (list == null){
				list = valueDao.queryValues(entry);
				cacheMgr.setCache(entry.getItemId(), list);
			}
			
			for (RegistryValueEntry value : list){
				attrValues.put(value.getName(), new ValueUpdateData(ValueUpdateType.entry, value.getValue(), value));
			}
		}
		
		return attrValues;
	}
	
	public RegistryItemEntry getEntry(){
		return entry;
	}

	@Override
	public RegistryPath getPath() {
		return path;
	}

	@Override
	public String getName() {
		return newName;
	}

	@Override
	public void setName(String name) {
		this.newName = name;
	}

	@Override
	public String getComment() {
		return itemComment;
	}

	@Override
	public void setComment(String comment) {
		this.itemComment = comment;
	}

	@Override
	public String getDefaultValue() {
		return defaultValue;
	}

	@Override
	public void setDefaultValue(String value) {
		this.defaultValue = value;
	}

	@Override
	public List<String> valueNames() {
		List<String> names = new ArrayList<String>();
		names.add(null);	// 默认值
		
		for (String key : getValueData(true).keySet()){
			names.add(key);
		}
			
		// 排序
		Collections.sort(names, new Comparator<String>(){
			public int compare(String o1, String o2) {
				if (o1 == null) o1 = "";
				if (o2 == null) o2 = "";
				return o1.compareTo(o2);
			}
		});
		
		return names;
	}
	
	@Override
	public boolean hasValue(String name) {
		return getValueData(true).containsKey(name);
	}

	@Override
	public String getValue(String name) {
		if (StringKit.isEmpty(name)){
			return getDefaultValue ();
		}
		
		ValueUpdateData ve = getValueData(true).get(name);
		if (ve == null)
			return null;
		return ve.getValue();
	}
	
	@Override
	public void setValue(String name, String value) {
		
		if (StringKit.isEmpty(name)){
			setDefaultValue (value);
			return;
		}
		
		ValueUpdateData ve = getValueData(true).get(name);
		if (ve != null && ve.getEntry() != null){
			getValueData(true).put(name, new ValueUpdateData(ValueUpdateType.update, value, ve.getEntry()));
		}else{
			getValueData(true).put(name, new ValueUpdateData(ValueUpdateType.insert, value, null));
		}
	}

	@Override
	public void setValue(String name, long value) {
		setValue(name, Long.valueOf(value).toString());
	}

	@Override
	public void removeValue(String name) {
		ValueUpdateData ve = getValueData(true).get(name);
		if (ve != null && ve.getEntry() != null)
			getValueData(true).put(name, new ValueUpdateData(ValueUpdateType.delete, null, ve.getEntry()));
		else
			getValueData(true).remove(name);
	}
}
