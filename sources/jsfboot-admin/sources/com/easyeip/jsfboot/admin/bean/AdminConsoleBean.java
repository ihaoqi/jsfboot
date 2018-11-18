package com.easyeip.jsfboot.admin.bean;

import java.util.List;

import com.easyeip.jsfboot.admin.bean.type.RunStatusInfo;
import com.easyeip.jsfboot.admin.bean.type.RunAccountRealmInfo;
import com.easyeip.jsfboot.admin.bean.type.RunModuleInfo;
import com.easyeip.jsfboot.core.module.type.JsfbootTheme;

public interface AdminConsoleBean {
	
	/**
	 * 取得运时行信息
	 * @return
	 */
	RunStatusInfo getRunStatusInfo();
	
	/**
	 * 取得运行的模块列表
	 * @return
	 */
	List<RunModuleInfo> getRunModuleList();
	
	/**
	 * 获取主题列表
	 * @return
	 */
	List<JsfbootTheme> getThemeList ();
	
	/**
	 * 获取授权适配器列表
	 * @return
	 */
	List<RunAccountRealmInfo> getAuthProviderList();
	
	/**
	 * 获取当前要显示的模块
	 * @return
	 */
	RunModuleInfo getShowModule();
	
	/**
	 * 获取当前要配置的主题
	 * @return
	 */
	JsfbootTheme getConfTheme();
}
