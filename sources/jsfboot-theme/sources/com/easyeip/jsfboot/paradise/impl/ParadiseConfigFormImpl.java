package com.easyeip.jsfboot.paradise.impl;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.paradise.ParadiseConfigForm;

public class ParadiseConfigFormImpl implements ParadiseConfigForm {
	
	private RegistryService registry;
	private RegistryItem ri;
	private Map<String,String> params;
	
	public ParadiseConfigFormImpl(RegistryService registry){
		this.registry = registry;
		params = new HashMap<String,String> ();
		try {
			ri = registry.createItem(RegistryPath.valueOfne(REGISTRY_ROOT_PATH));
		} catch (RegistryException e) {
			return;
		}
		
		params.put("appTitle", getRegValue("appTitle"));
		params.put("siteHeadLogoUrl", getRegValue("siteHeadLogoUrl"));
		params.put("adminHeadLogoUrl", getRegValue("adminHeadLogoUrl"));
		params.put("tailLogoUrl", getRegValue("tailLogoUrl"));
		
		params.put("siteMenuMode", getRegValue("siteMenuMode", "layout-menu-static"));
		params.put("adminMenuMode", getRegValue("adminMenuMode", "layout-menu-static"));
		params.put("siteMenuLightness", getRegValue("siteMenuLightness", "layout-menu-bright"));
		params.put("adminMenuLightness", getRegValue("adminMenuLightness", "layout-menu-dark"));
		
		params.put("siteMenuColor", getRegValue("siteMenuColor", "default"));
		params.put("siteWidgetColor", getRegValue("siteWidgetColor", "blue"));
		params.put("adminMenuColor", getRegValue("adminMenuColor", "deepsea"));
		params.put("adminWidgetColor", getRegValue("adminWidgetColor", "blue"));
		
		params.put("loginLogoUrl", getRegValue("loginLogoUrl"));
		params.put("welcomeTitle", getRegValue("welcomeTitle"));
		params.put("forgetPasswordUrl", getRegValue("forgetPasswordUrl"));
		
		params.put("accountTitle", getRegValue("accountTitle"));
		params.put("accountAvatar", getRegValue("accountAvatar"));
		
		params.put("titleBarType", getRegValue("titleBarType"));
		params.put("titleBarValue", getRegValue("titleBarValue"));
		
		params.put("contentMinWidth", getRegValue("contentMinWidth"));
	}
	
	private String getRegValue (String name){
		return getRegValue(name, null);
	}

	private String getRegValue (String name, String defval){
		String val = ri.getValue(name);
		if (StringKit.isEmpty(val))
			return defval;
		return val;
	}

	@Override
	public String getAppTitle() {
		return params.get("appTitle");
	}

	@Override
	public void setAppTitle(String title) {
		params.put("appTitle", title);
	}

	@Override
	public String getSiteHeadLogoUrl() {
		return params.get("siteHeadLogoUrl");
	}
	
	@Override
	public void setSiteHeadLogoUrl(String value) {
		params.put("siteHeadLogoUrl", value);
	}

	@Override
	public String getAdminHeadLogoUrl() {
		return params.get("adminHeadLogoUrl");
	}
	
	@Override
	public void setAdminHeadLogoUrl(String value) {
		params.put("adminHeadLogoUrl", value);
	}

	@Override
	public String getTailLogoUrl() {
		return params.get("tailLogoUrl");
	}
	
	@Override
	public void setTailLogoUrl(String value) {
		params.put("tailLogoUrl", value);
	}
	
	@Override
	public String getLoginLogoUrl() {
		return params.get("loginLogoUrl");
	}
	
	@Override
	public void setLoginLogoUrl(String value) {
		params.put("loginLogoUrl", value);
	}

	@Override
	public String getWelcomeTitle() {
		return params.get("welcomeTitle");
	}
	
	@Override
	public void setWelcomeTitle(String value) {
		params.put("welcomeTitle", value);
	}

	@Override
	public String getForgetPasswordUrl() {
		return params.get("forgetPasswordUrl");
	}

	@Override
	public void setForgetPasswordUrl(String value) {
		params.put("forgetPasswordUrl", value);
	}

	@Override
	public String getSiteMenuMode() {
		return params.get("siteMenuMode");
	}

	@Override
	public void setSiteMenuMode(String value) {
		params.put("siteMenuMode", value);
	}
	
	@Override
	public String getAdminMenuMode() {
		return params.get("adminMenuMode");
	}
	
	@Override
	public void setAdminMenuMode(String value) {
		params.put("adminMenuMode", value);
	}

	@Override
	public String getSiteMenuLightness() {
		return params.get("siteMenuLightness");
	}
	
	@Override
	public void setSiteMenuLightness(String value) {
		params.put("siteMenuLightness", value);
	}

	@Override
	public String getAdminMenuLightness() {
		return params.get("adminMenuLightness");
	}
	
	@Override
	public void setAdminMenuLightness(String value) {
		params.put("adminMenuLightness", value);
	}

	@Override
	public String getSiteMenuColor() {
		return params.get("siteMenuColor");
	}
	
	@Override
	public void setSiteMenuColor(String value) {
		params.put("siteMenuColor", value);
	}

	@Override
	public String getSiteWidgetColor() {
		return params.get("siteWidgetColor");
	}
	
	@Override
	public void setSiteWidgetColor(String value) {
		params.put("siteWidgetColor", value);
	}

	@Override
	public String getAdminMenuColor() {
		return params.get("adminMenuColor");
	}
	
	@Override
	public void setAdminMenuColor(String value) {
		params.put("adminMenuColor", value);
	}

	@Override
	public String getAdminWidgetColor() {
		return params.get("adminWidgetColor");
	}

	@Override
	public void setAdminWidgetColor(String value) {
		params.put("adminWidgetColor", value);
	}
	
	@Override
	public void save() throws Exception {
		
		RegistryItem ri = registry.createItem(RegistryPath.valueOf(REGISTRY_ROOT_PATH));
		ri.setValue("appTitle", getAppTitle());
		ri.setValue("siteHeadLogoUrl", params.get("siteHeadLogoUrl"));
		ri.setValue("adminHeadLogoUrl", params.get("adminHeadLogoUrl"));
		ri.setValue("tailLogoUrl", params.get("tailLogoUrl"));
		
		ri.setValue("siteMenuMode", params.get("siteMenuMode"));
		ri.setValue("adminMenuMode", params.get("adminMenuMode"));
		ri.setValue("siteMenuLightness", params.get("siteMenuLightness"));
		ri.setValue("adminMenuLightness", params.get("adminMenuLightness"));
		
		ri.setValue("siteMenuColor", params.get("siteMenuColor"));
		ri.setValue("siteWidgetColor", params.get("siteWidgetColor"));
		ri.setValue("adminMenuColor", params.get("adminMenuColor"));
		ri.setValue("adminWidgetColor", params.get("adminWidgetColor"));
		
		ri.setValue("loginLogoUrl", params.get("loginLogoUrl"));
		ri.setValue("welcomeTitle", params.get("welcomeTitle"));
		ri.setValue("forgetPasswordUrl", params.get("forgetPasswordUrl"));
		
		ri.setValue("accountTitle", params.get("accountTitle"));
		ri.setValue("accountAvatar", params.get("accountAvatar"));
		
		ri.setValue("titleBarType", params.get("titleBarType"));
		ri.setValue("titleBarValue", params.get("titleBarValue"));
		
		ri.setValue("contentMinWidth", params.get("contentMinWidth"));
		
		registry.updateItem(ri);
	}

	@Override
	public String getWidgetColor() {
		return null;
	}
	
	@Override
	public String getAccountTitle() {
		return params.get("accountTitle");
	}

	@Override
	public void setAccountTitle(String value) {
		params.put("accountTitle", value);
	}
	
	@Override
	public String getAccountAvatar() {
		return params.get("accountAvatar");
	}
	
	@Override
	public void setAccountAvatar(String value) {
		params.put("accountAvatar", value);
	}

	@Override
	public String getTitleBarType() {
		return params.get("titleBarType");
	}

	@Override
	public String getTitleBarValue() {
		return params.get("titleBarValue");
	}

	@Override
	public void setTitleBarType(String value) {
		params.put("titleBarType", value);
	}

	@Override
	public void setTitleBarValue(String value) {
		params.put("titleBarValue", value);
	}

	@Override
	public void setContentMinWidth(String widthCss) {
		params.put("contentMinWidth", widthCss);
	}

	@Override
	public String getContentMinWidth() {
		return params.get("contentMinWidth");
	}
}
