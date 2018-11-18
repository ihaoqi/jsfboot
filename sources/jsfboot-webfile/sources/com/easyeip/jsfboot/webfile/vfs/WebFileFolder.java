package com.easyeip.jsfboot.webfile.vfs;

import java.io.IOException;
import java.util.List;

public interface WebFileFolder extends WebFileEntry {
	
	/**
	 * 是否是根目录
	 * @return
	 */
	boolean isRoot();
	
	/**
	 * 取得文件列表，目录会排在前面
	 * @return
	 */
	List<WebFileEntry> listFiles();
	
	/**
	 * 创建一个新的目录
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	WebFileFolder createFolder(String fileName) throws Exception;
	
	/**
	 * 创建一个新的空白文件
	 * @param fileName 文件名，如logo.png，不能有路径
	 * @return 返回资源对象
	 * @throws IOException 出错将抛出异常，如文件重名
	 */
	WebFileResource createResource (String fileName) throws IOException;
	
	/**
	 * 删除指定资源目录
	 * @param fileName 文件名，如logo.png，不能有路径
	 * @throws IOException 出错将抛出异常
	 */
	void deleteFolder(String fileName) throws IOException;
	
	/**
	 * 删除指定资源文件
	 * @param fileName 文件名，如logo.png，不能有路径
	 * @throws IOException 出错将抛出异常
	 */
	void deleteResource (String fileName) throws IOException;
}
