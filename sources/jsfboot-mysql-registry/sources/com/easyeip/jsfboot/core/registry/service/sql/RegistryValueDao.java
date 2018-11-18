package com.easyeip.jsfboot.core.registry.service.sql;

import java.util.List;

public interface RegistryValueDao {
	public static final String BASE_TABLE_NAME = "registry_value";
	
	String getTableName ();

	List<RegistryValueEntry> queryValues (RegistryItemEntry item);
	
	boolean createOne (RegistryItemEntry item, String valueName, String valueContent);

	boolean delete(RegistryValueEntry entry);
	
	boolean save(RegistryValueEntry entry);
}
