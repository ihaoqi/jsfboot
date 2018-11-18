package com.easyeip.jsfboot.utils;

import javax.el.ELResolver;
import javax.faces.context.FacesContext;
//import javax.servlet.ServletContext;
//import javax.servlet.http.HttpServletRequest;

//import org.springframework.beans.factory.BeanFactory;
//import org.springframework.web.context.WebApplicationContext;

/**
 * 用于获取jsf bean的值
 * @author liaochenghui
 *
 */
public class JsfBeanUtils
{
//	private static BeanFactory springBeanFactory;
	
	/**
	 * 获取EL值，用法： getBean("UserNavigation");
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName)
	{
		FacesContext fc = FacesContext.getCurrentInstance();
		if (fc != null)
			return getManagedBean (fc, beanName);
//		else
//			return getSpringBean (beanName);
		return null;
	}
	
	/**
	 * 获取EL值，用法： getMBean(context, "UserNavigation");
	 * @param beanName
	 * @return
	 */
	public static Object getManagedBean(FacesContext context, String beanName)
	{
		ELResolver elr = context.getApplication().getELResolver();
		Object value = elr.getValue(context.getELContext(), null, beanName);
		
		return value;
	}

	/**
	 * 计算EL表达式，如  evalExpr(context, "#{groupManage.addGroupInfo}")
	 * @param context
	 * @param el_expr
	 * @return
	 */
	public static Object evalExpr(FacesContext context, String el_expr)
	{
        // 方法 一
		return context.getApplication().evaluateExpressionGet(
				context, el_expr, Object.class);
		
		// 方法 二
//        ELContext elContext = context.getELContext();
//        ExpressionFactory expressionFactory = context.getApplication().getExpressionFactory();
//        ValueExpression ve = expressionFactory.createValueExpression(elContext, el_expr, Object.class);
//        return ve.getValue(elContext);
	}
	
	/**
	 * 计算EL表达式，如  evalExpr("#{groupManage.addGroupInfo}")
	 * @param el_expr
	 * @return
	 */
	public static Object evalExpr(String el_expr)
	{
		FacesContext context = FacesContext.getCurrentInstance();
		return evalExpr(context, el_expr);
	}
	
	/**
	 * 与evalExpr相同，但会屏蔽异常，发生异常就返回原值
	 * @param el_expr
	 * @return
	 */
	public static Object evalExprNE(String el_expr)
	{
		try{
			FacesContext context = FacesContext.getCurrentInstance();
			return evalExpr(context, el_expr);
		}catch(Exception e){
			return el_expr;
		}
	}
	
//	/**
//	 * 从当前Portal环境中取得spring中配置的bean
//	 * @param beanName
//	 * @return
//	 */
//	public static Object getSpringBean(String beanName)
//	{
//		if (springBeanFactory != null)
//			return springBeanFactory.getBean(beanName);
//		
//		FacesContext facesContext = FacesContext.getCurrentInstance();
//		if (facesContext == null)
//			return null;
//		
//		HttpServletRequest req = (HttpServletRequest) facesContext.getExternalContext().getRequest();
//		return getSpringBean (req.getServletContext(), beanName);
//	}
	
//	public static Object getSpringBean(ServletContext servletContext, String beanName)
//	{
//		// 取得spring
//		if (springBeanFactory == null){
//	        springBeanFactory = (WebApplicationContext)servletContext.getAttribute(
//                		WebApplicationContext.ROOT_WEB_APPLICATION_CONTEXT_ATTRIBUTE);
//		}
//        
//		// 取得bean
//		if (springBeanFactory != null)
//			return springBeanFactory.getBean(beanName);
//		return null;
//	}
}
