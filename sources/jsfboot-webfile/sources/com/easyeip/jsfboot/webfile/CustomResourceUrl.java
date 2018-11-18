package com.easyeip.jsfboot.webfile;

public interface CustomResourceUrl {
	
	/**
	 * 取得唯一标记
	 * @return
	 */
	String getKey();
	
	/**
	 * 取得自定义url
	 * @return
	 */
	String getCustomUrl();
	
	/**
	 * 取得真实文件路径
	 * @return
	 */
	String getSourcePath();
}
