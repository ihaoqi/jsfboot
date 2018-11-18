package com.dexcoder.assistant.persistence;

import org.springframework.jdbc.core.JdbcOperations;
import org.springframework.jdbc.core.RowMapper;

/**
 * jdbc操作dao
 *
 * Created by liyd on 3/3/15.
 */
public interface JdbcDao extends JdbcQuery {

    /**
     * 插入一条记录 自动处理主键
     *
     * @param entity
     * @return
     */
    public Long insert(Object entity);

    /**
     * 插入一条记录 自动处理主键
     *
     * @param criteria the criteria
     * @return long long
     */
    public Long insert(Criteria criteria);

    /**
     * 保存一条记录，不处理主键
     *
     * @param entity
     */
    public void save(Object entity);

    /**
     * 保存一条记录，不处理主键
     *
     * @param criteria the criteria
     */
    public void save(Criteria criteria);

    /**
     * 根据Criteria更新
     *
     * @param criteria the criteria
     */
    public void update(Criteria criteria);

    /**
     * 根据实体更新
     *
     * @param entity the entity
     */
    public void update(Object entity);

    /**
     * 根据Criteria删除
     *
     * @param criteria the criteria
     */
    public void delete(Criteria criteria);

    /**
     * 删除记录 此方法会以实体中不为空的字段为条件
     *
     * @param entity
     */
    public void delete(Object entity);

    /**
     * 删除记录
     *
     * @param clazz the clazz
     * @param id the id
     */
    public void delete(Class<?> clazz, Long id);

    /**
     * 删除所有记录(TRUNCATE ddl权限)
     *
     * @param clazz the clazz
     */
    public void deleteAll(Class<?> clazz);
    
    /**
     * 取得jdbc
     * @return
     */
    JdbcOperations getJdbcTemplate();
    
    /**
     * 取得行映射器
     * @param clazz
     * @return
     */
    <T> RowMapper<T> getRowMapper(Class<T> clazz);
}
