package com.easyeip.jsfboot.webfile;

import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileSystem;

/**
 * web文件资源管理服务
 * @author liao
 *
 */
public interface WebfileResourceService{
	
	/**
	 * 获取当前配置的文件系统
	 * @return
	 */
	WebFileSystem getFileSystem();
	
	/**
	 * 按路径查询资源
	 * @param filePath	资源名称
	 * @param findFolder true表示要查找目录路径，false表示要查找文件路径
	 * @return
	 */
	WebFileEntry findResource(String filePath, boolean findFolder);
	
	/**
	 * 取得文件系统配置
	 * @return
	 */
	WebfileResourceProfile getProfile();
	
	/**
	 * 取得自定义资源URL管理
	 * @return
	 */
	ResourceUrlManager getCustomUrlManager();
}
