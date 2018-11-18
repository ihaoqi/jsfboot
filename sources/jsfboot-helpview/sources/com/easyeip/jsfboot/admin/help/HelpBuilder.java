package com.easyeip.jsfboot.admin.help;

import com.easyeip.jsfboot.core.module.JsfbootModule;

public interface HelpBuilder {
	
	HelpCatalog newCatalog(String name, String title);
	HelpCatalog newCatalog(String name, String title, HelpPage page);
	
	HelpPage includePage(String url);
	
	String fullModulePath(JsfbootModule module, String pagePath);
}
