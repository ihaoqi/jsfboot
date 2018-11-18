package com.easyeip.jsfboot.core.beans;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.faces.event.ActionEvent;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.services.impl.AnnotationHelper;
import com.easyeip.jsfboot.utils.FacesUtils;

/**
 * jsf bean基类
 * @author ihaoqi
 *
 */
public class JsfbootBean {
		
	private Map<String, Object> propBuffer;
	
	public JsfbootBean(){
		propBuffer = new HashMap<String,Object> ();
		initBeanAnnotation (this);
		initRequestParameter ();
	}

	/**
	 * 清除界面上的输入错误状态
	 * @param event
	 */
	public void clearInputInvalidState(ActionEvent event){
		FacesUtils.clearInputInvalidState();
	}
	
	/**
	 * 清除界面上的输入错误状态与输入值
	 * @param event
	 */
	public void clearInputInvalidValue(ActionEvent event){
		FacesUtils.clearInputInvalidValue();
	}

	//@PostConstruct
	private void initBeanAnnotation(Object bean){
		
		try {
			// 设置 jsfboot service
			AnnotationHelper.setUseJsfbootServiceAnnot(
					JsfbootContext.getDriver().getServiceManager(), bean);
			
			// 设置 jsf managedbean
			AnnotationHelper.setUseJsfManagedBeanAnnot(
					JsfbootContext.getDriver().getServiceManager(), bean);
		} catch (Exception e) {
			throw new RuntimeException (e);
		}
		
	}

	/**
	 * 设置bean属性（缓存）
	 * @param key
	 * @param value
	 */
	protected void setBeanProperty (String key, Object value){
		propBuffer.put(key, value);
	}
	
	/**
	 * 读取bean属性（缓存）
	 * @param key
	 * @return
	 */
	@SuppressWarnings("unchecked")
	protected <T> T getBeanProperty (String key){
		return (T) propBuffer.get(key);
	}
	
	/**
	 * 清除指定bean属性（缓存）
	 * @param key
	 */
	protected void removeBeanProperty (String key){
		propBuffer.remove(key);
	}
	
	/**
	 * 把请求的参数设置到属性值
	 */
	private void initRequestParameter(){
		for (Entry<String,String> entry : FacesUtils.getExternalContext().getRequestParameterMap().entrySet()){
			setBeanProperty (entry.getKey(), entry.getValue());
		}
	}
}
