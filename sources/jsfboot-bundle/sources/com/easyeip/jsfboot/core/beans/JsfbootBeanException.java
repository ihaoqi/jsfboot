package com.easyeip.jsfboot.core.beans;

public class JsfbootBeanException extends IllegalStateException {

	private static final long serialVersionUID = 1L;
	
	public JsfbootBeanException(){
		
	}
	
	public JsfbootBeanException(String message){
		super(message);
	}
	
	public JsfbootBeanException(Exception e){
		super(e);
	}

}
