package com.easyeip.jsfboot.paradise;

public interface ParadiseConfigForm extends ParadiseConfigValue {
	
	/**
	 * 设置标题
	 * @param title
	 */
	void setAppTitle(String title);

	/**
	 * 设置Site头部图标url
	 * @return
	 */
	void setSiteHeadLogoUrl(String value);
	
	/**
	 * 设置Admin头部图标url
	 * @return
	 */
	void setAdminHeadLogoUrl(String value);
	
	/**
	 * 设置尾部图标url
	 * @return
	 */
	void setTailLogoUrl(String value);
	
	/**
	 * 设置登录窗口图标
	 * @return
	 */
	void setLoginLogoUrl(String value);
	
	/**
	 * 设置登录窗口欢迎标题
	 * @return
	 */
	void setWelcomeTitle(String value);
	
	/**
	 * 设置忘记密码URL
	 * @return
	 */
	void setForgetPasswordUrl(String value);
	
	/**
	 * 设置Site菜单模式
	 * @return
	 */
	void setSiteMenuMode (String value);
	
	/**
	 * 设置Admin菜单模式
	 * @return
	 */
	void setAdminMenuMode(String value);
	
	/**
	 * 设置Site菜单亮度
	 * @return
	 */
	void setSiteMenuLightness (String value);
	
	/**
	 * 设置Admin菜单亮度
	 * @return
	 */
	void setAdminMenuLightness (String value);
	
	/**
	 * 设置Site菜单颜色
	 * @return
	 */
	void setSiteMenuColor(String value);
	
	/**
	 * 设置Site控件颜色
	 * @return
	 */
	void setSiteWidgetColor(String value);
	
	/**
	 * 设置Admin菜单颜色
	 * @return
	 */
	void setAdminMenuColor(String value);
	
	/**
	 * 设置Admin控件颜色
	 * @return
	 */
	void setAdminWidgetColor(String value);
	
	/**
	 * 设置用户名称
	 * @return
	 */
	void setAccountTitle(String value);
	
	/**
	 * 设置用户标题
	 * @return
	 */
	void setAccountAvatar(String value);
	
	/**
	 * 设置标题区类型
	 * @return
	 */
	void setTitleBarType(String value);
	
	/**
	 * 设置搜索框提交url
	 * @return
	 */
	void setTitleBarValue(String value);
	
	/**
	 * 设置内容区最小宽度
	 * @return
	 */
	void setContentMinWidth (String widthCss);
	
	/**
	 * 保存起来
	 * @throws Exception
	 */
	void save() throws Exception;
}
