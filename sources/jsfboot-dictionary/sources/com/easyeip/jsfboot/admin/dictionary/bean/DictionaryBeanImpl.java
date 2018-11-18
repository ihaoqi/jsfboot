package com.easyeip.jsfboot.admin.dictionary.bean;

import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.admin.dictionary.AdminDictionaryService;
import com.easyeip.jsfboot.admin.dictionary.DictionaryBean;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.utils.KeyValuePair;

public class DictionaryBeanImpl extends JsfbootBean implements DictionaryBean {

	@UseJsfbootService(AdminDictionaryService.class)
	AdminDictionaryService service;		// 字典服务
	
	@Override
	public List<String> list(String dict) {
		DictionaryItem result = service.loadDictionary(dict);
		if (result == null){
			return Collections.emptyList();
		}
		return result.asList().getList();
	}

	@Override
	public List<KeyValuePair> map(String dict) {
		DictionaryItem result = service.loadDictionary(dict);
		if (result == null){
			return Collections.emptyList();
		}
		return result.asMap().getKVPair();
	}

}
