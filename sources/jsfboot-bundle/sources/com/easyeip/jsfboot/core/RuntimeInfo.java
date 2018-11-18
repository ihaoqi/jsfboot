package com.easyeip.jsfboot.core;

/**
 * 系统运行时信息
 * @author ihaoqi
 *
 */
public interface RuntimeInfo {

	/**
	 * 判断是否是开发模式
	 * @return
	 */
	boolean isDevelopmentMode();
	boolean isProductionMode();
	
	/**
	 * 是否是打包的 war 系统
	 * @return
	 */
	boolean isArchiveWebSite();
	
	/**
	 * 获取用户目录
	 * @return windows：返回如 c:/users/liao
	 */
	String getUserHome();
	
	/**
	 * 取得真实的网站运行路径
	 * @return 返回真实 WEB-INF 目录所在路径，如 c:/tomcat/webapp/jsfboot。如果是war也是返回解压后的。
	 */
	String getWebContentPath();

	/**
	 * 返回配置文件修改后的保存路径
	 * @return 如果是网站是war，则返回以“用户文档目录/系统名称”路径，否则返回实际“WEB-INF/conf”目录
	 */
	String getConfigSavePath();
}
