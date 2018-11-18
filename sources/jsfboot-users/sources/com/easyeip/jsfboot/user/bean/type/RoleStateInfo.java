package com.easyeip.jsfboot.user.bean.type;

import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;

public class RoleStateInfo {
	
	private RoleDetails role;

	public RoleStateInfo(RoleDetails role){
		this.role = role;
	}
	
	/**
	 * 取得英文名称
	 * @return
	 */
	public String getCode(){
		if (role == null)
			return "";
		return role.getCode();
	}
	
	/**
	 * 取得中文标题
	 * @return
	 */
	public String getTitle(){
		if (role == null)
			return "";
		return role.getTitle();
	}
}
