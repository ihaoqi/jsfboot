package com.easyeip.jsfboot.web.faces;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class JsfbootExceptionHandlerFactory extends ExceptionHandlerFactory {

	private final ExceptionHandlerFactory wrapped;

	public JsfbootExceptionHandlerFactory(final ExceptionHandlerFactory wrapped) {
		this.wrapped = wrapped;
	}

	@Override
	public ExceptionHandler getExceptionHandler() {
		return new JsfbootExceptionHandler(wrapped.getExceptionHandler());
	}

	@Override
	public ExceptionHandlerFactory getWrapped() {
		return wrapped;
	}

}
