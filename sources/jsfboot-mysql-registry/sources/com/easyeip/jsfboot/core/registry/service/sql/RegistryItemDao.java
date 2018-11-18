package com.easyeip.jsfboot.core.registry.service.sql;

import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

public interface RegistryItemDao {
	
	public static final String BASE_TABLE_NAME = "registry_item";
	
	/**
	 * 取得表名称
	 * @return
	 */
	String getTableName ();
	
	/**
	 * 取得数据库操作类
	 * @return
	 */
	JdbcTemplate getJdbcTemplate();
	
	/**
	 * 查询指定id的记录
	 * @param itemId
	 * @return
	 */
	RegistryItemEntry queryOne (long itemId);
	
	/**
	 * 执行sql查询语句
	 * @param whereSql
	 * @param args
	 * @return
	 */
	List<RegistryItemEntry> queryItems (String whereSql, Object args[]);
	
	/**
	 * 插入一行记录
	 * @param parentEntry
	 * @param item_name
	 * @return
	 */
	RegistryItemEntry createOne (RegistryItemEntry parentEntry, String item_name);

	/**
	 * 删除一行记录
	 * @param entry
	 * @return
	 */
	boolean delete(RegistryItemEntry entry);
	
	/**
	 * 更新一行值与说明
	 * @param entry
	 * @return
	 */
	boolean update(RegistryItemEntry entry);
	
	/**
	 * 改名称
	 * @param entry
	 * @param newName
	 * @return
	 */
	boolean rename (RegistryItemEntry entry, String newName);
}
