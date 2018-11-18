package com.easyeip.jsfboot.admin.dictionary.bean;

import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import com.easyeip.jsfboot.admin.dictionary.AdminDictionaryService;
import com.easyeip.jsfboot.admin.dictionary.form.AddDictionaryForm;
import com.easyeip.jsfboot.admin.dictionary.form.DictOperPanel;
import com.easyeip.jsfboot.admin.dictionary.form.EditDictionaryForm;
import com.easyeip.jsfboot.admin.dictionary.type.DictDataTable;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryException;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryType;
import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.utils.StringKit;

public class AdminDictionaryBeanImpl extends JsfbootBean implements AdminDictionaryBean {
	
	@UseJsfbootService(AdminDictionaryService.class)
	AdminDictionaryService service;		// 字典服务
	
	List<DictionaryItem> dictCacheItems;// 字典列表缓存
	
	DictOperPanel operPanel;		// 字典操作面板
	AddDictionaryForm addForm;		// 添加表单
	EditDictionaryForm editForm;	// 编辑表单
	DictionaryItem selItem;			// 当前选中字典
	
	public AdminDictionaryBeanImpl(){
		// 默认显示提示面板
		operPanel = DictOperPanel.userTip;
	}

	@Override
	public List<DictionaryItem> getDictList() {
		if (dictCacheItems == null){
			// 把字典缓存下来
			dictCacheItems = new ArrayList<DictionaryItem>();
			for (String code : service.listDictionary()){
				dictCacheItems.add(service.loadDictionary(code));
			}
		}
		return dictCacheItems;
	}

	@Override
	public String getOperPanel() {
		return operPanel.name();
	}

	@Override
	public AddDictionaryForm getAddDictForm() {
		if (addForm == null){
			addForm = new AddDictionaryForm();
		}
		return addForm;
	}

	@Override
	public EditDictionaryForm getEditDictForm() {
		if (editForm == null || editForm.getSelItem() != selItem){
			editForm = new EditDictionaryForm(selItem);
		}
		return editForm;
	}

	@Override
	public DictionaryItem getSelDictItem() {
		return selItem;
	}
	
	@Override
	public void setSelDictItem(DictionaryItem dict){
		selItem = dict;
	}

	@Override
	public void toDictItemView() {
		if (selItem != null)
			operPanel = DictOperPanel.editDict;
		else
			operPanel = DictOperPanel.userTip;
	}

	@Override
	public void beginAddDictItem(ActionEvent event) {
		operPanel = DictOperPanel.addDict;
		addForm = null;
	}

	@Override
	public void endAddDictItem(ActionEvent event) {
		String code = addForm.getCode().trim();
		String title = addForm.getTitle().trim();
		String catalog = addForm.getCatalog().trim();
		
		if (StringKit.isEmpty(code) || StringKit.isEmpty(title)){
			FacesUtils.addMessageError("添加字典", "名称与标题不能为空。");
			return;
		}
		
		if (!NameUtils.isXMLName(code)){
			FacesUtils.addMessageError("添加字典", code + "不符合名称规范。");
			return;
		}
		
		try {
			DictionaryType dictType = DictionaryType.valueOf(addForm.getType());
			DictionaryItem dict = service.createDictionary(code, dictType);
			dict.setTitle(title);
			dict.setCatalog(catalog);
			
			DictDataTable table = dict.getTable();
			if (dictType == DictionaryType.List){
				table.addColumn("value", "内容");
				table.setRowCount(2);
				table.setValue(0, 0, "文本1");
				table.setValue(1, 0, "文本2");
			}else if (dictType == DictionaryType.Map){
				table.addColumn("key", "关键字");
				table.addColumn("value", "内容");
				table.setRowCount(2);
				table.setValue(0, 0, "key1"); table.setValue(0, 1, "内容1");
				table.setValue(1, 0, "key2"); table.setValue(1, 1, "内容2");
			}
			
			service.updateDictionary(dict);
		} catch (Exception e) {
			FacesUtils.addMessageError("添加字典", e.getMessage());
			return;
		}
		
		// 重新加载字典，选中当前字典
		toDictItemView(code);
	}
	
	/**
	 * 在列表中查找字典
	 * @param code
	 * @return
	 */
	private DictionaryItem findItemInCache (String code){
		for (DictionaryItem item : getDictList()){
			if (item.getCode().equals(code)){
				return item;
			}
		}
		
		return null;
	}

	@Override
	public void doSelectDictItem(SelectEvent event) {
		FacesUtils.clearInputInvalidState();
		toDictItemView();
	}

	@Override
	public void beginDelDictItem(ActionEvent event) {
		selItem = (DictionaryItem) event.getComponent().getAttributes().get("dict");
		operPanel = DictOperPanel.delDict;
	}

	@Override
	public void endDelDictItem(ActionEvent event) {
		try {
			service.removeDictionary(selItem.getCode());
			selItem = null;
			dictCacheItems = null;
			toDictItemView();
		} catch (DictionaryException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void doUpdateDictItem(ActionEvent event) {
		// 复制一个新的字典
		DictionaryItem newItem = selItem.convertType(selItem.getType());
	
		// 设置新的值并保存
		try {
			newItem.getTable().deleteAllRow();
			newItem.setTitle(editForm.getTitle());
			newItem.setCatalog(editForm.getCatalog());
			String newContent = editForm.getContent();
			editForm.text2Table(newContent, newItem.getTable());
			service.updateDictionary(newItem);
			FacesUtils.addMessageInfo("更新字典", editForm.getTitle() + " 保存成功。");
		} catch (Exception e) {
			FacesUtils.addMessageError("更新字典", e.getMessage());
			return;
		}
		
		// 更新显示
		toDictItemView(newItem.getCode());
	}

	void toDictItemView (String code){
		dictCacheItems = null;
		selItem = findItemInCache(code);
		toDictItemView();
	}
}
