package com.easyeip.jsfboot.core.module.impl;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ManifestFile;
import com.easyeip.jsfboot.core.module.ModulePath;
import com.easyeip.jsfboot.core.module.type.DataSourceRequire;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.ModuleVersion;
import com.easyeip.jsfboot.core.module.type.PageResource;
import com.easyeip.jsfboot.core.module.type.RealmDefinition;
import com.easyeip.jsfboot.core.module.type.ServiceDefinition;
import com.easyeip.jsfboot.core.module.type.impl.DataSourceDemandImpl;
import com.easyeip.jsfboot.core.module.type.impl.ModuleThemeImpl;
import com.easyeip.jsfboot.core.module.type.impl.ModuleVersionImpl;
import com.easyeip.jsfboot.core.module.type.impl.PageResourceImpl;
import com.easyeip.jsfboot.core.module.type.impl.RealmDefinitionImpl;
import com.easyeip.jsfboot.core.module.type.impl.ServiceDefinitionImpl;
import com.easyeip.jsfboot.core.module.type.schema.AccountRealmType;
import com.easyeip.jsfboot.core.module.type.schema.JsfbootModuleType;
import com.easyeip.jsfboot.core.module.type.schema.ServiceItemType;

/**
 * 模块包实现
 * @author ihaoqi
 *
 */
public class JsfbootModuleImpl implements JsfbootModule {

	private ModulePath confFile;
	private ManifestFile mf;
	private JsfbootModuleType moduleType;
	
	private JsfbootTheme themeList;
	private List<ServiceDefinition> serviceList;
	private RealmDefinition realmDef;
	
	private ModuleVersion version;
	private PageResource pageRes;
	private DataSourceRequire dataSources;
	
	@Override
	public String toString() {
		return getModuleInfo().getModuleName() + "/" + getModuleInfo().getModuleTitle();
	}
	
	/**
	 * 加载模块配置文件
	 * @throws Exception
	 */
	public void loadModule (URL confFile, JsfbootModuleType moduleType) throws Exception{
		this.confFile = ModulePath.valueOf(confFile);
		this.moduleType = moduleType;
	}
	
	@Override
	public String getName() {
		return this.getModuleInfo().getModuleName();
	}

	@Override
	public ModulePath getModuleFile() {
		return confFile;
	}

	@Override
	public JsfbootTheme getModuleTheme() {
		
		if (themeList == null && moduleType.getDefineTheme() != null){
			themeList = new ModuleThemeImpl(this, moduleType.getDefineTheme());
		}

		return themeList;
	}

	@Override
	public List<ServiceDefinition> getServiceList() {
		if (serviceList == null){
			serviceList = new ArrayList<ServiceDefinition>();
			if (moduleType.getDefineService() != null){
				for (ServiceItemType sit : moduleType.getDefineService().getService()){
					serviceList.add(new ServiceDefinitionImpl(sit));
				}
			}
		}
		return serviceList;
	}

	@Override
	public RealmDefinition getAccountRealm() {
		if (realmDef == null && moduleType.getAccountRealm() != null){
			AccountRealmType art = moduleType.getAccountRealm();
			if (StringKit.notEmpty(art.getRefService()) ||
					StringKit.notEmpty(art.getUseClass())){
				realmDef = new RealmDefinitionImpl(art);
			}
		}
		return realmDef;
	}

	@Override
	public ModuleVersion getModuleInfo() {
		
		if (version == null){
			version = new ModuleVersionImpl (moduleType.getModuleVersion());
		}
		
		return version;
	}

	@Override
	public PageResource getPageResource() {
		if (pageRes == null){
			pageRes = new PageResourceImpl (this, moduleType.getPageResource());
		}
		return pageRes;
	}

	@Override
	public DataSourceRequire getDataSources() {
		if (dataSources == null && moduleType.getDataSource() != null){
			// 如果不符合规范则不返回
			if (moduleType.getDataSource().isRequired()){
				dataSources = new DataSourceDemandImpl (moduleType.getDataSource());
			}
		}
		return dataSources;
	}

	@Override
	public ManifestFile getManifestFile() {
		if (mf != null)
			return mf;
		
		mf = new ManifestFile (this.getModuleFile());
		return mf;
	}

}
