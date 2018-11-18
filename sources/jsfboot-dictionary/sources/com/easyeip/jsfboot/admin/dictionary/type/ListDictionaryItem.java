package com.easyeip.jsfboot.admin.dictionary.type;

import java.util.List;

public interface ListDictionaryItem extends DictionaryItem {

	List<String> getList();

	void setList(List<String> list);
}
