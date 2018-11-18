package com.easyeip.jsfboot.webfile;

import java.util.List;

import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public interface ResourceUrlManager {
	/**
	 * 取得自定义URL列表
	 * @return
	 */
	List<CustomResourceUrl> getResourceList();
	
	/**
	 * 匹配指定的url，看是否有自定义资源
	 * @param customUrl
	 * @return
	 */
	CustomResourceUrl lookupUrlResource (String customUrl);
	
	/**
	 * 查找文件是否有自定义路径
	 * @param res
	 * @return
	 */
	String findResourceCustomUrl (WebFileResource res);
	
	/**
	 * 添加自定义资源url
	 * @param res
	 */
	boolean setResourceUrl (WebFileResource res, String customUrl);
	
	/**
	 * 移除指定自定义URL
	 * @param filePath 原文件路径
	 * @return
	 */
	boolean removeResourceUrl(String filePath);
}
