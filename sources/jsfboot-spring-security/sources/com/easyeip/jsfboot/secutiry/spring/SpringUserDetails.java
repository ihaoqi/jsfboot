package com.easyeip.jsfboot.secutiry.spring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.AccountDetailsWrapper;

/**
 * 适配spring的用户描述对象
 * @author liao
 *
 */
public class SpringUserDetails extends AccountDetailsWrapper implements UserDetails {

	private static final long serialVersionUID = 1L;

	public SpringUserDetails(AccountDetails details) {
		super(details);
	}
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// 获取用户角色列表
		List<GrantedAuthority> authors = new ArrayList<GrantedAuthority>();
		for (String role : getWrapper().getGrantRoles()){
			authors.add(new SimpleGrantedAuthority(role));
		}
		return authors;
	}

	@Override
	public String getUsername() {
		return getWrapper().getLoginId();
	}

	@Override
	public boolean isAccountNonExpired() {
		return !getWrapper().isAccountExpired();
	}

	@Override
	public boolean isAccountNonLocked() {
		return !getWrapper().isAccountLocked();
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return !getWrapper().isPasswordExpired();
	}
}
