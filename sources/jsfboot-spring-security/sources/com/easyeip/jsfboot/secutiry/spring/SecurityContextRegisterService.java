package com.easyeip.jsfboot.secutiry.spring;

import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.core.secutiry.JsfbootSecurityContext;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;

public class SecurityContextRegisterService extends GenericService implements JsfbootSecurityContext {
	
	private AccountDetails guestAccount;

	@Override
	public void startService(ServiceContext context) throws Exception {
		// 向jsfboot安全服务注册上下文
		JsfbootContext.getDriver().getSecutiryService().registerSecurityContext(this);
	}

	@Override
	public String getProviderName() {
		return "spring security";
	}

	@Override
	public AccountDetails getAuthentication() {
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth== null)
			return null;
		
		// 加载匿名账户
		if (auth instanceof AnonymousAuthenticationToken){
			
			if (guestAccount == null){
				try {
					guestAccount = JsfbootContext.getDriver().
							getSecutiryService().loadAccountByLoginId(auth.getName());
				} catch (Exception e) {
					return null;
				}
			}
			
			return guestAccount;
		}
		
		// 加载普通账户
		Object user = auth.getPrincipal();
		if (user instanceof AccountDetails)
			return (AccountDetails) user;
		else if (user instanceof UserDetails){
			return new AccountDetailsBridge((UserDetails) user);
		}

		return null;
	}

}
