package com.easyeip.jsfboot.secutiry.spring;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.utils.StringKit;

public class AccountDetailsService implements UserDetailsService {

	private String superAdminUsername;
	private String superAdminPassword;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// 创建超级用户
		if (StringKit.notEmpty(superAdminUsername) &&
				StringKit.equals(superAdminUsername, username)){
			return new SpringUserDetails (
					new SuperAdminDetails(superAdminUsername, superAdminPassword));
		}
		
		// 从账号服务中加载
		AccountDetails account = null;
		try {
			account = JsfbootContext.getDriver().
					getSecutiryService().loadAccountByLoginId(username);
		} catch (Exception e1) {
			e1.printStackTrace();
			throw new UsernameNotFoundException(e1.getMessage());
		}
		
		if (account == null){
			throw new UsernameNotFoundException("账号不存在。");
		}
		return new SpringUserDetails(account);
	}

	public String getSuperAdminUsername() {
		return superAdminUsername;
	}

	public void setSuperAdminUsername(String superAdminUsername) {
		this.superAdminUsername = superAdminUsername;
	}

	public String getSuperAdminPassword() {
		return superAdminPassword;
	}

	public void setSuperAdminPassword(String superAdminPassword) {
		this.superAdminPassword = superAdminPassword;
	}

}
