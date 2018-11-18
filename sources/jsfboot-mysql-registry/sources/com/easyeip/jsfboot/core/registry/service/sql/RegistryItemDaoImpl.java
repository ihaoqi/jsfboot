package com.easyeip.jsfboot.core.registry.service.sql;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.easyeip.jsfboot.core.registry.service.util.TranstaionJdbcTemplate;

public class RegistryItemDaoImpl extends JdbcDaoSupport implements RegistryItemDao {
	
	private String tableName;
	
	public RegistryItemDaoImpl(TranstaionJdbcTemplate jdbcTemplate, String tablePrefix){
		super.setDataSource(jdbcTemplate.getDataSource());
		super.setJdbcTemplate(jdbcTemplate);
		tableName = tablePrefix + BASE_TABLE_NAME;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public boolean delete(RegistryItemEntry entry) {
		String updateSql = "DELETE FROM " + getTableName() + " WHERE id_chain LIKE ?";  
        Object args[] = {entry.getIdChain() + "%"};
        return getJdbcTemplate().update(updateSql, args) > 0;
	}

	@Override
	public boolean update(RegistryItemEntry entry) {
		String updateSql = "UPDATE " + getTableName() + " SET comment=?, default_value=? WHERE item_id=?";  
        Object args[] = {entry.getComment(),entry.getDefaultValue(), entry.getItemId()};
        return getJdbcTemplate().update(updateSql, args) > 0;
	}

	@Override
	public RegistryItemEntry queryOne(long itemId) {

		String whereSql = "item_id=?";
		Object args[] = {itemId};
		
		List<RegistryItemEntry> result = queryItems(whereSql, args);
		
		if (result.isEmpty())
			return null;
		
		return result.get(0);
	}
	
	@Override
	public List<RegistryItemEntry> queryItems (String whereSql, Object args[]){
		
		String querySql = "SELECT `item_id`, `item_level`, `sort_num`, `id_chain`, " +
						"`item_path`, `item_name`, `default_value`, `comment` FROM " + 
						getTableName() + " WHERE " + whereSql;

		try{
			List<RegistryItemEntry> childs = getJdbcTemplate().query(
												querySql, new RowMapper<RegistryItemEntry>(){
				@Override
				public RegistryItemEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
					RegistryItemEntry entry = new RegistryItemEntry();
					entry.setItemId(rs.getLong(1));
					entry.setItemLevel(rs.getLong(2));
					entry.setSortNum(rs.getLong(3));
					entry.setIdChain(rs.getString(4));
					entry.setItemPath(rs.getString(5));
					entry.setItemName(rs.getString(6));
					entry.setDefaultValue(rs.getString(7));
					entry.setComment(rs.getString(8));
					
					return entry;
				}
			}, args!=null?args:new Object[0]);
			return childs;
			
		}catch (Exception e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}

	@Override
	public RegistryItemEntry createOne(RegistryItemEntry parentEntry, final String item_name) {
		
		// 设置新结点默认值
		long newSortId = 0;
		long newItemLevel = 0;
		String newIdChain;
		String newItemPath;
		
		// 获取新索引
		String sql = null;
		List<RegistryItemEntry> queryResult = null;
		
		if (parentEntry != null){
			// 设置新id
			newIdChain = parentEntry.getIdChain();
			newItemPath = getEntryFullPath(parentEntry);
			newItemLevel = parentEntry.getItemLevel() + 1;
			
			// 获取当前结点下的新索引
			sql = "item_level=? AND id_chain LIKE ? ORDER BY sort_num DESC LIMIT 1;";
			Object args2[] = {parentEntry.getItemLevel() + 1, parentEntry.getIdChain() + "%"};
			queryResult = queryItems (sql, args2);
			if (!queryResult.isEmpty()){
				newSortId = queryResult.get(0).getSortNum() + 1;
			}
		}else{
			// 设置新id
			newIdChain = ":";
			newItemPath = "/";
			
			// 获取根目录下的新索引
			sql = "item_level=0 AND item_path='/' ORDER BY sort_num DESC LIMIT 1;";
			queryResult = queryItems (sql, null);
			if (!queryResult.isEmpty()){
				newSortId = queryResult.get(0).getSortNum() + 1;
			}
		}
		
		// 插入到数据库，并取得主键值
		final String insertSql = "INSERT INTO " + getTableName() +
				" (item_level,sort_num,item_path,item_name)VALUES(?,?,?,?)";  
        KeyHolder keyHolder = new GeneratedKeyHolder();
        
        final long newItemLevel1 = newItemLevel;
        final long newSortId1 = newSortId;
        final String newItemPath1 = newItemPath;
        getJdbcTemplate().update(new PreparedStatementCreator() {  
            public PreparedStatement createPreparedStatement(  
                    Connection connection) throws SQLException {  
                PreparedStatement ps = connection.prepareStatement(insertSql,  
                        new String[] { "item_id" });
                ps.setLong(1, newItemLevel1);  
                ps.setLong(2, newSortId1);
                ps.setString(3, newItemPath1);
                ps.setString(4, item_name);
                return ps;  
            }  
        }, keyHolder);
        
        // 取回key，并更新chain
        long newItemId = keyHolder.getKey().longValue();
        newIdChain += newItemId + ":";
        sql = "UPDATE " + getTableName() + " SET id_chain=? WHERE item_id=?";
        Object args4[] = {newIdChain, newItemId};
        getJdbcTemplate().update(sql, args4);
        
        // 返回新记录
        return queryOne (newItemId);
	}

	@Override
	public boolean rename(RegistryItemEntry entry, String newName) {
		String oldPath = getEntryFullPath (entry);
		String newPath = getEntryFullPath (entry, newName);
		
		// 更新自己的名称
		String sql = "UPDATE " + getTableName() + " SET item_name=? WHERE item_id=?";
		Object args1[] = {newName, entry.getItemId()};
		if (getJdbcTemplate().update(sql, args1) == 0)
			return false;
		
		// 更新子结点路径
		sql = "UPDATE " + getTableName() +
				" SET item_path=CONCAT(?,SUBSTRING(item_path,?)) WHERE item_path LIKE ? OR item_path=?";
		Object args[] = {newPath, oldPath.length()+1, oldPath + "%", oldPath};
		getJdbcTemplate().update(sql, args);
	
		return true;
	}

	String getEntryFullPath (RegistryItemEntry entry){
		return getEntryFullPath(entry, entry.getItemName());
	}
	
	String getEntryFullPath (RegistryItemEntry entry, String name){
		String root = entry.getItemPath();
		if (root.endsWith("/"))
			return root + name;
		return root + "/" + name;
	}
}
