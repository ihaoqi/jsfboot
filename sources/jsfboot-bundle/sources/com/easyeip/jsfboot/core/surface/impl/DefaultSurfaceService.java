package com.easyeip.jsfboot.core.surface.impl;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.SurfaceService;

/**
 * 默认的主题管理实现
 * @author ihaoqi
 *
 */
public class DefaultSurfaceService extends GenericService implements SurfaceService {
	
	@UseJsfbootService(RegistryService.class)
	private RegistryService registry;
	
	private Map<String, JsfbootTheme> currentTheme;
	private SiteMenuManager menuMgr = null;

	@Override
	public void startService(ServiceContext context) throws Exception {
		
		currentTheme = new HashMap<String,JsfbootTheme> ();
		
	}

	@Override
	public void stopService() throws Exception {
		super.stopService();
	}

	@Override
	public JsfbootTheme getCurrentTheme(PageDomainType type) {
		return currentTheme.get(type.name());
	}

	@Override
	public void setCurrentTheme(PageDomainType type, String name) throws Exception {
		// 查找这个主题信息
		JsfbootTheme findTheme = null;
		for (JsfbootTheme mti : JsfbootContext.getDriver().getModuleManager().getAllTheme()){
			if (mti.getModulePackage().getModuleInfo().getModuleName().equals(name)){
				findTheme = mti; break;
			}
		}
		
		// 使用它
		if (findTheme != null){
			
			// 先判断类型是否匹配
			if (type == PageDomainType.Site && findTheme.isUseSite() == false)
				throw new Exception ("“" + name + "”主题不支持前端应用。");
			
			if (type == PageDomainType.Admin && findTheme.isUseAdmin() == false)
				throw new Exception ("“" + name + "”主题不支持后端应用。");
			
			currentTheme.put(type.name(), findTheme);
		}else{
			throw new Exception ("没有找到名称为“" + name + "”的主题模块。");
		}
	}

	@Override
	public SiteMenuManager getMenuManager() {
		
		// 首次使用时初使化
		if (menuMgr == null){
			SiteMenuManagerImpl smm = new SiteMenuManagerImpl ();
			smm.init(JsfbootContext.getDriver().getModuleManager(), registry);
			menuMgr = smm;
		}
		
		return menuMgr;
	}
}
