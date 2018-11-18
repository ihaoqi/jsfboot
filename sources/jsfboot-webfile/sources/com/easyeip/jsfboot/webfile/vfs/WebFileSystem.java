package com.easyeip.jsfboot.webfile.vfs;

/**
 * 虚拟文件系统
 * @author liao
 *
 */
public interface WebFileSystem {
	
	/**
	 * 取得根文件夹
	 * @return
	 */
	WebFileFolder getRootFolder();
	
	/**
	 * 添加文件操作监听
	 * @param listener
	 */
	void addFileListener(WebFileListener listener);
}
