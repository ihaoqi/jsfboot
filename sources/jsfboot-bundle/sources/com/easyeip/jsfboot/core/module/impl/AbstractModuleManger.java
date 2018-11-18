package com.easyeip.jsfboot.core.module.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.module.type.ModuleServicePack;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.ServiceDefinition;

public abstract class AbstractModuleManger implements ModuleManager {
	
	private List<JsfbootModule> modulePackageList;
	
	private List<JsfbootTheme> moduleThemeList;
	private List<ModuleServicePack> moduleServiceList;
	private List<AccountRealmPack> userAdaptiveList;
	
	public AbstractModuleManger(){
		modulePackageList = new ArrayList<JsfbootModule> ();
	}
	
	/**
	 * 添加一个已加载的模块
	 * @param pack
	 */
	public void addModule (JsfbootModule pack){
		modulePackageList.add(pack);
	}

	@Override
	public List<JsfbootModule> getAllModule() {
		return modulePackageList;
	}
	
	@Override
	public List<ModuleServicePack> getAllService() {
		if (moduleServiceList == null){
			moduleServiceList = new ArrayList<ModuleServicePack> ();
			
			// 加载服务列表
			for (JsfbootModule pack : modulePackageList){
				
				for (ServiceDefinition service : pack.getServiceList()){
					ModuleServicePack msi = new ModuleServicePackImpl(pack, service);
					moduleServiceList.add(msi);
				}
				
			}
			
		}
		return moduleServiceList;
	}

	@Override
	public List<JsfbootTheme> getAllTheme() {
		if (moduleThemeList == null){
			moduleThemeList = new ArrayList<JsfbootTheme> ();
			
			// 加载主题列表
			for (JsfbootModule pack : modulePackageList){
				
				if (pack.getModuleTheme() != null){
					moduleThemeList.add(pack.getModuleTheme());
				}
				
			}
		}
		return moduleThemeList;
	}

	@Override
	public List<AccountRealmPack> getAllAccountRealm() {
		if (userAdaptiveList == null){
			userAdaptiveList = new ArrayList<AccountRealmPack> ();
			
			for (JsfbootModule pack : modulePackageList){
				
				if (pack.getAccountRealm() != null){
					userAdaptiveList.add(new AccountRealmPackImpl(pack, pack.getAccountRealm()));
				}
				
			}
			
		}
		return userAdaptiveList;
	}

	/**
	 * 卸载所有
	 */
	public void freeAll(){
		modulePackageList.clear();
		if (moduleThemeList != null){
			moduleThemeList.clear();
		}
		if (moduleServiceList != null){
			moduleServiceList.clear();
		}
		if (userAdaptiveList != null){
			userAdaptiveList.clear();
		}
	}
}
