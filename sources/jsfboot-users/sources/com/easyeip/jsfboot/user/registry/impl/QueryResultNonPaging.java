package com.easyeip.jsfboot.user.registry.impl;

import java.util.List;

import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.user.type.QueryResult;

/**
 * 不分页的查询结果
 * @author ihaoqi
 *
 */
public class QueryResultNonPaging implements QueryResult {
	
	private int pageRows;
	private List<AccountEntity> rowList;
	
	public QueryResultNonPaging(int pageRow, List<AccountEntity> rowList) {
		this.pageRows = pageRow;
		this.rowList = rowList;
	}

	@Override
	public int getTotalRows() {
		return rowList.size();
	}

	@Override
	public int getPageRows() {
		return pageRows;
	}

	@Override
	public int getPageCount() {
		return rowList.size() == 0 ? 0 : 1;
	}

	@Override
	public List<AccountEntity> getPageAt(int page) {
		return rowList;
	}

	@Override
	public AccountEntity getFirstOne() {
		if (rowList.size() > 0)
			return rowList.get(0);
		return null;
	}

}
