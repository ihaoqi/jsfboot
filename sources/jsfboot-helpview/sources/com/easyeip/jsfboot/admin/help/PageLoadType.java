package com.easyeip.jsfboot.admin.help;

public enum PageLoadType {
	INCLUDE,	// 直接用ui:include包含
	REDIRECT,	// 反向请求，并聚合
	IFRAME		// 用iframe
}
