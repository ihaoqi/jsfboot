package com.easyeip.jsfboot.admin.help;

import java.util.List;

public interface HelpCatalogView {
	String getName();
	String getTitle();
	String getFullPath();
	
	List<HelpCatalog> getChilds();
	
	HelpCatalog getParent();
	
	HelpPage getPage();
}
