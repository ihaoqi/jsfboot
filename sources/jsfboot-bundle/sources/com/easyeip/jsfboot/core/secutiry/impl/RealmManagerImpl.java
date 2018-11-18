package com.easyeip.jsfboot.core.secutiry.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.ModuleManager;
import com.easyeip.jsfboot.core.module.type.AccountRealmPack;
import com.easyeip.jsfboot.core.secutiry.admin.RealmManager;
import com.easyeip.jsfboot.core.secutiry.admin.RealmSingleton;

/**
 * 用户适配器管理服务实现
 * @author ihaoqi
 *
 */
public class RealmManagerImpl implements RealmManager {
	
	private ModuleManager modmgr = null;
	private List<RealmSingleton> instList = null;
	
	private List<RealmSingleton> current;
	
	public RealmManagerImpl(ModuleManager modmgr){
		this.modmgr = modmgr;
		current = new ArrayList<RealmSingleton>();
	}

	@Override
	public List<RealmSingleton> getRealmList() {
		
		Map<String, AccountRealmPack> exists = new HashMap<String, AccountRealmPack>();
		
		if (instList == null){
			instList = new ArrayList<RealmSingleton>();
			
			for (AccountRealmPack uai : modmgr.getAllAccountRealm()){
				instList.add( new RealmSingletonImpl (uai));
				exists.put(uai.getModule().getName(), uai);
			}
			
		}
		return instList;
	}

	@Override
	public void selectCurrentRealm(String name) throws Exception {
		
		// 看是否重复
		for (RealmSingleton realm : current){
			if (realm.getName().equals(name)){
				return;
			}
		}
		
		// 先找到模块
		RealmSingleton findRealm = null;
		for (RealmSingleton uai : getRealmList()){
			
			if (StringKit.equals(uai.getName(), name)){
				findRealm = uai;
				break;
			}
		}
		
		if (findRealm == null){
			throw new Exception("不存在名称为" + name + "的授权适配器。");
		}

		// 实例化
		if (findRealm.hasBeenCreated() == false){
			try{
				findRealm.createInstance();
			}catch(InstantiationException e){
				throw new Exception("创建" + name + "用户认证适配器失败，类无法创建。");
			}catch (Exception e){
				throw new Exception("创建" + name + "用户认证适配器失败，" + e.getMessage());
			}
		}
		
		// 添加到列表
		current.add(findRealm);
	}

	@Override
	public RealmSingleton getCurrentRealm(String name) {
		for (RealmSingleton realm : current){
			if (realm.getName().equals(name)){
				return realm;
			}
		}
		
		return null;
	}

	@Override
	public List<RealmSingleton> getCurrentRealm() {
		return current;
	}
}
