package com.easyeip.jsfboot.admin.bean.impl;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.admin.bean.AdminConsoleBean;
import com.easyeip.jsfboot.admin.bean.type.RunStatusInfo;
import com.easyeip.jsfboot.admin.bean.type.RunAccountRealmInfo;
import com.easyeip.jsfboot.admin.bean.type.RunModuleInfo;

import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;

import com.easyeip.jsfboot.core.beans.JsfbootBeanException;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.secutiry.admin.RealmSingleton;
import com.easyeip.jsfboot.core.secutiry.admin.SecutiryServiceAdmin;

public class AdminConsoleBeanImpl implements AdminConsoleBean {
	
	RunStatusInfo runStatus = null;
	List<RunModuleInfo> moduleList = null;
	List<RunAccountRealmInfo> authList = null;
	RunModuleInfo showModule = null;
	JsfbootTheme confTheme = null;
	
	@Override
	public RunStatusInfo getRunStatusInfo() {
		if (runStatus == null){
			runStatus = new RunStatusInfo();
		}
		return runStatus;
	}


	@Override
	public List<RunModuleInfo> getRunModuleList() {
		if (moduleList == null){
			moduleList = new ArrayList<RunModuleInfo>();
			for (JsfbootModule mp : JsfbootContext.getDriver().getModuleManager().getAllModule()){
				
				moduleList.add(new RunModuleInfo(mp));
				
			}
		}
		return moduleList;
	}
	
	@Override
	public List<JsfbootTheme> getThemeList() {
		return JsfbootContext.getDriver().getModuleManager().getAllTheme();
	}

	@Override
	public List<RunAccountRealmInfo> getAuthProviderList() {
		
		if (authList == null){
			authList = new ArrayList<RunAccountRealmInfo>();
			SecutiryServiceAdmin ssa = (SecutiryServiceAdmin) JsfbootContext.getDriver().getSecutiryService();
			for (RealmSingleton api : ssa.getRealmManager().getRealmList()){
				authList.add(new RunAccountRealmInfo (api));
			}
		}
		return authList;
	}
	
	@Override
	public RunModuleInfo getShowModule() {
		if (showModule == null){
			String moduleName = FacesUtils.getFacesRequestParam("moduleName");
			if (StringKit.isEmpty(moduleName))
				throw new JsfbootBeanException("缺少模块名称参数。");
			JsfbootModule moduleObj = JsfbootContext.getDriver().getModuleManager().findModuleByName(moduleName);
			if (moduleObj == null)
				throw new JsfbootBeanException(moduleName + "模块不存在。");
			showModule = new RunModuleInfo(moduleObj);
		}
		return showModule;
	}
	
	@Override
	public JsfbootTheme getConfTheme() {
		if (confTheme == null){
			String moduleName = FacesUtils.getFacesRequestParam("moduleName");
			if (StringKit.isEmpty(moduleName))
				throw new JsfbootBeanException("缺少模块名称参数。");
			JsfbootModule moduleObj = JsfbootContext.getDriver().getModuleManager().findModuleByName(moduleName);
			if (moduleObj == null)
				throw new JsfbootBeanException(moduleName + "模块不存在。");
			confTheme = moduleObj.getModuleTheme();
		}
		return confTheme;
	}
}
