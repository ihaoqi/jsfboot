package com.easyeip.jsfboot.core.registry.service.sql;

import java.util.Iterator;

import com.easyeip.jsfboot.core.registry.RegistryItem;

public class MysqlRegistryItemIterator implements Iterator<RegistryItem>{
	
	RegistryItemDao dao;
	RegistryItemEntry entry;
	
	public MysqlRegistryItemIterator(RegistryItemDao dao, RegistryItemEntry entry){
		this.dao = dao;
		this.entry = entry;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

	@Override
	public RegistryItem next() {
		// TODO Auto-generated method stub
		return null;
	}

}
