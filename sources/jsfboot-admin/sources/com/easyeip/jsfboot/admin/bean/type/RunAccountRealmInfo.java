package com.easyeip.jsfboot.admin.bean.type;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.RealmDefinition;
import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.secutiry.admin.RealmSingleton;

public class RunAccountRealmInfo implements AccountRealmPack {
	
	private RealmSingleton auth;
	
	public RunAccountRealmInfo(RealmSingleton auth){
		this.auth = auth;
	}

	@Override
	public JsfbootModule getModule() {
		return auth.getModule();
	}

	@Override
	public RealmDefinition getDefinition() {
		return auth.getDefinition();
	}
	
	public String getLongName(){
		return auth.getName();
	}
	
	public boolean isInstance(){
		return auth.getInstance() != null;
	}

//	public boolean isSupportQuery(){
//		if (auth.getInstance() == null)
//			return false;
//		
//		return auth.getInstance().getAccountSearcher() != null;
//	}
}
