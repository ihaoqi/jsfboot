package com.easyeip.jsfboot.secutiry.spring;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;

/**
 * 超级管理员账户
 * @author ihaoqi
 *
 */
public class SuperAdminDetails implements AccountDetails {
	
	private String username;
	private String password;
	
	public SuperAdminDetails(String username, String password){
		this.username = username;
		this.password = password;
	}

	@Override
	public String getRealmName() {
		return null;
	}

	@Override
	public List<String> getGrantRoles() {
		return new ArrayList<String>();
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getLoginId() {
		return username;
	}
	
	@Override
	public String getNickname() {
		return username;
	}

	@Override
	public boolean isAccountExpired() {
		return false;
	}

	@Override
	public boolean isAccountLocked() {
		return false;
	}

	@Override
	public boolean isPasswordExpired() {
		return false;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean isSuperAdmin() {
		return true;
	}

}
