package com.easyeip.jsfboot.core.registry;

import java.util.List;

/**
 * 注册表服务
 * @author ihaoqi
 *
 */
public interface RegistryService {
	
	public static final String NOTIFY_LOADED = "loaded";	// 已加载载事件
	
	/**
	 * 取得概况
	 * @return
	 */
	RegistryProfile getProfile();
	
	/**
	 * 取得注册表根路径
	 * @return
	 */
	RegistryPath getRootPath ();
	
	/**
	 * 取得一个已存在注册表结点
	 * @param path
	 * @return 返回注册表结点结象，如果不存在返回 null
	 */
	RegistryItem getItem(RegistryPath path);
	
	/**
	 * 取得指定路径下的子结点（不会递归孙结点）
	 * @param registryPath 要获取的结点路径
	 * @return 返回不为null的列表
	 */
	List<RegistryItem> listChildren (RegistryPath path);
	
	/**
	 * 遍历路径下的子结点（非递归）
	 * @param path 要遍历的结点路径
	 * @return 返回子结点遍历器
	 */
	Iterable<RegistryItem> allChildren (RegistryPath path);
	
	/**
	 * 与getItem一样，但当路径不存在时会抛异常
	 * @param path
	 * @return 
	 * @throws RegistryException
	 */
	RegistryItem useItem(RegistryPath path) throws RegistryException;
	
	/**
	 * 创建一个注册表结点，如果已存在时则返回原来的
	 * @param path 注册表结点路径
	 * @return 返回注册表结点对象，出错抛出异常
	 */
	RegistryItem createItem(RegistryPath path) throws RegistryException;
	
	/**
	 * 移除一个结点
	 * @param path 要移除的结点路径，不能移除跟路径
	 * @throws Exception 移除失败就会抛出异常
	 */
	void removeItem (RegistryPath path) throws RegistryException;
	
	/**
	 * 更新已修改的结点
	 * @param item
	 * @throws RegistryException
	 */
	void updateItem(RegistryItem item) throws RegistryException;
}
