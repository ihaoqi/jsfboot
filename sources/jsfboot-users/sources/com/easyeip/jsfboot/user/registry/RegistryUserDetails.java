package com.easyeip.jsfboot.user.registry;

import java.util.Date;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;
import com.easyeip.jsfboot.user.type.AccountEntity;

public interface RegistryUserDetails extends AccountDetails {
	
	public static final String FIELD_CREATETIME = "createTime";

	/**
	 * 取得关联的账号实体
	 * @return
	 */
	AccountEntity getAccountEntry();
	
	/**
	 * 取得账号创建时间
	 * @return
	 */
	Date getCreateTime();
	
}
