package com.easyeip.jsfboot.core.module.impl;

import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.module.type.RealmDefinition;

public class AccountRealmPackImpl implements AccountRealmPack {
	
	JsfbootModule pack;
	RealmDefinition auth;
	
	public AccountRealmPackImpl(JsfbootModule pack, RealmDefinition auth){
		this.pack = pack;
		this.auth = auth;
	}

	@Override
	public JsfbootModule getModule() {
		return pack;
	}

	@Override
	public RealmDefinition getDefinition() {
		return auth;
	}

}
