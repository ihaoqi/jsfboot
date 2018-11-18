package com.easyeip.jsfboot.web.faces;

import java.util.Date;

import org.primefaces.application.exceptionhandler.ExceptionInfo;

public class ExceptionInfoWrapper extends ExceptionInfo {

	private static final long serialVersionUID = 1L;
	
	private ExceptionInfo wrapper;
	
	ExceptionInfoWrapper (ExceptionInfo wrapper){
		this.wrapper = wrapper;
	}
	
	@Override
	public Throwable getException() {
		return wrapper.getException();
	}

	@Override
	public void setException(Throwable exception) {
		wrapper.setException(exception);
	}

	@Override
	public String getType() {
		return wrapper.getType();
	}

	@Override
	public void setType(String type) {
		wrapper.setType(type);
	}

	@Override
	public String getMessage() {
		return wrapper.getMessage();
	}

	@Override
	public void setMessage(String message) {
		wrapper.setMessage(message);
	}

	@Override
	public StackTraceElement[] getStackTrace() {
		return wrapper.getStackTrace();
	}

	@Override
	public void setStackTrace(StackTraceElement[] stackTrace) {
		wrapper.setStackTrace(stackTrace);
	}

	@Override
	public Date getTimestamp() {
		return wrapper.getTimestamp();
	}

	@Override
	public void setTimestamp(Date timestamp) {
		wrapper.setTimestamp(timestamp);
	}

	@Override
	public String getFormattedStackTrace() {
		return wrapper.getFormattedStackTrace();
	}

	@Override
	public void setFormattedStackTrace(String formattedStackTrace) {
		wrapper.setFormattedStackTrace(formattedStackTrace);
	}

	@Override
	public String getFormattedTimestamp() {
		return wrapper.getFormattedTimestamp();
	}

	@Override
	public void setFormattedTimestamp(String formattedTimestamp) {
		wrapper.setFormattedTimestamp(formattedTimestamp);
	}
}
