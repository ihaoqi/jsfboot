package com.easyeip.jsfboot.core.module.type.impl;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.OutcomeUtils;
import com.easyeip.jsfboot.core.module.type.ThemePageTemplate;
import com.easyeip.jsfboot.core.module.type.schema.ThemePageTemplateType;

/**
 * 主题中的页面模板实现类
 * @author ihaoqi
 *
 */
public class ThemePageTemplateImpl implements ThemePageTemplate {
	
	private ThemePageTemplateType templType;
	private JsfbootModule pack;
	
	public ThemePageTemplateImpl(JsfbootModule pack, ThemePageTemplateType templType){
		this.templType = templType;
		this.pack = pack;
	}

	@Override
	public String getMain() {
		return OutcomeUtils.fullOutcome(pack, templType.getMainPage());
	}

	@Override
	public String getContent() {
		if (StringKit.isEmpty(templType.getMainPage()))
			return getMain();
		else
			return OutcomeUtils.fullOutcome(pack, templType.getOnlyContent());
	}

	@Override
	public String getBlank() {
		if (StringKit.isEmpty(templType.getMainPage()))
			return getMain();
		else
			return OutcomeUtils.fullOutcome(pack, templType.getBlankPage());
	}
}
