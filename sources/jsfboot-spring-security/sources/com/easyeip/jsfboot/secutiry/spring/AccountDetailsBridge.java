package com.easyeip.jsfboot.secutiry.spring;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;

public class AccountDetailsBridge implements AccountDetails {
	
	private UserDetails user;
	
	public AccountDetailsBridge(UserDetails user){
		this.user = user;
	}
	
	@Override
	public String getRealmName() {
		return null;
	}

	@Override
	public List<String> getGrantRoles() {
		List<String> roles = new ArrayList<String> ();
		for (GrantedAuthority ga : user.getAuthorities()){
			roles.add(ga.getAuthority());
		}
		return roles;
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getLoginId() {
		return user.getUsername();
	}
	
	@Override
	public String getNickname() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountExpired() {
		return !user.isAccountNonExpired();
	}

	@Override
	public boolean isAccountLocked() {
		return !user.isAccountNonLocked();
	}

	@Override
	public boolean isPasswordExpired() {
		return !user.isCredentialsNonExpired();
	}

	@Override
	public boolean isEnabled() {
		return user.isEnabled();
	}

	@Override
	public boolean isSuperAdmin() {
		return false;
	}
}
