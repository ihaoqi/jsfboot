package com.easyeip.jsfboot.admin.help.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.admin.AdminHelpViewService;
import com.easyeip.jsfboot.admin.help.DashboardGadget;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.admin.help.HelpCatalogView;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.PageDomainType;

public class AdminHelpViewServiceImpl extends GenericService implements AdminHelpViewService {

	private JsfbootModule module;
	private HelpBuilder builder;
	
	private HelpCatalog adminRootCatalog;
	private HelpCatalog siteRootCatalog;
	
	private List<DashboardGadget> gadgetList;
	
	public AdminHelpViewServiceImpl(){
		adminRootCatalog = new HelpCatalogImpl (null, "帮助", null);
		siteRootCatalog = new HelpCatalogImpl (null, "帮助", null);
		gadgetList = new ArrayList<DashboardGadget>();
	}
	
	/**
	 * 取得所在的模块
	 * @return
	 */
	public JsfbootModule getModule(){
		return module;
	}
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		
		module = context.getModule();

		addDashboardGadget("在线帮助", "fa fa-info-circle").
				setOutcome(OutcomeUtils.fullOutcome(module, "help"));
		
	}

	@Override
	public HelpBuilder getHelpBuilder() {
		if (builder == null){
			builder = new HelpBuilderImpl();
		}
		
		return builder;
	}

	@Override
	public HelpCatalog addHelpDocument(PageDomainType domain, HelpCatalog catalog) {
		if (domain == PageDomainType.Admin)
			adminRootCatalog.addChild(catalog);
		else
			siteRootCatalog.addChild(catalog);
		return catalog;
	}

	@Override
	public HelpCatalogView getRootCatalog(PageDomainType domain) {
		if (domain == PageDomainType.Admin)
			return adminRootCatalog;
		else
			return siteRootCatalog;
	}
	
	@Override
	public DashboardGadget addDashboardGadget(String title, String icon) {
		DashboardGadget gadget = new DashboardGadgetImpl(gadgetList.size(), title, icon);
		gadgetList.add(gadget);
		return gadget;
	}

	@Override
	public List<DashboardGadget> getDashboardGadgets() {
		return Collections.unmodifiableList(gadgetList);
	}
}
