package com.easyeip.jsfboot.webfile.vfs;

public interface WebFileListener {

	/**
	 * 文件操作通知
	 * @param entry 操作的文件
	 * @param notify 通知名称
	 */
	void webFileNotify(WebFileEntry entry, String notify);

}
