package com.easyeip.jsfboot.webfile.vfs;

import java.io.IOException;
import java.util.Date;

/**
 * web文件实体
 * @author liao
 *
 */
public interface WebFileEntry {
	
	/**
	 * 取得文件名称
	 * @return
	 */
	String getFileName();
	
	/**
	 * 文件或目录改名
	 * @param newName 新名称
	 * @exception 出错会抛出异常
	 */
	void rename(String newName) throws IOException;
	
	/**
	 * 取得文件完整路径，如/images/logo.png
	 * @return
	 */
	String getFullPath();
	
	/**
	 * 取得创建时间
	 * @return
	 */
	Date getCreateTime();
	
	/**
	 * 取得最后修改时间
	 * @return
	 */
	Date getModifyTime();
	
	/**
	 * 判断是否是目录
	 * @return
	 */
	boolean isDirectory();

	/**
	 * 取得所在的目录
	 * @return 如果已是根目录则会返回null
	 */
	WebFileFolder getParent();
	
	/**
	 * 转换成文件资源
	 * @return 失败返回null
	 */
	WebFileResource asResource();
	
	/**
	 * 转换成目录资源
	 * @return 失败返回null
	 */
	WebFileFolder asDirectory();
}
