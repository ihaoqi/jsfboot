package com.easyeip.jsfboot.core.registry;

import java.util.List;

public interface RegistryItem {

	/**
	 * 缺省值名称
	 */
	public static final String DEFAULT_NAME = "";
	
	/**
	 * 获取结点所有在路径
	 * @return 返回一个只读的路径
	 */
	RegistryPath getPath();
	
	/**
	 * 获取结点名称
	 * @return
	 */
	String getName();
	
	void setName (String name);
	
	/**
	 * 获取结点注释
	 * @return
	 */
	String getComment ();
	
	/**
	 * 设置结点注释
	 * @param comment
	 */
	void setComment (String comment);
	
	/**
	 * 取得缺省值
	 * @return 返回值对象，如果不存在就返回null
	 */
	String getDefaultValue ();
	
	/**
	 * 添加缺省值
	 * @param type 值类型
	 * @return 返回值对象
	 */
	void setDefaultValue(String value);

	/**
	 * 获取所有属性名称（包含缺省值）
	 * @return 返回属性名称数组
	 */
	List<String> valueNames();
	
	/**
	 * 获取属性值
	 * @param name 属性名称
	 * @return 返回值对象，没有就返回null
	 */
	String getValue (String name);
	
	/**
	 * 是否存在值
	 * @param name
	 * @return
	 */
	boolean hasValue (String name);
	
	/**
	 * 添加属性
	 * @param name 属性名称
	 * @param type 值类型
	 * @return 如果已存在相同名称的属性将删除它并重新添加
	 */
	void setValue (String name, String value);
	void setValue (String name, long value);
	
	/**
	 * 删除属性
	 * @param name 属性名称
	 */
	void removeValue (String name);
	
	/**
	 * 取消所有修改
	 */
	void reset();
	
	@Override
	String toString();
}
