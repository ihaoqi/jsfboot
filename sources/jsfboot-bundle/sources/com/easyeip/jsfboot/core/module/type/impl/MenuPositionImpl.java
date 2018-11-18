package com.easyeip.jsfboot.core.module.type.impl;

import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.module.type.schema.PositionItemType;

public class MenuPositionImpl implements MenuPosition {
	
	private String name;
	private String explain;
	
	public MenuPositionImpl(PositionItemType item){
		this.name = item.getName();
		this.explain = item.getTitle();
	}
	
	public MenuPositionImpl(String name, String explain){
		this.name = name;
		this.explain = explain;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getTitle() {
		return explain;
	}

}
