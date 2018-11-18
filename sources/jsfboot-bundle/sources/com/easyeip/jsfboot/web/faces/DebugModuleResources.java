package com.easyeip.jsfboot.web.faces;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.utils.StringKit;

/**
 * 模块资源重定向到开发时的目录
 * @author 成慧
 *
 */
public class DebugModuleResources {
	
	static Logger logger = Logger.getLogger(DebugModuleResources.class.getName());
	
	private static DebugModuleResources instance;

	/**
	 * 模块重定向路径Map<模块名称,目录路径>
	 */
	private static Map<String, File> redirect;
	
	static {
		redirect = new HashMap<String, File> ();
	}
	
	/**
	 * 获取资源重定向实例
	 * @return
	 */
	public static DebugModuleResources getInstance(){
		
		if (instance == null){
			synchronized(redirect){
				if (instance != null)
					return instance;
				
				instance = new DebugModuleResources();
				instance.init(JsfbootContext.getDriver().getModuleManager());
			}
		}
		
		return instance;
	}
	
	DebugModuleResources(){
	}
	
	/**
	 * 初使化可重定向的资源
	 * @param mm
	 */
	void init(ModuleManager mm){
		
		// 如果是生产环境不访问开发路径
		if (JsfbootContext.getDriver().getRuntimeInfo().isProductionMode())
			return;
		
		StringBuilder sb = new StringBuilder();
		// 如果模块定义是在jar中，并且MF文件中有资源路径，并且资源路径存在，则可以重定向
		for (JsfbootModule jm : mm.getAllModule()){
			
			if (!jm.getModuleFile().isInJarFile())
				continue;
			
			String resPath = jm.getManifestFile().getResourcesPath();
			if (StringKit.isEmpty(resPath))
				continue;
			
			File checkFolder = new File(resPath);
			if (checkFolder.exists() && checkFolder.isDirectory()){
				redirect.put(jm.getName(), checkFolder);
				
				if (sb.length() > 0)
					sb.append(", ");
				sb.append(jm.getName());
			}
		}
		
		// 输出LOG
		logger.log(Level.WARNING, "重定向模块页面：" + sb.toString());
	}
	
	/**
	 * 从路径中取得模块名称，然后再取模块的资源路径
	 * @param urlPath 请求路径，如/jsfboot-helpview/help.xhtml
	 * @return
	 */
	File getResourcesPath (String urlPath){
		
		if (redirect == null || redirect.isEmpty())
			return null;
		
		// 取第一个路径做为模块名
		String[] paths = urlPath.split("/");
		for (String p : paths){
			if (StringKit.notEmpty(p))
				return redirect.get(p);
		}
		
		return null;
	}
}
