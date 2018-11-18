package com.easyeip.jsfboot.core.registry.service.sql;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

import com.easyeip.jsfboot.core.registry.service.util.TranstaionJdbcTemplate;

public class RegistryValueDaoImpl extends JdbcDaoSupport implements RegistryValueDao {
	
	private String tableName;
	
	public RegistryValueDaoImpl(TranstaionJdbcTemplate jdbcTemplate, String tablePrefix){
		super.setDataSource(jdbcTemplate.getDataSource());
		super.setJdbcTemplate(jdbcTemplate);
		tableName = tablePrefix + BASE_TABLE_NAME;
	}

	@Override
	public String getTableName() {
		return tableName;
	}

	@Override
	public boolean delete(RegistryValueEntry entry) {
		String updateSql = "DELETE FROM " + getTableName() + " WHERE item_id=? AND value_name=?";  
        Object args[] = {entry.getItemId(), entry.getName()};
        return getJdbcTemplate().update(updateSql, args) > 0;
	}

	@Override
	public boolean save(RegistryValueEntry entry) {
		String updateSql = "UPDATE " + getTableName() +
				" SET value_content=? WHERE item_id=? AND value_name=?";
        Object args[] = {entry.getValue(), entry.getItemId(), entry.getName()};
        return getJdbcTemplate().update(updateSql, args) > 0;
	}

	@Override
	public List<RegistryValueEntry> queryValues(RegistryItemEntry item) {
		String querySql = "SELECT `item_id`,`value_name`,`value_content` FROM " +
						getTableName() + " WHERE item_id=? ORDER BY value_name";
		Object args[] = {item.getItemId()};
		return getJdbcTemplate().query(querySql, new RowMapper<RegistryValueEntry>(){
			@Override
			public RegistryValueEntry mapRow(ResultSet rs, int rowNum) throws SQLException {
				RegistryValueEntry valueEntry = new RegistryValueEntry();
				valueEntry.setItemId(rs.getLong(1));
				valueEntry.setName(rs.getString(2));
				valueEntry.setValue(rs.getString(3));
				return valueEntry;
			}
		}, args);
	}

	@Override
	public boolean createOne(RegistryItemEntry item, String valueName, String valueContent) {
		String saveSql = "INSERT INTO " + getTableName() + 
				" (item_id,value_name,value_content)VALUES(?,?,?)";  
        Object args3[] = {item.getItemId(),valueName,valueContent};
        return getJdbcTemplate().update(saveSql, args3) > 0;
	}

}
