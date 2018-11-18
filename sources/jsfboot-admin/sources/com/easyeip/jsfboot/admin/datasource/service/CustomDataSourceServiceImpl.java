package com.easyeip.jsfboot.admin.datasource.service;

import java.util.ArrayList;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.easyeip.jsfboot.admin.AdminHelpViewService;
import com.easyeip.jsfboot.admin.datasource.CustomDataSourceService;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.jdbc.DataSourceFinder;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.PageDomainType;

public class CustomDataSourceServiceImpl implements CustomDataSourceService, DataSourceFinder {
	
	private static final String CUSTOMDS_REGPATH = "/jsfboot/module/jsfboot-admin/custom-source";
	
	@UseJsfbootService(DataSourceService.class)
	private DataSourceService dataSourceService;
	
	@UseJsfbootService(RegistryService.class)
	private RegistryService registry;
	
	@UseJsfbootService (AdminHelpViewService.class)
	private AdminHelpViewService helpView;

	@Override
	public void startService(ServiceContext context) throws Exception {
		dataSourceService.registryFinder(this);
		initHelpPage (context);
	}

	@Override
	public void stopService() throws Exception {
		dataSourceService.unregistryFinder(this);
	}
	
	// 注册帮助
	void initHelpPage(ServiceContext context){

		JsfbootModule module = context.getModule();
		
		HelpBuilder hb = helpView.getHelpBuilder();
		HelpCatalog root = helpView.addHelpDocument(PageDomainType.Admin,
				hb.newCatalog(module.getName(), module.getModuleInfo().getModuleTitle(),
				hb.includePage(hb.fullModulePath(module, "/help/readme.xhtml"))));
	
		root.addChild(hb.newCatalog("dashboard", "系统信息", hb.includePage("dashboard.xhtml")));
		HelpCatalog sysConf = root.addChild(hb.newCatalog("system", "控制面板"));
		sysConf.addChild(hb.newCatalog("console", "模块选项", hb.includePage("console.xhtml")));
		sysConf.addChild(hb.newCatalog("data-sources", "数据连接", hb.includePage("data-sources.xhtml"))).
				addChild(hb.newCatalog("druid-config", "Druid配置", hb.includePage("druid-config.xhtml")));
		sysConf.addChild(hb.newCatalog("nav-menus", "导航菜单", hb.includePage("nav-menus.xhtml")));
		sysConf.addChild(hb.newCatalog("registry", "统一配置", hb.includePage("registry.xhtml")));
		
		helpView.addDashboardGadget("字体图标", "fa fa-font").
					setOutcome(OutcomeUtils.fullOutcome(module, "font-icons"));
	}
	
	@Override
	public List<String> getDataSourceList() {
		List<String> customSourceList = new ArrayList<String>();
		
		for (RegistryItem item : registry.allChildren(RegistryPath.valueOfne(CUSTOMDS_REGPATH))){
			
			customSourceList.add(item.getName());
		}
		
		return customSourceList;
	}

	@Override
	public DataSourceDefinition getCustomDataSource(String name) {
		RegistryPath path = RegistryPath.valueOfne(CUSTOMDS_REGPATH).addChild(name);
		if (path == null)
			return null;
		
		RegistryItem item = registry.getItem(path);
		if (item == null)
			return null;
		
		DataSourceDefinitionImpl def = new DataSourceDefinitionImpl(name);
		return read(def, item);
	}
	
	@Override
	public DataSourceDefinition createCustomDataSource(String name) throws Exception {
		RegistryPath path = RegistryPath.valueOfne(CUSTOMDS_REGPATH).addChild(name);
		RegistryItem item = registry.getItem(path);
		if (item != null)
			throw new Exception (name + "已存在。");
		
		item = registry.createItem(path);
		DataSourceDefinitionImpl def = new DataSourceDefinitionImpl(name);
		save (def, item);
		return def;
	}

	@Override
	public void updateCustomDataSource(DataSourceDefinition definition) throws Exception {
		RegistryPath path = RegistryPath.valueOf(CUSTOMDS_REGPATH).addChild(definition.getName());
		RegistryItem item = registry.useItem(path);
		save (definition, item);
	}

	@Override
	public boolean removeCustomDataSource(String name) {
		RegistryPath path = RegistryPath.valueOfne(CUSTOMDS_REGPATH).addChild(name);
		try {
			registry.removeItem(path);
			return true;
		} catch (RegistryException e) {
			return false;
		}
	}
	
	public DataSourceDefinition read(DataSourceDefinition def, RegistryItem item) {
		
		def.setExplain(item.getComment());
		
		def.setDriverClassName(item.getValue("driverClass"));
		def.setConnectionUrl(item.getValue("url"));
		def.setUsername(item.getValue("username"));
		def.setPassword(item.getValue("password"));
		
		def.setInitialSize(item.getValue("initialSize"));
		def.setMaxActive(item.getValue("maxActive"));
		def.setMaxWait(item.getValue("maxWait"));
		def.setMinIdle(item.getValue("minIdle"));
		def.setQueryTimeout(item.getValue("queryTimeout"));
		
		def.setFilters(item.getValue("filters"));
		def.setMoveParams(item.getValue("moveParams"));
		

		return def;
	}

	public DataSourceDefinition save(DataSourceDefinition def, RegistryItem item)throws Exception {
		
		item.setComment(def.getExplain());

		item.setValue("driverClass", def.getDriverClassName());
		item.setValue("url", def.getConnectionUrl());
		item.setValue("username", def.getUsername());
		item.setValue("password", def.getPassword());
		
		item.setValue("initialSize", def.getInitialSize());
		item.setValue("maxActive", def.getMaxActive());
		item.setValue("maxWait", def.getMaxWait());
		item.setValue("minIdle", def.getMinIdle());
		item.setValue("queryTimeout", def.getQueryTimeout());
		
		item.setValue("filters", def.getFilters());
		item.setValue("moveParams", def.getMoveParams());

		registry.updateItem(item);
		return def;
	}
	
	@Override
	public DataSource getDataSourceObject(String dataSourceName) throws NamingException {
		DataSourceDefinition  definition = getCustomDataSource(dataSourceName);
		if (definition == null)
			return null;
		
		// 创建briud数据源
		try {
			return CustomDataSourceFactory.create(definition);
		} catch (Exception e) {
			throw new NamingException (e.getMessage());
		}
	}
}
