package com.easyeip.jsfboot.core.secutiry.ppm;

import com.easyeip.jsfboot.core.secutiry.AccountDetails;

public interface MenuAccessVoter {
	
	/**
	 * 判断用户是否具有菜单或动作的权限
	 * @param account
	 * @param menu
	 * @return
	 */
	boolean voter(AccountDetails account, VoterMenuDetails menu);

}
