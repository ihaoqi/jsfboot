package com.easyeip.jsfboot.core.secutiry;

import java.util.List;

public abstract class AccountDetailsWrapper implements AccountDetails {
	
	private AccountDetails wrapper;
	
	public AccountDetailsWrapper(AccountDetails wrapper){
		this.wrapper = wrapper;
	}
	
	public AccountDetails getWrapper(){
		return wrapper;
	}

	@Override
	public String getRealmName() {
		return wrapper.getRealmName();
	}

	@Override
	public List<String> getGrantRoles() {
		return wrapper.getGrantRoles();
	}

	@Override
	public String getPassword() {
		return wrapper.getPassword();
	}

	@Override
	public String getLoginId() {
		return wrapper.getLoginId();
	}

	@Override
	public String getNickname() {
		return wrapper.getNickname();
	}

	@Override
	public boolean isSuperAdmin() {
		return wrapper.isSuperAdmin();
	}

	@Override
	public boolean isAccountExpired() {
		return wrapper.isAccountExpired();
	}

	@Override
	public boolean isAccountLocked() {
		return wrapper.isAccountLocked();
	}

	@Override
	public boolean isPasswordExpired() {
		return wrapper.isPasswordExpired();
	}

	@Override
	public boolean isEnabled() {
		return wrapper.isEnabled();
	}

}
