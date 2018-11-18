package com.easyeip.jsfboot.user.registry;

import java.util.Date;
import java.util.List;

import com.easyeip.jsfboot.core.secutiry.admin.AccountRealm;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.utils.DateUtils;

public class RegistryUserDetailsImpl implements RegistryUserDetails {
	
	private AccountRealm realm;
	private AccountEntity entry;

	public RegistryUserDetailsImpl(AccountRealm realm, AccountEntity entry) {
		this.realm = realm;
		this.entry = entry;
	}

	@Override
	public List<String> getGrantRoles() {
		return entry.getRoles();
	}

	@Override
	public String getPassword() {
		return entry.getPassword();
	}
	
	@Override
	public String getLoginId() {
		return entry.getLoginId();
	}

	@Override
	public boolean isAccountExpired() {
		return entry.isAccountExpired();
	}

	@Override
	public boolean isAccountLocked() {
		return entry.isAccountLocked();
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
	public AccountEntity getAccountEntry() {
		return entry;
	}
	
	@Override
	public String getRealmName() {
		return realm.getRealmName();
	}

	@Override
	public Date getCreateTime() {
		return DateUtils.strToDate(entry.getMoreFields().get(FIELD_CREATETIME));
	}

	@Override
	public String getNickname() {
		return entry.getUserName();
	}

	@Override
	public boolean isSuperAdmin() {
		return false;
	}
}
