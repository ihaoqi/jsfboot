package com.easyeip.jsfboot.user.bean.type;

import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.user.type.AccountEntity;


public class AccountEntityWrapper implements AccountEntity {
	
	private AccountEntity entry;
	
	public AccountEntityWrapper(AccountEntity entry){
		this.entry = entry;
	}
	
	public AccountEntity getWrapper(){
		return entry;
	}

	@Override
	public String getId() {
		return entry.getId();
	}

	@Override
	public String getGroupCode() {
		return entry.getGroupCode();
	}

	@Override
	public String getLoginId() {
		return entry.getLoginId();
	}

	@Override
	public String getUserName() {
		return entry.getUserName();
	}

	@Override
	public boolean isAccountExpired() {
		return entry.isAccountExpired();
	}

	@Override
	public boolean isAccountLocked() {
		return entry.isAccountExpired();
	}

	@Override
	public boolean isPasswordExpired() {
		return entry.isPasswordExpired();
	}

	@Override
	public boolean isEnabled() {
		return entry.isEnabled();
	}

	@Override
	public Map<String, String> getMoreFields() {
		return entry.getMoreFields();
	}

	@Override
	public void setGroupCode(String groupCode) {
		entry.setGroupCode(groupCode);
	}

	@Override
	public void setUserName(String name) {
		entry.setUserName(name);
	}

	@Override
	public void setAccountExpired(boolean expired) {
		entry.setAccountExpired(expired);
	}

	@Override
	public void setAccountLocked(boolean locked) {
		entry.setAccountLocked(locked);
	}

	@Override
	public void setPasswordExpired(boolean expired) {
		entry.setPasswordExpired(expired);
	}

	@Override
	public void setEnabled(boolean enabled) {
		entry.setEnabled(enabled);
	}

	@Override
	public void setRoles(String roles) {
		entry.setRoles(roles);
	}

	@Override
	public void setMoreField(String name, String value) {
		entry.setMoreField(name, value);
	}

	@Override
	public List<String> getRoles() {
		return entry.getRoles();
	}

	@Override
	public String getPassword() {
		return entry.getPassword();
	}

	@Override
	public String getExplain() {
		return entry.getExplain();
	}

	@Override
	public void setExplain(String explain) {
		entry.setExplain(explain);
	}

}
