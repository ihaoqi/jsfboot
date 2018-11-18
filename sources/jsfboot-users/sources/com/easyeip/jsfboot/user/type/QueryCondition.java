package com.easyeip.jsfboot.user.type;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 帐号查询条件
 * @author liao
 *
 */
public class QueryCondition {
	
	private String groupKey;
	private boolean allChilds;

	private List<Where> filterList;
	private List<OrderBy> orderList;
	
	private QueryCondition(String groupKey, boolean allChilds){
		this.groupKey = groupKey;
		this.allChilds = allChilds;
		
		filterList = new ArrayList<Where> ();
		orderList = new ArrayList<OrderBy> ();
	}
	
	/**
	 * 查询一个分组
	 * @param groupKey 要查询的分组，为空表示查询所有分组
	 * @param allChilds 是否包含所有子账户
	 * @return
	 */
	public static QueryCondition group(String groupKey, boolean allChilds){
		return new QueryCondition(groupKey, allChilds);
	}
	
	/**
	 * 查询所有
	 * @return
	 */
	public static QueryCondition all(){
		return new QueryCondition(null, true);
	}

	/**
	 * 添加字段比较
	 * @param fieldName	字段值
	 * @param equalValue 字段值
	 * @return
	 */
	public QueryCondition equal(String fieldName, String equalValue){
		filterList.add(new Where (fieldName, equalValue));
		return this;
	}
	
	/**
	 * 添加排序字段
	 * @param fieldName	字段名称
	 * @param orderType	排序类型，如DESC
	 * @return
	 */
	public QueryCondition order(String fieldName, OrderByType orderType){
		orderList.add(new OrderBy (fieldName, orderType));
		return this;
	}

	/**
	 * 获取要查找的分组Key，为空表示查询所有
	 * @return
	 */
	public String getGroupKey() {
		return groupKey;
	}
	
	/**
	 * 取得所有过滤字段
	 * @return
	 */
	public List<Where> getFilterFields(){
		return Collections.unmodifiableList(filterList);
	}
	
	/**
	 * 取得所有查询字段
	 * @return
	 */
	public List<OrderBy> getOrderFields(){
		return Collections.unmodifiableList(orderList);
	}

	/**
	 *  是否查询分组下所有子账号（包含子分组），getGroupKey()不为空时有效
	 * @return
	 */
	public boolean isAllChilds() {
		return allChilds;
	}
	
	public class Where{
		private String field;
		private String value;
		
		public Where(String field, String value){
			this.field = field;
			this.value = value;
		}

		public String getValue() {
			return value;
		}

		public String getField() {
			return field;
		}
	}
	
	public class OrderBy{
		private String field;
		private OrderByType value;
		
		public OrderBy(String field, OrderByType value){
			this.field = field;
			this.value = value;
		}

		public OrderByType getValue() {
			return value;
		}

		public String getField() {
			return field;
		}
	}
}
