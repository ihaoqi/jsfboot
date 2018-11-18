package com.easyeip.jsfboot.admin.dictionary.type;

/**
 * 字典项（支持表格）
 * @author ihaoqi
 *
 */
public interface DictionaryItem {
	
	/**
	 * 取得字典类型
	 * @return
	 */
	DictionaryType getType();
	
	/**
	 * 转换成其他类型的字典
	 * @param type
	 * @return 返回一个新字典
	 */
	DictionaryItem convertType(DictionaryType type);
	
	/**
	 * 取得字典代码
	 * @return
	 */
	String getCode();
	
	/**
	 * 取得标题
	 * @return
	 */
	String getTitle();
	
	/**
	 * 设置标题
	 * @param title
	 * @return
	 */
	void setTitle(String title);
	
	/**
	 * 取得分类
	 * @return
	 */
	String getCatalog();
	
	/**
	 * 设置分类
	 * @param catalog
	 */
	void setCatalog(String catalog);
	
	/**
	 * 取得行数
	 * @return
	 */
	int getSize();
	
	/**
	 * 转换成List内容对象
	 * @return
	 */
	ListDictionaryItem asList();
	
	/**
	 * 转换成Map内容对象
	 * @return
	 */
	MapDictionaryItem asMap();
	
	/**
	 * 转换成Table内容对象
	 * @return
	 */
	DictDataTable getTable();

	/**
	 * 复制一个字典
	 * @param src
	 * @return
	 */
	DictionaryItem copyFrom(DictionaryItem src);
}
