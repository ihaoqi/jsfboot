package com.easyeip.jsfboot.user.registry.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.secutiry.ppm.DefaultPermission;
import com.easyeip.jsfboot.core.secutiry.ppm.MenuPermission;
import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.utils.StringKit;

public class RegistryAccountRole implements RoleDetails {
	
	RegistryService registry;
	RegistryItem regItem;
	
//	String id;
	String code;
	String title;
	String explain;
	
	List<MenuPermission> permList;
	
	public RegistryAccountRole(){
		this.regItem = null;
//		this.id = null;
	}
	
	public RegistryAccountRole(RegistryService registry, RegistryItem regItem) {
		this.registry = registry;
		this.regItem = regItem;
		
//		this.id = regItem.getName();
		this.code = regItem.getName();
		this.title = regItem.getDefaultValue();
		this.explain = regItem.getValue("explain");
	}
	
	public RegistryItem getRegistryItem() {
		return regItem;
	}
	
//	@Override
//	public String getId() {
//		return id;
//	}

	@Override
	public String getCode() {
		return code;
	}

	@Override
	public String getTitle() {
		return title;
	}
	
	@Override
	public String getExplain() {
		return explain;
	}

	@Override
	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public List<MenuPermission> getPermissions() {
		if (permList != null)
			return permList;
		
		permList = new ArrayList<MenuPermission>();
			
		if (regItem == null)
			return permList;
		
		// 从注册表中加载权限信息
		for (RegistryItem item : registry.allChildren(regItem.getPath())){
			
			DefaultPermission perm = new DefaultPermission ();
			perm.setPageDomain(PageDomainType.valueOf(item.getValue("pageDomain")));
			perm.setMenuPosition(item.getValue("menuPosition"));
			perm.setMenuName(item.getValue("menuName"));
			perm.setGrantActions(item.getValue("grantActions"));
			perm.setGrantAllAction(StringKit.isTrue(item.getValue("isGrantAllAction"),
									perm.getGrantActions().size() == 0));
			
			permList.add(perm);
		}

		return permList;
	}

	@Override
	public void addPermission(MenuPermission perm) {
		// 移除已存在的权限
		List<MenuPermission> removeList = new ArrayList<MenuPermission>();
		for (MenuPermission p : getPermissions()){
			if (StringKit.equals(p.getKey(), perm.getKey())){
				removeList.add(p);
			}
		}
		
		for (MenuPermission p : removeList){
			permList.remove(p);
		}
		
		permList.add(perm);
	}
	
	@Override
	public boolean removePermission(String permKey) {
		for (MenuPermission p : getPermissions()){
			if (StringKit.equals(p.getKey(), permKey)){
				permList.remove(p);
				return true;
			}
		}
		
		return false;
	}
	
	public static void save(RegistryService registry, RegistryItem regItem,
							RoleDetails accRole) throws RegistryException{
		
		// 保存标题
		regItem.setDefaultValue(accRole.getTitle());
		regItem.setValue("explain", accRole.getExplain());
		registry.updateItem(regItem);
		
		// 更新权限
		Map<String, RegistryItem> childItem = mapRegistryChildren(registry, regItem.getPath());
		for (MenuPermission p : accRole.getPermissions()){
			RegistryItem existItem = childItem.get(p.getKey());
			if (existItem == null){
				// 添加
				RegistryPath permPath = regItem.getPath().makeChild(p.getKey());
				existItem = registry.createItem(permPath);
			}else{
				// 更新
				childItem.remove(p.getKey());
			}
			
			// 保存权限
			existItem.setValue("pageDomain", p.getPageDomain().name());
			existItem.setValue("menuPosition", p.getMenuPosition());
			existItem.setValue("menuName", p.getMenuName());
			existItem.setValue("isGrantAllAction", Boolean.valueOf(p.isGrantAllAction()).toString());
			existItem.setValue("grantActions", StringKit.listToString(p.getGrantActions(), ","));
			registry.updateItem(existItem);
		}
		
		// 移除不存在的权限
		for (Entry<String,RegistryItem> e : childItem.entrySet()){
			registry.removeItem(e.getValue().getPath());
		}
	}
	
	private static Map<String, RegistryItem> mapRegistryChildren (RegistryService registry, RegistryPath path){
		
		Map<String, RegistryItem> result = new HashMap<String, RegistryItem>();
		
		for (RegistryItem item : registry.allChildren(path)){
			result.put(item.getName(), item);
		}
		
		return result;
	}

	@Override
	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public void setTitle(String title) {
		this.title = title;
	}
}
