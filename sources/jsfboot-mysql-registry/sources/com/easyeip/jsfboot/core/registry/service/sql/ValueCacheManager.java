package com.easyeip.jsfboot.core.registry.service.sql;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ValueCacheManager {
	
	private Map<Long,List<RegistryValueEntry>> cacheMap;
	
	public ValueCacheManager(){
		cacheMap = new HashMap<Long,List<RegistryValueEntry>>();
	}
	
	/**
	 * 取得cache
	 * @param itemId
	 * @return
	 */
	public List<RegistryValueEntry> getCache (long itemId){
		return cacheMap.get(itemId);
	}
	
	/**
	 * 保存cahce
	 * @param itemId
	 * @param valueList
	 */
	public void setCache(long itemId, List<RegistryValueEntry> valueList){
		cacheMap.put(itemId, valueList);
	}
	
	/**
	 * 移除item value
	 * @param itemId
	 */
	public void removeCache (long itemId){
		cacheMap.remove(itemId);
	}

	/**
	 * 清除所有cache
	 */
	public void clearAll(){
		cacheMap.clear();
	}
}
