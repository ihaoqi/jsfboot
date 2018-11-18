package com.easyeip.jsfboot.core.module.impl;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.JaxbUtils;
import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModulePath;
import com.easyeip.jsfboot.core.module.type.schema.JsfbootModuleType;

/**
 * 模块管理实现
 * @author ihaoqi
 *
 */
public class DefaultModuleManger extends AbstractModuleManger implements ModuleManagerAdmin {

	private static final String MODULE_XSD_FILE = "/jsfboot-module-1.0.xsd";
	
	/**
	 * 查找并加载所有模块
	 * @param moduleList 应用程序的模块配置文件
	 * @throws Exception 加载出错会抛出异常
	 */
	public void loadModuleList (List<URL> moduleList) throws Exception{
		
		// 先加载所有模块的配置文件，不允许有重名的
		Map<JsfbootModuleType, URL> moduleTypeMap = new HashMap<JsfbootModuleType, URL>();
		Map<String, JsfbootModuleType> moduleNameMap = new HashMap<String, JsfbootModuleType>();
		List<JsfbootModuleType> typeList = new ArrayList<JsfbootModuleType>();
		
		for (URL url : moduleList){
			// 加载配置文件，如出错会抛出异常
			JsfbootModuleType moduleType = openJsfbootModuleConfigure (url);
			
			// 判断模块名称是否为空或重名
			ModulePath urlFile = ModulePath.valueOf(url);
			String moduleName = moduleType.getModuleVersion().getModuleName();
			if (!NameUtils.isXMLName(moduleName)){
				throw new Exception("加载 " + urlFile.getFilename() + " 文件失败：模块名称为空或不符合规范。");
			}
			
			// 判断是否已存在
			JsfbootModuleType oldType = moduleNameMap.get(moduleName);
			if (oldType != null){
				ModulePath oldFile = ModulePath.valueOf(moduleTypeMap.get(oldType));
				throw new Exception("加载 " + urlFile.getFilename() + " 文件失败：与" +
							oldFile.getFilename() + "定义的模块重名。");
			}
			
			moduleNameMap.put(moduleName, moduleType);
			moduleTypeMap.put(moduleType, url);
			typeList.add(moduleType);
		}
		
		moduleNameMap.clear();
		
		// 按照load-on-startup+名称对模块排序
		Collections.sort(typeList, new Comparator<JsfbootModuleType>(){
			public int compare(JsfbootModuleType o1, JsfbootModuleType o2) {
				int eq = o1.getLoadOnStartup() - o2.getLoadOnStartup();
				if (eq != 0)
					return eq;
				
				String name1 = o1.getModuleVersion().getModuleName();
				String name2 = o2.getModuleVersion().getModuleName();
				
				if (name1 == null) name1 = "";
				if (name2 == null) name2 = "";
				
				return name1.compareTo(name2);
			}
		});
		
		// 按顺序加载排序后的模块
		for (JsfbootModuleType module : typeList){
			
			JsfbootModuleImpl pack = new JsfbootModuleImpl ();
			pack.loadModule(moduleTypeMap.get(module), module);
			
			// 添加到列表中
			super.addModule(pack);
			
		}
		
		// 结束
	}
	
	@Override
	public void freeModuleList() {
		super.freeAll();
	}
	
	/**
	 * 打开jsfboot-Module.xml配置文件
	 * @param confFile 文件路径
	 * @return 返回xsd对应的对象
	 * @throws Exception
	 */
	private JsfbootModuleType openJsfbootModuleConfigure(URL confFile) throws Exception{
		
		JsfbootModuleType moduleType = null;
		InputStream conf_stream = null;
		try{
			conf_stream = confFile.openStream();
			moduleType = (JsfbootModuleType) JaxbUtils.createXsdObject(
								JsfbootModuleType.class, conf_stream, 
								this.getClass().getResource(MODULE_XSD_FILE));
		}catch (Exception e){
			throw new Exception("加载 " + ModulePath.valueOf(confFile).getFilename() + " 文件失败：" + e.getMessage());
		}finally{
			if (conf_stream != null)
				conf_stream.close();
		}
		
		return moduleType;
	}

	@Override
	public JsfbootModule findModuleByName(String name) {
		for (JsfbootModule mod : getAllModule()){
			if (mod.getModuleInfo().getModuleName().equals(name))
				return mod;
		}
		return null;
	}
}
