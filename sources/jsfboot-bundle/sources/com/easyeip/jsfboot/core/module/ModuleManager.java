package com.easyeip.jsfboot.core.module;

import java.util.List;

import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.module.type.ModuleServicePack;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;

/**
 * 模块管理服务
 * @author ihaoqi
 *
 */
public interface ModuleManager {
	
	/**
	 * 获取所有安装的模块
	 * @return
	 */
	List<JsfbootModule> getAllModule();
	
	/**
	 * 按名称查找模块
	 * @param name
	 * @return
	 */
	JsfbootModule findModuleByName (String name);
	
	/**
	 * 列表所有模块中的主题
	 * @return
	 */
	List<JsfbootTheme> getAllTheme();
	
	/**
	 * 列出所有模块中的服务
	 * @return
	 */
	List<ModuleServicePack> getAllService();
	
	/**
	 * 列出所有模块中的用户适配器
	 * @return
	 */
	List<AccountRealmPack> getAllAccountRealm();
}
