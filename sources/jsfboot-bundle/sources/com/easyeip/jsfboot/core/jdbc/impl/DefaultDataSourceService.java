package com.easyeip.jsfboot.core.jdbc.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.sql.DataSource;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.jdbc.DataSourceFinder;
import com.easyeip.jsfboot.core.jdbc.DataSourceService;
import com.easyeip.jsfboot.core.jdbc.ModuleDataSource;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.DataSourceRequire;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.JsfbootService;
import com.easyeip.jsfboot.core.services.NotifyCallback;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceManager;

/**
 * 数据源服务实现
 * @author ihaoqi
 *
 */
public class DefaultDataSourceService implements DataSourceService, JsfbootService {
	
	public static final String JNDIBIND_REGPATH = "/jsfboot/core/data-source";

	@UseJsfbootService(RegistryService.class)
	private RegistryService registry;
	private List<ModuleDataSource> bindList;
	private Map<String,DataSource> dataSourceCached;
	
	private List<DataSourceFinder> dataSourceFinder;
	private boolean allowCreateDataSource = false;

	@Override
	public void startService(final ServiceContext context) throws Exception {
		
		// 加载数据源配置（不允许有重复）
		bindList = new ArrayList<ModuleDataSource>();
		Map<String,ModuleDataSourceImpl> oneCheck = new HashMap<String,ModuleDataSourceImpl>();
		
		for (JsfbootModule jm : context.getDriver().getModuleManager().getAllModule()){
			DataSourceRequire dsa = jm.getDataSources();
			if (dsa == null)
				continue;
			
			ModuleDataSourceImpl bind = new ModuleDataSourceImpl(jm, dsa);
			if (oneCheck.get(bind.getModuleName()) == null){
				bindList.add(bind);
				oneCheck.put(bind.getModuleName(), bind);
			}
		}
		
		dataSourceCached = new HashMap<String,DataSource>();
		
		// 初使化绑定
		loadJndiSourceBind();
		
		// 初使化查找器
		dataSourceFinder = new ArrayList<DataSourceFinder>();
		dataSourceFinder.add(new JndiDataSourceFinder());
		
		// 添加服务启动完成监听，必须要所有服务都启动后才能创建数据源
		context.addServiceListener(ServiceManager.class, new NotifyCallback(){
			@Override
			public void notifyCallback(Object sender, String type, Map<String, Object> param) {
				allowCreateDataSource = true;
				context.removeServiceListener(this);
			}
		}, ServiceManager.NOTIFY_FINISH);
	}

	@Override
	public void stopService() throws Exception {

		bindList.clear();
	}

	@Override
	public List<ModuleDataSource> getModuleDataSources() {
		return Collections.unmodifiableList(bindList);
	}

	@Override
	public void bindModuleDataSource(String moduleName, String dataSourceName) {
		
		ModuleDataSource bind = findModuleDataSource(moduleName);
		if (bind != null){
			// 更新并保存
			((ModuleDataSourceImpl)bind).bindDataSource(dataSourceName);
			saveJndiSourceBind();
			
			// 清除 cached
			if (dataSourceCached.get(moduleName) != null){
				dataSourceCached.remove(moduleName);
			}
		}
	}
	
	private ModuleDataSource findModuleDataSource(String moduleName) {
		for (ModuleDataSource bind : bindList){
			if (bind.getModuleName().equals(moduleName)){
				
				return bind;
			}
		}
		
		return null;
	}
	
	@Override
	public void registryFinder(DataSourceFinder finder) {
		// 插入到第一个，优先查找
		dataSourceFinder.add(0, finder);
	}

	@Override
	public DataSource getDataSourceObject(String moduleName) throws NamingException {
		
		if (allowCreateDataSource == false){
			throw new NamingException("服务管理启动没结束前不允许创建数据源对象。");
		}
		
		// 先从cached中查询
		DataSource source = dataSourceCached.get(moduleName);
		if (source != null)
			return source;
		
		// 查找模块数据源定义
		ModuleDataSource find = null;
		for (ModuleDataSource bind : bindList){
			if (StringKit.equals(bind.getModuleName(), moduleName)){
				find = bind;
				break;
				
			}
		}
		
		if (find == null)
			throw new NamingException(moduleName + " 没有定义数据源。");
		
		// 取得绑定的数据源名称
		String bind = find.getDataSource();
		if (StringKit.isEmpty(bind)){
			bind = find.getDefinition().getDefault();
		}
		
		if (StringKit.isEmpty(bind))
			throw new NamingException(moduleName + " 没有绑定数据源。");
		
		// 查找真实数据源
		for (DataSourceFinder finder : dataSourceFinder){
			
			source = finder.getDataSourceObject(bind);
			if (source != null){
				break;
			}
			
		}
		
		if (source == null){
			throw new NamingException(bind + " 数据源不存在。");
		}
		
		// 缓存下来
        dataSourceCached.put(moduleName, source);

        return source;
	}
	
	/**
	 * 从注册表中加载绑定
	 */
	private void loadJndiSourceBind(){
		
		for (RegistryItem item : registry.allChildren(RegistryPath.valueOfne(JNDIBIND_REGPATH))){

			// 更新
			ModuleDataSource bind = findModuleDataSource(item.getName());
			if (bind != null){
				((ModuleDataSourceImpl)bind).bindDataSource(item.getValue("bind"));
			}
		}
		
	}
	
	/**
	 * 把绑定保存到注册表
	 */
	private void saveJndiSourceBind(){
		
		RegistryPath root = RegistryPath.valueOfne(JNDIBIND_REGPATH);
		
		for (ModuleDataSource bind : bindList){
			RegistryPath childPath = root.makeChild(bind.getModuleName());
			try{
				if (StringKit.isEmpty(bind.getDataSource())){
					registry.removeItem(childPath);
				}
				else{
					RegistryItem item = registry.createItem(childPath);
					item.setValue("module", bind.getModuleName());
					item.setValue("bind", bind.getDataSource());
					registry.updateItem(item);
				}
			}
			catch (RegistryException e){
				//
			}
		}
	}

	@Override
	public void unregistryFinder(DataSourceFinder finder) {
		dataSourceFinder.remove(finder);
	}
}
