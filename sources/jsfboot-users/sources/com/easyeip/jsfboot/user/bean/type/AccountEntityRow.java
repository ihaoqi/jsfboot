package com.easyeip.jsfboot.user.bean.type;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.user.type.AccountGroup;
import com.easyeip.jsfboot.user.type.AccountManager;

/**
 * 账号表单
 * @author ihaoqi
 *
 */
public class AccountEntityRow extends AccountEntityWrapper {

	private AccountManager service;
	private GroupStateInfo groupInfo;
	private List<RoleStateInfo> roleList;
	
	public AccountEntityRow(AccountManager service, AccountEntity entry){
		super(entry);
		this.service = service;
	}
	
	@Override
	public void setGroupCode(String groupCode) {
		super.setGroupCode(groupCode);
		groupInfo = null;
	}
	
	/**
	 * 获取分组信息
	 * @return
	 */
	public GroupStateInfo getGroupInfo(){
		if (groupInfo == null){
			AccountGroup group = service.queryGroupOne(getGroupCode());
			groupInfo = new GroupStateInfo(getGroupCode(), group);
		}
		
		return groupInfo;
	}

	/**
	 * 获取角色列表
	 * @return
	 */
	public List<RoleStateInfo> getRoleList(){
		if (roleList == null){
			roleList = new ArrayList<RoleStateInfo>();
			for (String role : this.getRoles()){
				RoleDetails r = service.queryRoleOne(role);
				if (r != null){
					roleList.add(new RoleStateInfo(r));
				}
			}
		}
		
		return roleList;
	}
}
