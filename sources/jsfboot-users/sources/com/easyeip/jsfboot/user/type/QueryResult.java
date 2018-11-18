package com.easyeip.jsfboot.user.type;

import java.util.List;

/**
 * 账户查询结果
 * @author liao
 *
 */
public interface QueryResult {
	
	public static final int NOT_PAGINATION = -1;
	
	/**
	 * 取得账户数量
	 * @return
	 */
	int getTotalRows();

	/**
	 * 取得每页行数
	 * @return
	 */
	int getPageRows();
	
	/**
	 * 取得页数
	 * @return
	 */
	int getPageCount();
	
	/**
	 * 取得指定页的列表
	 * @param page 页号，索引从0开始
	 * @return
	 */
	List<AccountEntity> getPageAt(int page);
	
	/**
	 * 取得第一个
	 * @return
	 */
	AccountEntity getFirstOne();
}
