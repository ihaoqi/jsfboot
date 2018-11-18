package com.easyeip.jsfboot.admin.dictionary.type;

import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.KeyValuePair;

public interface MapDictionaryItem extends DictionaryItem {

	Map<String,String> getMap();
	void setMap(Map<String,String> map);
	
	List<KeyValuePair> getKVPair();
	void setKVPair(List<KeyValuePair> list);
}
