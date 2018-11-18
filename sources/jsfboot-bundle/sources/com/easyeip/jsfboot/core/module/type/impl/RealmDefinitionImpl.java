package com.easyeip.jsfboot.core.module.type.impl;

import com.easyeip.jsfboot.core.module.type.RealmDefinition;
import com.easyeip.jsfboot.core.module.type.schema.AccountRealmType;

public class RealmDefinitionImpl implements RealmDefinition {
	
	AccountRealmType type;
	
	public RealmDefinitionImpl(AccountRealmType adapterType){
		this.type = adapterType;
	}

	@Override
	public String getUseClass() {
		return type.getUseClass();
	}

	@Override
	public String getRefService() {
		return type.getRefService();
	}

	@Override
	public String getTitle() {
		return type.getTitle();
	}

	@Override
	public String getExplain() {
		return type.getExplain();
	}

}
