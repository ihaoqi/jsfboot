package com.easyeip.jsfboot.admin.help.impl;

import com.easyeip.jsfboot.admin.help.PageLoadType;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.admin.help.HelpPage;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;

public class HelpBuilderImpl implements HelpBuilder {

	@Override
	public HelpCatalog newCatalog(String name, String title) {
		return new HelpCatalogImpl(name, title);
	}

	@Override
	public HelpCatalog newCatalog(String name, String title, HelpPage page) {
		return new HelpCatalogImpl(name, title, page);
	}

	@Override
	public HelpPage includePage(String url) {
		return new HelpPageImpl (url, PageLoadType.INCLUDE);
	}

	@Override
	public String fullModulePath(JsfbootModule module, String pagePath) {
		return OutcomeUtils.fullOutcome(module, pagePath);
	}
}
