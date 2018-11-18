package com.easyeip.jsfboot.admin.help;

/**
 * 帮助目录，一个目录关联一个页面，并且可以有多个子目录，
 * @author liao
 *
 */
public interface HelpCatalog extends HelpCatalogView {

	HelpCatalog addChild(HelpCatalog child);

	void setPage(HelpPage page);
}
