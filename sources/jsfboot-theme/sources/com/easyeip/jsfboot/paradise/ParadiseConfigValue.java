package com.easyeip.jsfboot.paradise;

public interface ParadiseConfigValue {
	
	public static final String REGISTRY_ROOT_PATH = "/jsfboot/module/jsfboot-theme/paradise";
	
	/**
	 * 取得应用程序标题
	 * @return
	 */
	String getAppTitle();

	/**
	 * 取得Site头部图标url
	 * @return
	 */
	String getSiteHeadLogoUrl();
	
	/**
	 * 取得Admin头部图标url
	 * @return
	 */
	String getAdminHeadLogoUrl();
	
	/**
	 * 取得尾部图标url
	 * @return
	 */
	String getTailLogoUrl();
	
	/**
	 * 取得登录窗口图标
	 * @return
	 */
	String getLoginLogoUrl();
	
	/**
	 * 取得登录窗口欢迎标题
	 * @return
	 */
	String getWelcomeTitle();
	
	/**
	 * 取得忘记密码URL
	 * @return
	 */
	String getForgetPasswordUrl();
	
	/**
	 * 取得Site菜单模式
	 * @return
	 */
	String getSiteMenuMode ();
	
	/**
	 * 取得Admin菜单模式
	 * @return
	 */
	String getAdminMenuMode();
	
	/**
	 * 取得Site菜单亮度
	 * @return
	 */
	String getSiteMenuLightness ();
	
	/**
	 * 取得Admin菜单亮度
	 * @return
	 */
	String getAdminMenuLightness ();
	
	/**
	 * 取得Site菜单颜色
	 * @return
	 */
	String getSiteMenuColor();
	
	/**
	 * 取得Site控件颜色
	 * @return
	 */
	String getSiteWidgetColor();
	
	/**
	 * 取得Admin菜单颜色
	 * @return
	 */
	String getAdminMenuColor();
	
	/**
	 * 取得Admin控件颜色
	 * @return
	 */
	String getAdminWidgetColor();
	
	/**
	 * 根据当前主题类型返回控件颜色
	 * @return
	 */
	String getWidgetColor();
	
	/**
	 * 取得用户名称
	 * @return
	 */
	String getAccountTitle();
	
	/**
	 * 取得用户标题
	 * @return
	 */
	String getAccountAvatar();
	
	/**
	 * 取得标题区类型
	 * @return
	 */
	String getTitleBarType();
	
	/**
	 * 取得标题值
	 * @return
	 */
	String getTitleBarValue();
	
	/**
	 * 取得内容区最小宽度
	 * @return
	 */
	String getContentMinWidth ();
	
}
