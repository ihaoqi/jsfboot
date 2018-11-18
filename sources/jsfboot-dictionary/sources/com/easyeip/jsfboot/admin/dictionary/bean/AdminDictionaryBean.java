package com.easyeip.jsfboot.admin.dictionary.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.SelectEvent;

import com.easyeip.jsfboot.admin.dictionary.form.AddDictionaryForm;
import com.easyeip.jsfboot.admin.dictionary.form.EditDictionaryForm;
import com.easyeip.jsfboot.admin.dictionary.type.DictionaryItem;

/**
 * 字典管理bean
 * @author ihaoqi
 *
 */
public interface AdminDictionaryBean {

	/**
	 * 获取字段列表
	 * @return
	 */
	List<DictionaryItem> getDictList();
	
	/**
	 * 当前操作类型
	 * @return
	 */
	String getOperPanel();
	
	/**
	 * 获取添加表单
	 * @return
	 */
	AddDictionaryForm getAddDictForm();
	
	/**
	 * 获取编辑表单
	 * @return
	 */
	EditDictionaryForm getEditDictForm();
	
	/**
	 * 获取当前选中字典
	 * @return
	 */
	DictionaryItem getSelDictItem();
	void setSelDictItem(DictionaryItem dict);
	
	/**
	 * 还原操作面板
	 * @param event
	 */
	void toDictItemView();
	
	/**
	 * 开始添加字典
	 * @param event
	 */
	void beginAddDictItem(ActionEvent event);
	void endAddDictItem(ActionEvent event);
	
	/**
	 * 开始删除字典
	 * @param event
	 */
	void beginDelDictItem(ActionEvent event);
	void endDelDictItem(ActionEvent event);
	
	/**
	 * 选中一个结点
	 * @param event
	 */
	void doSelectDictItem(SelectEvent event);
	
	/**
	 * 更新当前修改的值
	 * @param event
	 */
	void doUpdateDictItem(ActionEvent event);
}
