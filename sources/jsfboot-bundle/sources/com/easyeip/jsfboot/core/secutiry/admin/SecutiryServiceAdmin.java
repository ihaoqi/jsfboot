package com.easyeip.jsfboot.core.secutiry.admin;

import com.easyeip.jsfboot.core.secutiry.SecutiryService;

public interface SecutiryServiceAdmin extends SecutiryService {

	RealmManager getRealmManager();
	
}
