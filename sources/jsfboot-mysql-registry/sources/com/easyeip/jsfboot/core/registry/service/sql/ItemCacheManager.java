package com.easyeip.jsfboot.core.registry.service.sql;

import java.util.HashMap;
import java.util.Map;

public class ItemCacheManager {
	
	private Map<String, RegistryItemEntry> cacheMap;
	
	public ItemCacheManager(){
		cacheMap = new HashMap<String, RegistryItemEntry>();
	}
	
	/**
	 * 取得cache
	 * @param itemPath
	 * @return
	 */
	public RegistryItemEntry getCache (String itemPath){
		return cacheMap.get(itemPath);
	}
	
	/**
	 * 保存cahce
	 * @param itemId
	 * @param valueList
	 */
	public void setCache(String itemPath, RegistryItemEntry entry){
		cacheMap.put(itemPath, entry);
	}
	
	/**
	 * 移除item value
	 * @param itemId
	 */
	public void removeCache (String itemPath){
		cacheMap.remove(itemPath);
	}

	/**
	 * 清除所有cache
	 */
	public void clearAll(){
		cacheMap.clear();
	}
}
