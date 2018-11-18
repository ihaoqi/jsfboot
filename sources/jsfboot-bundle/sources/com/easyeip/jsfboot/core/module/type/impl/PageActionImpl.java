package com.easyeip.jsfboot.core.module.type.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.type.PageAction;
import com.easyeip.jsfboot.core.module.type.schema.PageActionType;
import com.easyeip.jsfboot.utils.StringKit;

public class PageActionImpl implements PageAction {

	private JsfbootModule module;
	private PageActionType pat;
	private List<String> publicPages;
	
	public PageActionImpl(JsfbootModule module, PageActionType pat) {
		this.module = module;
		this.pat = pat;
	}

	@Override
	public String getName() {
		return pat.getName();
	}

	@Override
	public String getTitle() {
		return pat.getTitle();
	}

	@Override
	public String getIcon() {
		return pat.getIcon();
	}

	@Override
	public String getOutcome() {
		return pat.getOutcome();
	}

	@Override
	public List<String> getPublicPages() {
		if (publicPages == null){
			publicPages = new ArrayList<String> ();
			for (String p : StringKit.stringSplit(pat.getPublicPages(), ",")){
				publicPages.add(OutcomeUtils.fullOutcome(module, p));
			}
		}
		return publicPages;
	}

}
