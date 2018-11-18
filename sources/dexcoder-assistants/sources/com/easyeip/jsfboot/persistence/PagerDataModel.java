package com.easyeip.jsfboot.persistence;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortMeta;
import org.primefaces.model.SortOrder;
import org.springframework.jdbc.core.JdbcTemplate;

import com.dexcoder.assistant.persistence.BoundSql;
import com.dexcoder.assistant.persistence.Criteria;
import com.dexcoder.assistant.persistence.DefaultJdbcDao;
import com.dexcoder.assistant.persistence.JdbcQuery;
import com.dexcoder.assistant.persistence.SqlAssembleUtils;

/**
 * 支持primefaces的分页数据模型
 * @author liao
 *
 */
public class PagerDataModel<T> extends LazyDataModel<Object> {

	private static final long serialVersionUID = 1L;
	
	private DefaultJdbcDao jdbcDao;
	private Criteria criteria;
	private PagerDataWrapper<T> dataWrapper;
	private String DATABASE;
	
	/**
	 * 创建数据分页模型
	 * @param jdbcDao jdbcDao
	 * @param criteria 查询条件
	 * @param pageSize 每页行数
	 */
	public PagerDataModel(JdbcQuery jdbcDao, Criteria criteria, int pageSize){
		this(jdbcDao, criteria, pageSize, null);
	}
	
	/**
	 * 创建数据分页模型
	 * @param jdbcDao jdbcDao
	 * @param criteria 查询条件
	 * @param pageSize 每页行数
	 * @param dataWrapper 数据行对象包装
	 */
	public PagerDataModel(JdbcQuery jdbcDao, Criteria criteria,
							int pageSize, PagerDataWrapper<T> dataWrapper){
		
		this.jdbcDao = (DefaultJdbcDao) jdbcDao;
		this.criteria = criteria;
		this.dataWrapper = dataWrapper;

		// 查询行数量
		this.setPageSize(pageSize);
		this.setRowCount(jdbcDao.queryCount(criteria.clone()));
	}
	
    public List<Object> load(int first, int pageSize, String sortField, SortOrder sortOrder, Map<String,Object> filters) {
    	List<SortMeta> sortmeta = null;
    	if (sortField != null && sortField.length() > 0){
	    	sortmeta = new ArrayList<SortMeta> ();
	    	sortmeta.add(new SortMeta(null, sortField, sortOrder, null));
    	}
    	return load (first, pageSize, sortmeta, filters); 
	}
    
    @SuppressWarnings("unchecked")
	public List<Object> load(int first, int pageSize, List<SortMeta> multiSortMeta, Map<String,Object> filters) {
    	
        // 获取数据库类型
        JdbcTemplate target = (JdbcTemplate) jdbcDao.getJdbcTemplate();
        if (DATABASE == null){
        	try{
        		Connection conn = target.getDataSource().getConnection();
		        DatabaseMetaData metaData = conn.getMetaData();
		        DATABASE = metaData.getDatabaseProductName().toUpperCase();
		        conn.close();
        	}catch (Exception e){
        		e.printStackTrace();
        		return null;
        	}
        }
        
        // 添加排序、过滤条件
        Criteria loadCriteria = getLoadCriteria(multiSortMeta, filters);
        
        // 获取分页sql并执行
    	BoundSql boundSql = SqlAssembleUtils.buildListSql(null, loadCriteria, jdbcDao.getNameHandler());
        String pagerSql = getPageSql(boundSql.getSql(), first, pageSize);
        List<?> list = target.query(pagerSql, boundSql.getParams().toArray(),
        					jdbcDao.getRowMapper(criteria.getEntityClass()));
        
        // 重新包装对象
        List<Object> result = null;
        if (dataWrapper != null){
        	result = new ArrayList<Object> ();
        	for (Object obj : list){
        		result.add(dataWrapper.wrapper((T) obj));
        	}
        }else{
        	result = (List<Object>) list;
        }
        
        return result;
    }
    
    /**
     * 获取排序与过滤的条件
     * @param multiSortMeta
     * @param filters
     * @return
     */
    private Criteria getLoadCriteria(List<SortMeta> multiSortMeta, Map<String,Object> filters){
    	Criteria loadCriteria = criteria.clone();
    	
        // 添加排序条件
        if (multiSortMeta != null && !multiSortMeta.isEmpty()){
        	// 清除原有排序
        	loadCriteria.clearOrder();
        	// 添加排序字段
        	for (SortMeta sort : multiSortMeta){
        		if (sort.getSortOrder() == SortOrder.ASCENDING){
        			loadCriteria.asc(parseFieldName(sort.getSortField()));
        		}else if (sort.getSortOrder() == SortOrder.DESCENDING){
        			loadCriteria.desc(parseFieldName(sort.getSortField()));
        		}
        	}
        }
        
        // 不支持滤字段（下面代码有问题，不能用）
//        if (filters != null && !filters.isEmpty()){
//        	for (Entry<String,Object> filter : filters.entrySet()){
//        		if (loadCriteria.isWhere())
//        			loadCriteria.or(filter.getKey(), new Object[]{filter.getValue()});
//        		else
//        			loadCriteria.where(filter.getKey(), new Object[]{filter.getValue()});
//        	}
//        }
        
        return loadCriteria;
    }
    
    /**
     * 分析字段名，如 source.studentCode 分析为 student_code
     * @param columnName
     * @return
     */
    private String parseFieldName (String columnName){
    	String[] names = columnName.split("\\.");
    	String lastName = null;
    	for (int i = names.length - 1; i >= 0; i --){
    		if (names[i].length() > 0 && lastName == null){
    			lastName = names[i];
    			break;
    		}
    	}
    	
    	if (lastName == null)
    		lastName = columnName;
    	
    	return lastName;
    }
    
    /**
     * 获取分页查询sql
     * 
     * @param sql
     * @param pager
     * @return
     */
    private String getPageSql(String sql, int first, int pageSize) {
        StringBuilder pageSql = new StringBuilder(200);
        if ("MYSQL".equals(DATABASE)) {
            pageSql.append(sql);
            pageSql.append(" limit ");
            pageSql.append(first);
            pageSql.append(",");
            pageSql.append(pageSize);
        } else if ("ORACLE".equals(DATABASE)) {
            pageSql.append("select * from ( select rownum num,temp.* from (");
            pageSql.append(sql);
            pageSql.append(") temp where rownum <= ").append(first + pageSize);
            pageSql.append(") where num > ").append(first);
        }
        return pageSql.toString();
    }
}
