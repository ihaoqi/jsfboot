package com.easyeip.jsfboot.webfile.type;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;
import com.easyeip.jsfboot.webfile.web.WebfileResourceServlet;

public class WebFileResourceForm {
	
	WebfileResourceService service;
	private WebFileResource file;
	
	private String newFileName;		// 文件名
	private String customDownPath;	// 自定义下载路径
	
	public WebFileResourceForm(WebfileResourceService service, WebFileResource file){
		this.service = service;
		this.file = file;
		setNewFileName(file.getFileName());
		customDownPath = service.getCustomUrlManager().findResourceCustomUrl(file);
	}
	
	public WebFileResource getRaw(){
		return file;
	}

	public String getFileSize(){
		if (file.getFileSize() < 1024)
			return Long.valueOf(file.getFileSize()).toString();
		else
			return Long.valueOf(file.getFileSize()/1024).toString() + "K";
	}
	
	/**
	 * 取得下载路径
	 * @return
	 */
	public String getDownloadPath(){
		return WebfileResourceServlet.JRESOURCE_SERVLET + file.getFullPath();
	}
	
	/**
	 * 是否图片文件
	 * @return
	 */
	public boolean getIsImageFile(){
		
		String[] extNames = {"png","jpg","jpeg","gif","bmp","ico"};
		String fileName = file.getFileName();
		int fileExtIdx = fileName.lastIndexOf(".");
		if (fileExtIdx >= 0){
			String fileExtName = fileName.substring(fileExtIdx + 1).toLowerCase();
			for (String ext : extNames){
				if (StringKit.equals(ext, fileExtName))
					return true;
			}
		}
		
		return false;
	}

	public String getCustomDownPath() {
		return customDownPath;
	}

	public void setCustomDownPath(String customDownPath) {
		this.customDownPath = customDownPath;
	}

	public String getNewFileName() {
		return newFileName;
	}

	public void setNewFileName(String newFileName) {
		this.newFileName = newFileName;
	}
}
