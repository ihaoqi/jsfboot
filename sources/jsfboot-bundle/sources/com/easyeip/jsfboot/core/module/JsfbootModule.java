package com.easyeip.jsfboot.core.module;

import java.util.List;

import com.easyeip.jsfboot.core.module.type.DataSourceRequire;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.ModuleVersion;
import com.easyeip.jsfboot.core.module.type.PageResource;
import com.easyeip.jsfboot.core.module.type.RealmDefinition;
import com.easyeip.jsfboot.core.module.type.ServiceDefinition;

/**
 * 一个安装后的模块
 * @author ihaoqi
 *
 */
public interface JsfbootModule {
	
	/**
	 * 取得模块名称
	 * @return
	 */
	String getName();
	
	/**
	 * 取得模块定义文件路径
	 * @return 
	 */
	ModulePath getModuleFile();
	
	/**
	 * 取得模块定义所在JAR的MANIFEST.MF
	 * @return
	 */
	ManifestFile getManifestFile();
	
	/**
	 * 取得版本信息
	 * @return
	 */
	ModuleVersion getModuleInfo();
	
	/**
	 * 列表所有模块中的主题
	 * @return
	 */
	JsfbootTheme getModuleTheme();
	
	/**
	 * 取得页面资源定义
	 * @return
	 */
	PageResource getPageResource();
	
	/**
	 * 列出所有模块中的服务
	 * @return
	 */
	List<ServiceDefinition> getServiceList();
	
	/**
	 * 取得数据源定义
	 * @return
	 */
	DataSourceRequire getDataSources();
	
	/**
	 * 取得用户适配器
	 * @return
	 */
	RealmDefinition getAccountRealm();
}
