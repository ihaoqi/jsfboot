package com.easyeip.jsfboot.core.registry.service;

import java.util.List;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryProfile;
import com.easyeip.jsfboot.core.registry.RegistryService;

public class RegistryServiceWrapper implements RegistryService {
	
	private RegistryService wrapper;
	
	public RegistryServiceWrapper (RegistryService wrapper){
		setWrapper(wrapper);
	}
	
	public void setWrapper(RegistryService wrapper){
		this.wrapper = wrapper;
	}
	
	public RegistryService getWrapper(){
		return wrapper;
	}
	
	@Override
	public RegistryProfile getProfile() {
		return wrapper.getProfile();
	}

	@Override
	public RegistryPath getRootPath() {
		return wrapper.getRootPath();
	}

	@Override
	public void removeItem(RegistryPath path) throws RegistryException {
		wrapper.removeItem(path);
	}

	@Override
	public RegistryItem getItem(RegistryPath path) {
		return wrapper.getItem(path);
	}
	
	@Override
	public RegistryItem useItem(RegistryPath path) throws RegistryException {
		return wrapper.useItem(path);
	}

	@Override
	public List<RegistryItem> listChildren(RegistryPath path) {
		return wrapper.listChildren(path);
	}

	@Override
	public Iterable<RegistryItem> allChildren(RegistryPath path) {
		return wrapper.allChildren(path);
	}

	@Override
	public RegistryItem createItem(RegistryPath path) throws RegistryException {
		return wrapper.createItem(path);
	}

	@Override
	public void updateItem(RegistryItem item) throws RegistryException {
		wrapper.updateItem(item);
	}
}
