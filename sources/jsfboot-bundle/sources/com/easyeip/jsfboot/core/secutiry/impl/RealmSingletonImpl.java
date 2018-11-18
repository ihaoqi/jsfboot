package com.easyeip.jsfboot.core.secutiry.impl;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.module.type.RealmDefinition;
import com.easyeip.jsfboot.core.secutiry.admin.AccountRealm;
import com.easyeip.jsfboot.core.secutiry.admin.RealmSingleton;

public class RealmSingletonImpl implements RealmSingleton {

	private AccountRealmPack uai;
	private AccountRealm instance = null;
	
	public RealmSingletonImpl(AccountRealmPack uai){
		this.uai = uai;
		
		// 初使化
		initServiceInstance();
	}
	
	@Override
	public String toString() {
		return getName();
	}

	@Override
	public JsfbootModule getModule() {
		return uai.getModule();
	}

	@Override
	public RealmDefinition getDefinition() {
		return uai.getDefinition();
	}

	@Override
	public boolean hasBeenCreated() {
		return instance != null;
	}

	@Override
	public AccountRealm getInstance() {
		return instance;
	}

	/**
	 * 创建实现
	 * @throws Exception
	 */
	public AccountRealm createInstance() throws Exception{
		if (instance != null)
			return instance;

		RealmDefinition def = getDefinition();
		if (!StringKit.isEmpty(def.getRefService())){
			
			Object service = JsfbootContext.getDriver().getServiceManager().getService(def.getRefService());
			if (service == null){
				throw new Exception (def.getRefService() + "服务不存在。");
			}
			if (!(service instanceof AccountRealm)){
				throw new Exception (def.getRefService() + "不具有" + AccountRealm.class.getName() + "接口。");
			}
			
			instance = (AccountRealm) service;
			
		}else if (!StringKit.isEmpty(def.getUseClass())){
			
			Class<?> cls = JsfbootContext.class.getClassLoader().loadClass(def.getUseClass());
			Object service = cls.newInstance();

			if (!(service instanceof AccountRealm)){
				throw new Exception (def.getUseClass() + "不具有" + AccountRealm.class.getName() + "接口。");
			}
			
			instance = (AccountRealm) service;
			
		}else{
			throw new Exception ("没有定义具体的类或服务。");
		}
		
		return instance;
	}
	
	/**
	 * 预先初使化实现
	 * @return
	 */
	private boolean initServiceInstance(){
		RealmDefinition def = getDefinition();
		if (!StringKit.isEmpty(def.getRefService())){
			
			Object service = JsfbootContext.getDriver().getServiceManager().getService(def.getRefService());
			if (service != null && (service instanceof AccountRealm)){
				instance = (AccountRealm) service;
				return true;
			}
		}
		return false;
	}

	@Override
	public String getName() {
		return this.getModule().getName();
	}

//	@Override
//	public String getLongName() {
//		return this.getModule().getName() + "/" + this.getName();
//	}
}
