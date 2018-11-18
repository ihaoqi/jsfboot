package com.easyeip.jsfboot.utils;

public class KeyValuePair {

	String key;
	String value;
	
	public KeyValuePair(String key, String value){
		this.key = key;
		this.value = value;
	}
	
	/**
	 * 分析一个键值对字符串，如 key=value
	 * @param keyValStr
	 * @param joinChar 键值对链接不同符，如“=”、“/”、“:”
	 * @return
	 */
	public static KeyValuePair parse(String keyValStr, String joinChar, boolean trimKV){
		int spIndex = keyValStr.indexOf(joinChar);
		if (spIndex < 0){
			return new KeyValuePair(keyValStr, null);
		}
		
		String key, value;
		key = keyValStr.substring(0, spIndex);
		value = keyValStr.substring(spIndex+joinChar.length());
		
		return new KeyValuePair (trimKV ? key.trim() : key, trimKV ? value.trim() : value);
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String val){
		this.value = val;
	}
	
	@Override
	public String toString() {
		return key + "=" + value;
	}

}
