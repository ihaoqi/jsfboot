package com.easyeip.jsfboot.admin.bean.type;

import java.util.List;
import java.util.Map.Entry;

import com.easyeip.jsfboot.core.registry.RegistryPath;

public class ItemAttrPair implements Entry<RegistryPath, List<ItemAttribute>> {
	
	RegistryPath key;
	List<ItemAttribute> value;
	
	public ItemAttrPair(RegistryPath key, List<ItemAttribute> value){
		this.key = key;
		this.value = value;
	}

	@Override
	public RegistryPath getKey() {
		return key;
	}

	@Override
	public List<ItemAttribute> getValue() {
		return value;
	}

	@Override
	public List<ItemAttribute> setValue(List<ItemAttribute> value) {
		this.value = value;
		return value;
	}

}
