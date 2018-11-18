package com.easyeip.jsfboot.core.driver.impl;

import javax.servlet.ServletContext;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.RuntimeInfo;

/**
 * 运行时信息
 * 
 * @author ihaoqi
 *
 */
public class RuntimeInfoImpl implements RuntimeInfo {

	private boolean developmentMode = true;
	private ServletContext servletContext;
	private String webContentPath;
	private String configFilename;
	private String productName;

	public RuntimeInfoImpl(ServletContext servletContext) {
		this.servletContext = servletContext;
		productName = null;
		configFilename = null;
	}

	public void setDevelopmentMode(boolean value) {
		developmentMode = value;
	}
	
	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	@Override
	public boolean isDevelopmentMode() {
		return developmentMode;
	}

	@Override
	public boolean isProductionMode() {
		return !developmentMode;
	}

	@Override
	public boolean isArchiveWebSite() {
		// 如果是war，getRealPath会返回null，或.war!路径
		String path = servletContext.getRealPath("/WEB-INF/");

		return (path == null) || (path.toLowerCase().indexOf(".war!") >= 0);
	}

	@Override
	public String getWebContentPath() {
		if (webContentPath == null) {

			if (isArchiveWebSite()) {

				// 取得jar文件名
				String thisPath = JsfbootContext.class.getProtectionDomain().
									getCodeSource().getLocation().getFile();
				// 查找webinf目录
				int index = thisPath.lastIndexOf("/WEB-INF/");
				webContentPath = thisPath.substring(1, index);

			} else {
				webContentPath = servletContext.getRealPath("/");
				// 去除最后的/
				webContentPath = webContentPath.substring(0, webContentPath.length() - 1);
			}
		}

		return webContentPath;
	}

	@Override
	public String getConfigSavePath() {
		if (configFilename == null) {
			
			if (productName == null){
				throw new RuntimeException("无有效有产品名称。");
			}
			
			if (isArchiveWebSite()) {
				String rootPath = "";
				rootPath = this.getUserHome();
				rootPath += "/" + productName;
				configFilename = rootPath;
			} else {
				configFilename = getWebContentPath() + "/WEB-INF/conf";
			}
		}
		return configFilename;
	}

	@Override
	public String getUserHome() {
		return System.getProperty("user.home");
	}
}
