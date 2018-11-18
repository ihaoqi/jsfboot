package com.easyeip.jsfboot.webfile.vfs;

import java.io.IOException;
import java.io.OutputStream;

/**
 * 具体文件
 * @author liao
 *
 */
public interface WebFileResource extends WebFileEntry {

	/**
	 * 取得以byte为单位文件长度。
	 * @return
	 */
	long getFileSize();
	
	/**
	 * 取得文件类型（扩展名），如logo.png返回png
	 * @return 返回不带.的扩展名，如png、jpg、zip，如果没有扩展名返回空串(非null)。
	 */
	String getFileType();
	
	/**
	 * 读取文件所有内容
	 * @param os 输出流
	 * @return 读取的长度
	 * @exception 出错会抛出异常
	 */
	int load(OutputStream os) throws IOException;
	
	/**
	 * 读取文件指定长度的内容
	 * @param os 输出流
	 * @param size 要读的字节数
	 * @return 读取的长度
	 * @exception 出错会抛出异常
	 */
	int load(OutputStream os, int size) throws IOException;
	
	/**
	 * 覆盖文件内容，文件长度将会改变，更新时间也会变
	 * @param is 输入流
	 * @return 返回文件长度
	 * @exception 出错会抛出异常
	 */
	int save (byte[] data) throws IOException;
	
	/**
	 * 用指定长度的内容覆盖文件，文件长度将会改变，更新时间也会变
	 * @param is 输入流
	 * @param size 输入字节数
	 * @return 返回文件长度
	 * @exception 出错会抛出异常
	 */
	int save (byte[] data, int size) throws IOException;
}
