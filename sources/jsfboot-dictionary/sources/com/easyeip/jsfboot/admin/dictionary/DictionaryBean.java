package com.easyeip.jsfboot.admin.dictionary;

import java.util.List;

import com.easyeip.jsfboot.utils.KeyValuePair;

/**
 * 全局字典bean
 * @author liao
 *
 */
public interface DictionaryBean {
	
	/**
	 * 取列表字典
	 * @param dict
	 * @return
	 */
	List<String> list(String dict);
	
	/**
	 * 取keyValue字典
	 * @param dict
	 * @return
	 */
	List<KeyValuePair> map(String dict);

}
