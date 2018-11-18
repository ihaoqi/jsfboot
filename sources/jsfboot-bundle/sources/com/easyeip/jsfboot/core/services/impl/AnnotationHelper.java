package com.easyeip.jsfboot.core.services.impl;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.core.annotation.UseJsfManagedBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.services.ServiceManager;
import com.easyeip.jsfboot.utils.JsfBeanUtils;

/**
 * 注解处理器
 * @author ihaoqi
 *
 */
public class AnnotationHelper {
	
	/**
	 * 设置UseJsfbootService注解
	 * @param serviceManager
	 * @param bean
	 * @throws Exception
	 */
	public static void setUseJsfbootServiceAnnot(ServiceManager serviceManager, Object object) throws Exception{
		for (Entry<Field, UseJsfbootService> annot : parseUseJsfbootService(object).entrySet()){
			
			// 取出要使用服务名称，如果已初使化则直接使用
			UseJsfbootService annoObj = annot.getValue();
			String descName = annoObj.value().getName();
			Object descService = serviceManager.getService(descName);
			
			if (descService == null){
				throw new Exception (descName + " 服务不存在。");
			}
			
			// 设置属性
			annot.getKey().setAccessible(true);
			annot.getKey().set(object, descService);
		}
	}
	
	/**
	 * 设置UseJsfbootService注解
	 * @param serviceManager
	 * @param bean
	 * @throws Exception
	 */
	public static void setUseJsfManagedBeanAnnot(ServiceManager serviceManager, Object object) throws Exception{
		for (Entry<Field, UseJsfManagedBean> annot : parseUseJsfManagedBean(object).entrySet()){
			
			// 取出要使用服务名称，如果已初使化则直接使用
			UseJsfManagedBean annoObj = annot.getValue();
			String elexpr = annoObj.value().trim();
			
			if (!(elexpr.startsWith("#{") && elexpr.endsWith("}"))){
				elexpr = "#{" + elexpr + "}";
			}
			
			Object value = JsfBeanUtils.evalExpr(elexpr);
			
			// 设置属性
			annot.getKey().setAccessible(true);
			annot.getKey().set(object, value);
		}
	}
	
	/**
	 * 分析UseJsfbootService注解数量
	 * @param obj
	 * @return
	 */
	private static Map<Field, UseJsfbootService> parseUseJsfbootService (Object obj){
		
		Map<Field, UseJsfbootService> map = new HashMap<Field, UseJsfbootService>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (final Field field : fields) {
			
			// @UseJsfbootService
            final UseJsfbootService annot = field.getAnnotation(UseJsfbootService.class);
            if (annot != null) {
            	map.put(field,  annot);
            }
        }
		
		return map;
	}
	
	/**
	 * 分析UseJsfManagedBean注解数量
	 * @param obj
	 * @return
	 */
	private static Map<Field, UseJsfManagedBean> parseUseJsfManagedBean (Object obj){
		
		Map<Field, UseJsfManagedBean> map = new HashMap<Field, UseJsfManagedBean>();
		
		Field[] fields = obj.getClass().getDeclaredFields();
		for (final Field field : fields) {
			
			// @UseJsfbootService
            final UseJsfManagedBean annot = field.getAnnotation(UseJsfManagedBean.class);
            if (annot != null) {
            	map.put(field,  annot);
            }
        }
		
		return map;
	}
}
