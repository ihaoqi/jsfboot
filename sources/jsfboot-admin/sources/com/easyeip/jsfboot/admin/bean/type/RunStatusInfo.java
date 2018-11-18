package com.easyeip.jsfboot.admin.bean.type;

import java.util.Collection;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.driver.AppMessageService;
import com.easyeip.jsfboot.core.driver.AppRunMessage;
import com.easyeip.jsfboot.core.secutiry.admin.RealmSingleton;
import com.easyeip.jsfboot.core.secutiry.admin.SecutiryServiceAdmin;
import com.easyeip.jsfboot.core.surface.PageDomainType;

public class RunStatusInfo {
		
	/**
	 * 取得APP运行时消息
	 * @return
	 */
	public Collection<AppRunMessage> getAppRunMessages() {
		AppMessageService msgService = (AppMessageService) JsfbootContext.getService(AppMessageService.class);
		return msgService.getMessages();
	}
	
	public int getAppRunMessageShowSize() {
		
		return Math.min(10, getAppRunMessages().size());
	}
	
	public int getJmsiteModuleCount (){
		return JsfbootContext.getDriver().getModuleManager().getAllModule().size();
	}
	
	public int getJmsiteServiceCount (){
		return JsfbootContext.getDriver().getModuleManager().getAllService().size();
	}
	
	public int getJmsiteThemeCount (){
		return JsfbootContext.getDriver().getModuleManager().getAllTheme().size();
	}

	public int getUserAdapterCount (){
		return JsfbootContext.getDriver().getModuleManager().getAllAccountRealm().size();
	}
	
	public String getCurrentAdapter(){
		SecutiryServiceAdmin ssa = (SecutiryServiceAdmin) JsfbootContext.getDriver().getSecutiryService();
		String authList = "";
		for(RealmSingleton realm : ssa.getRealmManager().getCurrentRealm()){
			if (authList.length() > 0){
				authList += ", ";
			}
			authList += realm.getName();
		}
		
		return authList;
	}
	
	/**
	 * 取得前台主题名称
	 * @return
	 */
	public String getSiteThemeName(){
		return JsfbootContext.getDriver().getSurfaceService().getCurrentTheme(PageDomainType.Site).getName();
	}
	
	/**
	 * 取得后台主题名称
	 * @return
	 */
	public String getAdminThemeName(){
		return JsfbootContext.getDriver().getSurfaceService().getCurrentTheme(PageDomainType.Admin).getName();
	}
}
