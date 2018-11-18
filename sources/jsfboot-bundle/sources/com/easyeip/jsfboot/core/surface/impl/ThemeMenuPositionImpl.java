package com.easyeip.jsfboot.core.surface.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.surface.ThemeMenuPosition;

public class ThemeMenuPositionImpl implements ThemeMenuPosition {
	
	private Map<String, String> themePlaceholder;
	
	public ThemeMenuPositionImpl(){
		themePlaceholder = new HashMap<String, String>();
	}
	
	@Override
	public Map<String, String> getPairMap() {
		return themePlaceholder;
	}

	@Override
	public boolean pairMenuBar(String positionName, String menuBarName) {
		if (StringKit.isEmpty(menuBarName))
			themePlaceholder.remove(positionName);
		else
			themePlaceholder.put(positionName, menuBarName);
		return true;
	}

	@Override
	public void copyForm(ThemeMenuPosition pos) {
		themePlaceholder.clear();
		Map<String, String> map = pos.getPairMap();
		for (Entry<String, String> entry : map.entrySet()){
			pairMenuBar(entry.getKey(), entry.getValue());
		}
	}
}
