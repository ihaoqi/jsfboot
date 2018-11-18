package com.easyeip.jsfboot.user.type;

import java.util.List;

/**
 * 账户分组（组织机构）
 * @author liao
 *
 */
public interface AccountGroup {
	
	/**
	 * 获取分组关键字，分组的唯一标记
	 * @return
	 */
	String getId();
	
	/**
	 * 取得英文名称
	 * @return
	 */
	String getCode();
	
	/**
	 * 分取中文标题
	 * @return
	 */
	String getTitle();
	
	/**
	 * 取得说明
	 * @return
	 */
	String getExplain();
	
	/**
	 * 是否可用
	 * @return
	 */
	boolean isEnabled();
	
	void setCode(String code);

	void setTitle(String title);

	void setExplain(String explain);
	
	void setEnabled (boolean enabled);
	
	/**
	 * 取得上级分组
	 * @return
	 */
	AccountGroup getParent();
	
	/**
	 * 取得子分组
	 * @return 返回只读列表
	 */
	List<AccountGroup> getChildren();
}
