package com.easyeip.jsfboot.core.surface.impl;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.menu.DefaultMenuItem;
import org.primefaces.model.menu.MenuElement;

public class DefaultMenuItemEx extends DefaultMenuItem {

	private static final long serialVersionUID = 1L;

	public int getElementsCount(){
    	return 0;
    }
    
    public List<MenuElement> getElements(){
    	return new ArrayList<MenuElement>();
    }
}
