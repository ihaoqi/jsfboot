package com.easyeip.jsfboot.core.module.type;

public class EmptyThemeTemplate implements ThemePageTemplate {
	
	static final String EMPTY_TEMPLATE = "/jsfboot-bundle/empty-template.xhtml";

	@Override
	public String getMain() {
		return EMPTY_TEMPLATE;
	}

	@Override
	public String getContent() {
		return EMPTY_TEMPLATE;
	}

	@Override
	public String getBlank() {
		return EMPTY_TEMPLATE;
	}

}
