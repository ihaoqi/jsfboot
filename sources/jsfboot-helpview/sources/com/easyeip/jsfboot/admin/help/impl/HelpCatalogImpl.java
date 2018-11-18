package com.easyeip.jsfboot.admin.help.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.admin.help.HelpPage;

public class HelpCatalogImpl implements HelpCatalog {
	
	private HelpCatalog parent;
	private String name;
	private String title;
	private List<HelpCatalog> childs;
	private HelpPage page;
	
	public HelpCatalogImpl (String name, String title){
		this(name, title, null);
	}
	
	public HelpCatalogImpl (String name, String title, HelpPage page){
		this.name = name;
		this.title = title;
		this.page = page;
		
		childs = new ArrayList<HelpCatalog> ();
	}
	
	@Override
	public HelpCatalog getParent(){
		return parent;
	}
	
	public void setParent(HelpCatalog parent){
		this.parent = parent;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public List<HelpCatalog> getChilds() {
		return Collections.unmodifiableList(childs);
	}

	@Override
	public HelpPage getPage() {
		return page;
	}
	
	@Override
	public void setPage(HelpPage page) {
		this.page = page;
	}

	@Override
	public HelpCatalog addChild (HelpCatalog child){
		((HelpCatalogImpl)child).setParent(this);
		childs.add(child);
		return child;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getFullPath() {
		if (parent == null){
			return "";
		}else{
			return getParent().getFullPath() + "/" + name;
		}
	}
}
