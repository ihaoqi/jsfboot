package com.easyeip.jsfboot.secutiry.spring;

import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.SecutiryService;

public class JsfbootJpnVoter extends RoleVoter {
	
	private static final String JSFBOOT_DENYALL = "JSFBOOT_DENYALL";	// 拒绝所有
	private static final String JSFBOOT_PERMITALL = "JSFBOOT_PERMITALL";// 允许所有
	private static final String JSFBOOT_ACCOUNT = "JSFBOOT_ACCOUNT";	// 根据账号
		
	public JsfbootJpnVoter(){
		this.setRolePrefix("JSFBOOT_");
	}
	
	public int vote(Authentication authentication, Object object,
			Collection<ConfigAttribute> attributes) {
		
		int result = ACCESS_ABSTAIN;
		Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
		
		// 取得当前账号
		AccountDetails account = null;
		if (authentication.getPrincipal() instanceof AccountDetails){
			account = (AccountDetails) authentication.getPrincipal();
		}
		
		FilterInvocation filter = null;
		if (object instanceof FilterInvocation){
			filter = (FilterInvocation) object;
		}

		for (ConfigAttribute attribute : attributes) {

			if (!this.supports(attribute))
				continue;
			
			// 默认拒绝访问
			result = ACCESS_DENIED;
			
			// 直接拒绝
			if (attribute.getAttribute().equals(JSFBOOT_DENYALL))
				return result;
			
			// 直接放行
			if (attribute.getAttribute().equals(JSFBOOT_PERMITALL))
				return ACCESS_GRANTED;
			
			// 判断页面权限
			if (attribute.getAttribute().equals(JSFBOOT_ACCOUNT)){
				
				// 判断是否允许访问这个url
				if (account != null && filter != null){
					SecutiryService ss = JsfbootContext.getDriver().getSecutiryService();
					if (ss.checkPermission(account, new FilterUrlRequestInfo(filter))){
						result = ACCESS_GRANTED;
					}
				}
				
				return result;
			}
			
			// 查看用户是否包含这个角色
			for (GrantedAuthority authority : authorities) {
				if (attribute.getAttribute().equals(authority.getAuthority())) {
					return ACCESS_GRANTED;
				}
			}
		}

		return result;
	}
}
