package com.easyeip.jsfboot.core.services;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.options.type.ServiceParam;

public class ServiceUtils {
	
	public static Object getService (Class<?> serviceClazz){
		return JsfbootContext.getDriver().getServiceManager().getService(serviceClazz);
	}

	/**
	 * 把一个对象转换成 JsfbootService
	 * @param service
	 * @return 返回JsfbootService对象，如果失败返回null
	 */
	public static JsfbootService toJsfbootService (Object service) {
		if (service instanceof JsfbootService)
			return (JsfbootService) service;
		
		return null;
	}
	
	/**
	 * 取得文本格式的服务参数
	 * @param context
	 * @param paramName
	 * @return
	 */
	public static String getParamTextValue (ServiceContext context, String paramName){
		ServiceParam param = context.getServiceConf().getParam(paramName);
		if (param == null)
			return null;
		return param.getValue();
	}
	
	public static String getParamTextValue (ServiceContext context, String paramName, String defValue){
		String value = getParamTextValue(context, paramName);
		if (StringKit.isEmpty(value)){
			return defValue;
		}
		return value;
	}
	
	public static long getParamLongValue (ServiceContext context, String paramName, long defValue){
		String value = getParamTextValue (context, paramName);
		if (StringKit.isEmpty(value)){
			return defValue;
		}
		
		try{
			return Long.valueOf(value);
		}catch (Exception e){
			return defValue;
		}
	}
	
	/**
	 * 从服务参数中创建对象
	 * @param context
	 * @param paramName
	 * @return
	 * @throws Exception
	 */
	public static Object getParamObjectValue (ServiceContext context,
								String paramName) throws Exception{
		ServiceParam param = context.getServiceConf().getParam(paramName);
		if (param == null)
			throw new Exception ("缺少 " + paramName + " 参数。");
		
		return getParamObjectValue(context, param);
	}
	
	/**
	 * 从服务参数中创建对象
	 * @param context
	 * @param param
	 * @return
	 * @throws Exception
	 */
	public static Object getParamObjectValue (ServiceContext context,
								ServiceParam param) throws Exception{
		
		Object result = null;
		
		// 查找服务
		String ref_service = param.getRefService();
		if (!StringKit.isEmpty(ref_service)){
			result = context.getDriver().getServiceManager().getService(ref_service);
			if (result == null)
				throw new Exception ("服务 '" + ref_service + "' 不存在。");
		}
		
		// 查找bean
		String ref_bean = param.getRefBean();
		if (result == null && !StringKit.isEmpty(ref_bean)){
			result = context.getDriver().getAppBean(ref_bean);
			if (result == null)
				throw new Exception ("Bean '" + ref_bean + "' 不存在。");
		}
		
		// 创建对象
		String ref_class = param.getValue();
		if (result == null && !StringKit.isEmpty(ref_class)){
			Class<?> clazz = context.getClass().getClassLoader().loadClass(ref_class);
			result = clazz.newInstance();
		}
		
		if (result == null){
			throw new Exception (param.getName() + " 参数缺少值。");
		}
		
		return result;
	}

}
