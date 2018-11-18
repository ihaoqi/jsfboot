package com.easyeip.jsfboot.webfile;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.vfs.regfs.RegistryFileSystem;

public class WebfileResourceProfile {
	
	private RegistryService registry;

	public WebfileResourceProfile(RegistryService registry){
		this.registry = registry;
		
	}
	/**
	 * 取得文件上传大小限制（KB为单位）
	 * @return
	 */
	public int getFileUploadLimit(){
		RegistryItem item = registry.getItem(RegistryPath.valueOfne(RegistryFileSystem.REGISTRY_WEBFILES_PATH));
		if (item == null)
			return 1024;
		return StringKit.toInteger(item.getValue("uploadLimit"), 1024);
	}
	
	/**
	 * 设置文件上传大小限制（KB为单位）
	 * @param kb
	 */
	public void setFileUploadLimit(int kb){
		try {
			RegistryItem item = registry.createItem(RegistryPath.valueOf(RegistryFileSystem.REGISTRY_WEBFILES_PATH));
			item.setValue("uploadLimit", kb);
			registry.updateItem(item);
		} catch (RegistryException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 取得允许上传的文件类型
	 * @return 反回类型匹配正则表达式，如 /(\.|\/)(gif|jpe?g|png)$/
	 */
	public String getFileUploadTypes(){
		RegistryItem item = registry.getItem(RegistryPath.valueOfne(RegistryFileSystem.REGISTRY_WEBFILES_PATH));
		if (item == null)
			return null;
		return item.getValue("uploadTypes");
	}
	
	/**
	 * 设置允许上传的文件类型
	 * @param types 类型匹配正则表达式，如 /(\.|\/)(gif|jpe?g|png)$/
	 */
	public void setFileUploadTypes(String types){
		try {
			RegistryItem item = registry.createItem(RegistryPath.valueOf(RegistryFileSystem.REGISTRY_WEBFILES_PATH));
			item.setValue("uploadTypes", types);
			registry.updateItem(item);
		} catch (RegistryException e) {
			e.printStackTrace();
		}
	}
}
