package com.easyeip.jsfboot.admin.bean.impl;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.bean.AdminRegistryBean;
import com.easyeip.jsfboot.admin.bean.form.ItemAttributeForm;
import com.easyeip.jsfboot.admin.bean.form.RegistryItemForm;
import com.easyeip.jsfboot.admin.bean.type.ItemAttrPair;
import com.easyeip.jsfboot.admin.bean.type.ItemAttribute;
import com.easyeip.jsfboot.admin.bean.type.RegistryDetailPanel;
import com.easyeip.jsfboot.admin.bean.type.RegistryTreeNode;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.beans.JsfbootBeanException;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryProfile;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryService;

public class AdminRegistryBeanImpl extends JsfbootBean implements AdminRegistryBean {
	
	@UseJsfbootService(RegistryService.class)
	private RegistryService registry;
	
	private RegistryTreeNode treeRoot;
	
	// 当前详情面板类型
	private RegistryDetailPanel detialPanel;

	// 以下是当前选中结点对象相关
	private RegistryPath selectPath;
	private RegistryItem selectItem;
	private ItemAttrPair path4attrs;
	
	// 以下是操作相关
	private RegistryItemForm registryItemForm;
	private ItemAttributeForm attributeForm;
	
	public AdminRegistryBeanImpl(){
		
		detialPanel = RegistryDetailPanel.registryDetail;

		initTreeRoot();
	}
	
	private void initTreeRoot(){
		// 生成根结点，扩展根结点(只显示最多20行)
		treeRoot = new RegistryTreeNode (registry, registry.getItem(registry.getRootPath()));
		int extCount = 0; int maxRow = 25;
		for (int i = 0; i < treeRoot.getChildCount() && extCount < maxRow; i ++){
			RegistryTreeNode n2 = (RegistryTreeNode) treeRoot.getChildren().get(i);
			if (extCount + n2.getChildCount() < maxRow){
				n2.expandChildren(true);
				n2.setExpanded(true);
				extCount += n2.getChildCount();
			}
		}
		selectPath = null;
	}
	
	public void throwNullPointerException() {
        throw new NullPointerException("A NullPointerException!");
    }

	public RegistryService getRegistry() {

		return registry;
	}
	
	@Override
	public RegistryItem getSelectItem() {
		if (selectPath == null)
			return null;
		
		if (selectItem != null && StringKit.equals(selectItem.getPath().getFullPath(), selectPath.getFullPath()))
			return selectItem;
		
		selectItem = registry.getItem(selectPath);
		return selectItem;
	}
	
	@Override
	public TreeNode getTreeRoot() {
		return treeRoot;
	}

	@Override
	public void onNodeSelect(NodeSelectEvent event) {
		RegistryTreeNode regNode = (RegistryTreeNode) event.getTreeNode();
		RegistryItem item = (RegistryItem) regNode.getData();
		
		regNode.selectThisNode();
		
		selectPath = item.getPath();
		detialPanel = RegistryDetailPanel.itemDetail;
	}
	
	public void onNodeExpand(NodeExpandEvent event) {
		RegistryTreeNode node = (RegistryTreeNode) event.getTreeNode();
		node.expandChildren(true);
		
		if (selectPath != null){
			RegistryTreeNode selNode = treeRoot.findNodeOfItem(selectPath);
			if (selNode != null){
				selNode.selectThisNode();
			}
		}
	}
	
	public void onNodeCollapse(NodeCollapseEvent event) {
		RegistryTreeNode node = (RegistryTreeNode) event.getTreeNode();
		node.expandChildren(false);
	}
	
	@Override
	public String getDetailPanel() {
		return detialPanel.name();
	}

	@Override
	public void beginAddRegistryItem(ActionEvent event) {
		registryItemForm = null;
		if (selectPath == null)
			getRegistryItemFrom().setSelectPath(registry.getRootPath());
		else
			getRegistryItemFrom().setSelectPath(selectPath);
		detialPanel = RegistryDetailPanel.addItem;
	}
	
	@Override
	public void endAddRegistryItem(ActionEvent event) {
		String name = registryItemForm.getName().trim();
		String comment = registryItemForm.getComment();
		
		if (!RegistryPath.isValidName(name)){
			FacesUtils.addMessageError("添加", name + " 不符合名称规范。");
			return;
		}
		
		RegistryItem addParent = registry.getItem(registryItemForm.getSelectPath());
		RegistryPath newPath = RegistryPath.valueOf(addParent.getPath()).makeChild(name);
		if (registry.getItem(newPath) != null){
			FacesUtils.addMessageError("添加", name + " 已存在。");
			return;
		}
		
		try{
			RegistryItem item = registry.createItem(newPath);
			if (StringKit.notEmpty(comment)){
				item.setComment(comment);
				registry.updateItem(item);
			}
			
			// 选中新结点
			treeRoot.addChildOfItem(addParent.getPath(), item);
			toRegistryItemView (event);
			
		}catch (RegistryException e){
			FacesUtils.addMessageError("添加", e.getMessage());
			return;
		}
	}
	
	@Override
	public void beginEditRegistryItem(ActionEvent event) {
		registryItemForm = null;
		RegistryItem item = registry.getItem(selectPath);
		getRegistryItemFrom().setSelectPath(item.getPath());
		getRegistryItemFrom().setName(item.getName());
		getRegistryItemFrom().setComment(item.getComment());
		detialPanel = RegistryDetailPanel.editItem;
	}
	
	@Override
	public void endEditRegistryItem(ActionEvent event){
		String name = registryItemForm.getName();
		String comment = registryItemForm.getComment();
		
		if (!RegistryPath.isValidName(name)){
			FacesUtils.addMessageError("修改", name + " 不符合名称规范。");
			return;
		}
		
		RegistryItem item = registry.getItem(registryItemForm.getSelectPath());
		
		boolean nameChanged = !StringKit.equals(item.getName(), name);
		
		item.setName(name);
		item.setComment(comment);
		
		try {
			registry.updateItem(item);
		} catch (RegistryException e) {
			FacesUtils.addMessageError("修改", e.getMessage());
			return;
		}
		
		// 如果改了名就重新加载树
		if (nameChanged){
			initTreeRoot();
		}

		toRegistryItemView (event);
	}
	
	@Override
	public void beginDelRegistryItem(ActionEvent event) {
		registryItemForm = null;
		getRegistryItemFrom().setSelectPath(selectPath);
		detialPanel = RegistryDetailPanel.delItem;
	}
	
	@Override
	public void endDelRegistryItem(ActionEvent event){
		// 删除结点
		RegistryItem item = getSelectItem();
		try {
			registry.removeItem(item.getPath());
		} catch (RegistryException e) {
			throw new JsfbootBeanException (e);
		}
		treeRoot.removeChildOfItem(item.getPath());
		
		// 重置面板
		selectPath = null;
		detialPanel = RegistryDetailPanel.registryDetail;
	}
	
	@Override
	public void toRegistryItemView(ActionEvent event){
		if (selectPath == null)
			detialPanel = RegistryDetailPanel.registryDetail;
		else
			detialPanel = RegistryDetailPanel.itemDetail;
	}
	
	@Override
	public RegistryItemForm getRegistryItemFrom() {
		if (registryItemForm == null){
			registryItemForm = new RegistryItemForm();
		}
		return registryItemForm;
	}
	
	@Override
	public List<ItemAttribute> getRegistryItemAttrs() {
		// 重新配对
		if (path4attrs == null || path4attrs.getKey() != selectPath){
			path4attrs = new ItemAttrPair (selectPath, null);
		}
		
		// 重新生成属性数组
		if (path4attrs.getValue() == null){
			
			List<ItemAttribute> attrs = new ArrayList<ItemAttribute>();
			path4attrs.setValue(attrs);
			RegistryItem item = getSelectItem ();
			for (String name : item.valueNames()){
				attrs.add(new ItemAttribute (name, item.getValue(name)));
			}
		}
		
		// 返回数组
		return path4attrs.getValue();
	}

	@Override
	public void beginAddItemAttr(ActionEvent event) {
		attributeForm = null;
		detialPanel = RegistryDetailPanel.addAttr;
	}

	@Override
	public void endAddItemAttr(ActionEvent event) {
		
		String name = attributeForm.getName();
		String value = attributeForm.getValue();
		
		RegistryItem item = getSelectItem();
		if (item.hasValue(name)){
			FacesUtils.addMessageError("添加属性", name + " 已存在，请重新输入一个名称。");
			return;
		}
		
		item.setValue(name, value);
		try {
			registry.updateItem(item);
		} catch (RegistryException e) {
			throw new JsfbootBeanException (e);
		}
		
		if (path4attrs != null)
			path4attrs.setValue(null);
		toRegistryItemView(event);
	}

	@Override
	public void beginEditItemAttr(ActionEvent event) {
		ItemAttribute attr = (ItemAttribute) event.getComponent().getAttributes().get("attr");
		if (attr == null)
			return;
		this.attributeForm = null;
		if (StringKit.isEmpty(attr.getName()))
			getAttributeForm().setNotName(true);
		else
			getAttributeForm().setOldName(attr.getName());
		getAttributeForm().setValue(attr.getValue());
		detialPanel = RegistryDetailPanel.editAttr;
	}

	@Override
	public void endEditItemAttr(ActionEvent event) {
		
		String name = attributeForm.getName();
		String value = attributeForm.getValue();
		RegistryItem item = getSelectItem();
		
		// 设置缺省值
		if (getAttributeForm().isNotName()){
			item.setDefaultValue(value);
		}else{
			boolean rename = name.equals(attributeForm.getOldName()) == false;
			// 判断新名称是否已存在
			if (rename == true){
				if (item.hasValue(name)){
					FacesUtils.addMessageError("修改属性", name + " 已存在，请重新输入一个名称。");
					return;
				}
			}
			
			// 更新值
			item.setValue(name, value);
			
			if (rename == true){
				item.removeValue(attributeForm.getOldName());
			}
		}
		
		try {
			registry.updateItem(item);
		} catch (RegistryException e) {
			throw new JsfbootBeanException (e);
		}

		if (path4attrs != null)
			path4attrs.setValue(null);
		toRegistryItemView(event);
	}

	public ItemAttributeForm getAttributeForm() {
		if (attributeForm == null){
			attributeForm = new ItemAttributeForm();
			attributeForm.setSelectPath(selectPath);
		}
		return attributeForm;
	}

	@Override
	public void doDelItemAttr(ActionEvent event) {
		ItemAttribute attr = (ItemAttribute) event.getComponent().getAttributes().get("attr");
		if (attr == null)
			return;
		
		RegistryItem item = this.getSelectItem();
		item.removeValue(attr.getName());
		
		try {
			registry.updateItem(item);
		} catch (RegistryException e) {
			throw new JsfbootBeanException (e);
		}
		if (path4attrs != null)
			path4attrs.setValue(null);
	}

	@Override
	public RegistryProfile getRegProfile() {
		return registry.getProfile();
	}
}
