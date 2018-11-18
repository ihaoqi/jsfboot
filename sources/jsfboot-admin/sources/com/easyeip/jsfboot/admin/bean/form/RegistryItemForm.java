package com.easyeip.jsfboot.admin.bean.form;

import com.easyeip.jsfboot.core.registry.RegistryPath;

/**
 * 添加注册表表单
 * @author ihaoqi
 *
 */
public class RegistryItemForm {

	private String name = "";			// 新结点名称
	private String comment = "";		// 新结点说明
	private RegistryPath selectPath;	// 当前选中结点
	
	public RegistryPath getSelectPath() {
		return selectPath;
	}
	public void setSelectPath(RegistryPath path) {
		this.selectPath = path;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
}
