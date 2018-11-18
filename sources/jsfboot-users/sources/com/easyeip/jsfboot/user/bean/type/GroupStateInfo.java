package com.easyeip.jsfboot.user.bean.type;

import com.easyeip.jsfboot.user.type.AccountGroup;

public class GroupStateInfo {

	private String code;
	private AccountGroup group;
	
	public GroupStateInfo(String code, AccountGroup group) {
		this.code = code;
		this.group = group;
	}
	
	@Override
	public String toString() {
		return getTitle();
	}

	public String getTitle(){
		if (group == null)
			return code;
		return group.getTitle();
	}
	
	public boolean isExist(){
		if (group == null)
			return false;
		return true;
	}
	
	public boolean isDisabled(){
		if (group == null)
			return true;
		return !group.isEnabled();
	}

}
