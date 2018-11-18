package com.easyeip.jsfboot.paradise.impl;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.JsfBeanUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.web.JsfbootPageNavigator;
import com.easyeip.jsfboot.web.NavigatorPath;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.paradise.ParadiseConfigValue;

public class ParadiseConfigValueImpl implements ParadiseConfigValue {

	private RegistryItem ri;
	private Map<String,String> caches;
	
	public ParadiseConfigValueImpl(RegistryService registry){
		ri = registry.getItem(RegistryPath.valueOfne(REGISTRY_ROOT_PATH));
		caches = new HashMap<String,String> ();
	}
	
	private String getParam(String name, String defval){
		if (ri== null)
			return defval;
		String val = ri.getValue(name);
		if (StringKit.isEmpty(val))
			return defval;
		return val;
	}
	
	/**
	 * 计算表达式
	 * @param name
	 * @param defval
	 * @return
	 */
	private String exprParam (String name, String defval){
		String calcExpr = getParam(name, defval);
		if (!StringKit.isEmpty(calcExpr)){
			return (String) JsfBeanUtils.evalExprNE(calcExpr);
		}
		
		return calcExpr;
	}
	
	@Override
	public String getAppTitle() {
		
		String parmName = "appTitle";
		
		if (caches.get(parmName) != null)
			return caches.get(parmName);

		caches.put(parmName, exprParam(parmName, "#{jsfbootConfig.appVersion.productTitle}"));
		
		return caches.get(parmName);
	}

	@Override
	public String getSiteHeadLogoUrl() {
		return getParam("siteHeadLogoUrl", "/jsfboot-bundle/jsfboot-logo.png");
	}

	@Override
	public String getAdminHeadLogoUrl() {
		String siteLogo = getParam("siteHeadLogoUrl", null);
		String defLogo = "/jsfboot-bundle/jsfboot-logo.png";
		
		return getParam("adminHeadLogoUrl", siteLogo == null ? defLogo : siteLogo);
	}

	@Override
	public String getTailLogoUrl() {
		return getParam("tailLogoUrl", "/jsfboot-bundle/jsfboot-logo.png");
	}
	
	@Override
	public String getLoginLogoUrl() {
		return getParam("loginLogoUrl", getSiteHeadLogoUrl());
	}

	@Override
	public String getWelcomeTitle() {
		return exprParam("welcomeTitle", "欢迎使用 #{paradiseTheme.config.appTitle}");
	}

	@Override
	public String getForgetPasswordUrl() {
		return getParam("forgetPasswordUrl", null);
	}

	@Override
	public String getSiteMenuMode() {
		return getParam("siteMenuMode", "layout-menu-static");
	}

	@Override
	public String getAdminMenuMode() {
		return getParam("adminMenuMode", "layout-menu-static");
	}

	@Override
	public String getSiteMenuLightness() {
		return getParam("siteMenuLightness", "layout-menu-bright");
	}

	@Override
	public String getAdminMenuLightness() {
		return getParam("adminMenuLightness", "layout-menu-dark");
	}

	@Override
	public String getSiteMenuColor() {
		return getParam("siteMenuColor", "default");
	}

	@Override
	public String getSiteWidgetColor() {
		return getParam("siteWidgetColor", "blue");
	}

	@Override
	public String getAdminMenuColor() {
		return getParam("adminMenuColor", "deepsea");
	}

	@Override
	public String getAdminWidgetColor() {
		return getParam("adminWidgetColor", "blue");
	}

	@Override
	public String getWidgetColor() {
		NavigatorPath navPath = JsfbootPageNavigator.getNavigatorPath(FacesUtils.getServletRequest());
		if (navPath != null && navPath.getPageDomain() == PageDomainType.Admin)
			return getAdminWidgetColor();
		return getSiteWidgetColor();
	}

	@Override
	public String getAccountTitle() {
		return exprParam("accountTitle", "#{paradiseTheme.accountUsername}");
	}

	@Override
	public String getAccountAvatar() {
		return exprParam("accountAvatar", "/jsfboot-bundle/jsfboot-avatar.png");
	}

	@Override
	public String getTitleBarType() {
		return exprParam("titleBarType", null);
	}

	@Override
	public String getTitleBarValue() {
		String defexpr = "#{paradiseTheme.config.appTitle}";
		String calcExpr = getParam("titleBarValue", null);
		if (!StringKit.isEmpty(calcExpr)){
			try{
				return (String) JsfBeanUtils.evalExpr(calcExpr);
			}catch (Exception e){
				
			}
		}
		
		return (String) JsfBeanUtils.evalExpr(defexpr);
	}

	@Override
	public String getContentMinWidth() {
		return getParam("contentMinWidth", "0px");
	}

}
