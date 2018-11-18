package com.easyeip.jsfboot.utils;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.primefaces.context.RequestContext;

public class FacesUtils {

	public static FacesContext getFacesContext(){
		return FacesContext.getCurrentInstance();
	}
	
	public static ExternalContext getExternalContext(){
		return getFacesContext().getExternalContext();
	}
	
	/**
	 * 获取通过 f:param 传递的参数
	 * @param name
	 * @return
	 */
	public static String getFacesRequestParam(String name){
		return getFacesContext().getExternalContext().getRequestParameterMap().get(name);
	}
	
	public static HttpServletRequest getServletRequest(){
		return (HttpServletRequest) getExternalContext().getRequest();
	}
	
	public static HttpServletResponse getServletResponse(){
		return (HttpServletResponse) getExternalContext().getResponse();
	}
	
	public static void addMessageInfo(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public static void addMessageWarn(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_WARN, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	public static void addMessageError(String summary, String detail) {
        FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, summary, detail);
        FacesContext.getCurrentInstance().addMessage(null, message);
    }
	
	/**
	 * 添加ajax回调参数
	 * @param haveCompleted
	 */
	public static void setAllowCloseDialog(boolean allowCloseDialog){
		addAjaxCallbackParam("allowCloseDialog", allowCloseDialog);
	}
	
	public static void addAjaxCallbackParam(String name, Object value){
		RequestContext context = RequestContext.getCurrentInstance();
		context.addCallbackParam(name, value);
	}

	/**
	 * 清除输入组件的无效状态
	 */
	public static void clearInputInvalidState (){
		
		UIComponent viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		if (viewRoot == null) return;
		
		clearInputInvalidState(viewRoot, false);
	}
	
	/**
	 * 清除输入组件的无效状态与值
	 */
	public static void clearInputInvalidValue (){
		
		UIComponent viewRoot = FacesContext.getCurrentInstance().getViewRoot();
		if (viewRoot == null) return;
		
		clearInputInvalidState(viewRoot, true);
	}
	
	private static void clearInputInvalidState (UIComponent viewRoot, boolean clearValue){
		
		if (viewRoot == null) return;
		
		if (viewRoot instanceof UIInput){
			UIInput input = (UIInput) viewRoot;
			input.setValid(true);
			input.setSubmittedValue(null);
			if (clearValue == true){
				input.setLocalValueSet(false);
				input.resetValue();
			}
		}
		
		if (viewRoot.getChildCount() == 0)
			return;
		
		for (UIComponent ui : viewRoot.getChildren()){
			clearInputInvalidState (ui, clearValue);
		}
	}
}
