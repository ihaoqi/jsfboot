package com.easyeip.jsfboot.admin.dictionary;

import com.easyeip.jsfboot.admin.dictionary.type.DictionaryException;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;

/**
 * 字典管理
 * @author ihaoqi
 *
 */
public interface AdminDictionaryService {

	/**
	 * 列出所有字典的代码
	 * @return
	 */
	Iterable<String> listDictionary();
	
	/**
	 * 取得字典，不存在返回null
	 * @param code
	 * @return
	 */
	DictionaryItem loadDictionary (String code);
	
	/**
	 * 创建一个新的字典项
	 * @param code 字典名称，不能与现在的重复
	 * @param type 字典类型
	 * @return
	 * @throws Exception
	 */
	DictionaryItem createDictionary (String code, DictionaryType type) throws DictionaryException;

	/**
	 * 保存字典,如果代码不存在或出错都会抛出异常
	 * @param dict
	 * @throws DictionaryException
	 */
	void updateDictionary(DictionaryItem dict) throws DictionaryException;
	
	/**
	 * 删除一个注册表项
	 * @param code
	 * @return 成功返回true，失败返回false
	 */
	void removeDictionary (String code) throws DictionaryException;
}
