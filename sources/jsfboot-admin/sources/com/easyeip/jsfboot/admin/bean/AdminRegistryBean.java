package com.easyeip.jsfboot.admin.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.bean.form.ItemAttributeForm;
import com.easyeip.jsfboot.admin.bean.form.RegistryItemForm;
import com.easyeip.jsfboot.admin.bean.type.ItemAttribute;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryProfile;

/**
 * 注册表管理jsf bean
 * @author ihaoqi
 *
 */
public interface AdminRegistryBean {
	
	RegistryProfile getRegProfile();

	/**
	 * 取得树的根结点
	 * @return
	 */
	TreeNode getTreeRoot();
	
	/**
	 * 获取当前选中结点
	 * @return
	 */
	RegistryItem getSelectItem ();
	
	/**
	 * 设置当前选中结点
	 * @param item
	 */
	void onNodeSelect(NodeSelectEvent event);
	void onNodeExpand(NodeExpandEvent event);
	void onNodeCollapse(NodeCollapseEvent event);
	
	/**
	 * 获取当前详情面板的类型
	 * @return
	 */
	String getDetailPanel();
	
	// 添加结点
	void beginAddRegistryItem(ActionEvent event);
	void endAddRegistryItem(ActionEvent event);
	
	// 编辑结点
	void beginEditRegistryItem(ActionEvent event);
	void endEditRegistryItem(ActionEvent event);
	
	// 删除结点
	void beginDelRegistryItem(ActionEvent event);
	void endDelRegistryItem(ActionEvent event);
	
	void toRegistryItemView(ActionEvent event);
	
	/**
	 * 获取添加注册表项表单
	 * @return
	 */
	RegistryItemForm getRegistryItemFrom();
	
	/**
	 * 取得选中结点的属性列表
	 * @return
	 */
	List<ItemAttribute> getRegistryItemAttrs();
	
	// 添加属性
	void beginAddItemAttr(ActionEvent event);
	void endAddItemAttr(ActionEvent event);
	
	// 删除属性
	void doDelItemAttr(ActionEvent event);
	
	// 修改属性
	void beginEditItemAttr(ActionEvent event);
	void endEditItemAttr(ActionEvent event);
	
	/**
	 * 取得属性表单
	 * @return
	 */
	ItemAttributeForm getAttributeForm();

}
