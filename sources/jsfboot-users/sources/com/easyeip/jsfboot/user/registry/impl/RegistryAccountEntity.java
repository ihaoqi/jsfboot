package com.easyeip.jsfboot.user.registry.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.secutiry.PasswordSaltUtils;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.utils.DateUtils;
import com.easyeip.jsfboot.utils.StringKit;

public class RegistryAccountEntity implements AccountEntity {
	
	public static final String MORE_CREATETIME = "createTime";
	
	private RegistryItem regItem;
	
	private String id;
	private String loginId;
	private String password;
	
	private String groupCode;
	private String userName;
	private String explain;
	private boolean enabled;
	private boolean accountExpired;
	private boolean accountLocked;
	private boolean passwordExpired;
	private List<String> roleList;
	private Map<String, String> moreParams;
	
	public RegistryAccountEntity(){
		enabled = true;
		accountExpired = false;
		accountLocked = false;
		passwordExpired = false;
		roleList = new ArrayList<String> ();
		moreParams = new HashMap<String,String>();
		moreParams.put(MORE_CREATETIME, DateUtils.dateToStr(new Date()));
	}

	public RegistryAccountEntity(RegistryItem regItem) { 
		this();
		this.regItem = regItem;
		
		// 加载数据
		loginId = regItem.getDefaultValue();
		id = loginId;
		
		userName = regItem.getValue(FIELD_USERNAME);
		groupCode = regItem.getValue(FIELD_GROUPID);
		explain = regItem.getValue(FIELD_EXPLAIN);
		password = regItem.getValue(FIELD_PASSWORD);
		
		enabled = StringKit.isTrue(regItem.getValue(FIELD_ENABLED), true);
		accountExpired = StringKit.isTrue(regItem.getValue(FIELD_ACCOUNTEXPIRED), false);
		accountLocked = StringKit.isTrue(regItem.getValue(FIELD_ACCOUNTLOCKED), false);
		passwordExpired = StringKit.isTrue(regItem.getValue(FIELD_PASSWORDEXPIRED), false);
		
		setRoles (regItem.getValue(FIELD_ROLES));
		
		moreParams.put(MORE_CREATETIME, regItem.getValue(MORE_CREATETIME));
	}
	
	/**
	 * 测试输入的密码是否有效
	 * @param inputPassword
	 * @return
	 */
	public boolean checkPassword (String inputPassword){
		String newSalt = PasswordSaltUtils.encode(inputPassword, getLoginId());
		String oldSalt = this.getPassword();
		return StringKit.equals(newSalt, oldSalt);
	}

	/**
	 * 保存密码和是否过期两个字段
	 * @param registry
	 * @throws RegistryException
	 */
	public void savePassword(RegistryService registry)throws RegistryException{
		// 用账号做盐加密密码
		String saltPwd = PasswordSaltUtils.encode(getPassword(), getLoginId());
		regItem.setValue(FIELD_PASSWORD, saltPwd);
		regItem.setValue(FIELD_PASSWORDEXPIRED, Boolean.valueOf(isPasswordExpired()).toString());
		registry.updateItem(regItem);
	}
	
	/**
	 * 保存账号信息
	 * @param registry
	 * @param regItem
	 * @param user
	 * @throws RegistryException
	 */
	public static void saveEntry(RegistryService registry,RegistryItem regItem,
			AccountEntity user) throws RegistryException{

		regItem.setValue(FIELD_USERNAME, user.getUserName());
		regItem.setValue(FIELD_GROUPID, user.getGroupCode());
		regItem.setValue(FIELD_EXPLAIN, user.getExplain());
		
		// 这个函数只在新建账号时才保存密码和账号，否则要用 savePassword 来保存密码
		if (user.getId() == null){
			regItem.setDefaultValue(user.getLoginId());
			// 用账号做盐加密密码
			String saltPwd = PasswordSaltUtils.encode(user.getPassword(), user.getLoginId());
			regItem.setValue(FIELD_PASSWORD, saltPwd);
		}
		
		regItem.setValue(FIELD_ROLES, StringKit.listToString(user.getRoles(), ","));
		regItem.setValue(FIELD_ACCOUNTEXPIRED, Boolean.valueOf(user.isAccountExpired()).toString());
		regItem.setValue(FIELD_ACCOUNTLOCKED, Boolean.valueOf(user.isAccountLocked()).toString());
		regItem.setValue(FIELD_PASSWORDEXPIRED, Boolean.valueOf(user.isPasswordExpired()).toString());
		regItem.setValue(FIELD_ENABLED, Boolean.valueOf(user.isEnabled()).toString());
		
		for (Entry<String,String> mp : user.getMoreFields().entrySet()){
			regItem.setValue(mp.getKey(), mp.getValue());
		}

		registry.updateItem(regItem);
	}
	
	public RegistryItem getRegistryItem() {
		return regItem;
	}

	@Override
	public String getId() {
		return id;
	}

	@Override
	public String getGroupCode() {
		return groupCode;
	}

	@Override
	public String getLoginId() {
		return loginId;
	}
	
	public void setLoginId(String loginid){
		this.loginId = loginid;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getExplain() {
		return explain;
	}

	public void setExplain(String explain) {
		this.explain = explain;
	}

	@Override
	public String getUserName() {
		return userName;
	}

	@Override
	public boolean isAccountExpired() {
		return accountExpired;
	}

	@Override
	public boolean isAccountLocked() {
		return accountLocked;
	}

	@Override
	public boolean isPasswordExpired() {
		return passwordExpired;
	}
	
	@Override
	public void setPasswordExpired(boolean expired) {
		this.passwordExpired = expired;
	}
	
	public boolean isPasswordNonExpired() {
		return !passwordExpired;
	}
	
	public void setPasswordNonExpired(boolean nonExpired) {
		this.passwordExpired = !nonExpired;
	}

	@Override
	public boolean isEnabled() {
		return enabled;
	}

	@Override
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}

	@Override
	public void setUserName(String name) {
		this.userName = name;
	}

	@Override
	public void setAccountExpired(boolean expired) {
		this.accountExpired = expired;
	}

	@Override
	public void setAccountLocked(boolean locked) {
		this.accountLocked = locked;
	}

	@Override
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Override
	public List<String> getRoles() {
		return roleList;
	}
	
	public void setRoles(List<String> list) {
		roleList.clear();
		roleList.addAll(list);
	}
	
	@Override
	public void setRoles(String roles) {
		roleList.clear();
		
		if (StringKit.isEmpty(roles))
			return;
		
		roleList.addAll(StringKit.stringSplit(roles, ","));

	}
	
	@Override
	public Map<String, String> getMoreFields() {
		return moreParams;
	}

	@Override
	public void setMoreField(String name, String value) {
		moreParams.put(name, value);
	}
}
