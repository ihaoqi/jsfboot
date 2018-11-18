package com.easyeip.jsfboot.admin.help.impl;

import com.easyeip.jsfboot.admin.help.DashboardGadget;
import com.easyeip.jsfboot.utils.KeyValuePair;

public class DashboardGadgetImpl implements DashboardGadget {
	
	private int position;
	private String title;
	private String icon;
	private String url;
	private String outcome;
	private KeyValuePair param;
	
	public DashboardGadgetImpl(int position, String title, String icon){
		this.position = position;
		this.title = title;
		this.icon = icon;
	}

	@Override
	public int getPosition() {
		return position;
	}

	@Override
	public String getTitle() {
		return title;
	}

	@Override
	public String getIcon() {
		return icon;
	}

	@Override
	public String getUrl() {
		return url;
	}

	@Override
	public KeyValuePair getParam() {
		return param;
	}

	@Override
	public DashboardGadget setParam(String name, String value) {
		this.param = new KeyValuePair (name, value);
		return this;
	}

	@Override
	public DashboardGadget setUrl(String url) {
		this.url = url;
		return this;
	}

	@Override
	public String getOutcome() {
		return outcome;
	}

	@Override
	public DashboardGadget setOutcome(String outcome) {
		this.outcome = outcome;
		return this;
	}

}
