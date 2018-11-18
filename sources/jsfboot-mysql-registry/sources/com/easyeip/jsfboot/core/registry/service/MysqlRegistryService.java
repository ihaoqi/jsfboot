package com.easyeip.jsfboot.core.registry.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.jdbc.support.rowset.SqlRowSetMetaData;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryProfile;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.registry.service.sql.ItemCacheManager;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryItemDao;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryItemDaoImpl;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryItemEntry;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryValueDao;
import com.easyeip.jsfboot.core.registry.service.sql.RegistryValueDaoImpl;
import com.easyeip.jsfboot.core.registry.service.sql.ValueCacheManager;
import com.easyeip.jsfboot.core.registry.service.sql.ValueUpdateData;
import com.easyeip.jsfboot.core.registry.service.sql.ValueUpdateType;
import com.easyeip.jsfboot.core.registry.service.util.TranstaionJdbcTemplate;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceUtils;

/**
 * 基于mysql的注册表服务实现
 * @author liao
 *
 */
public class MysqlRegistryService extends RegistryServiceAdmin implements RegistryService {

	static Logger logger = Logger.getLogger(MysqlRegistryService.class.getName());

	private ServiceContext context;
	private RegistryItemDao itemDao;
	private RegistryValueDao valueDao;
	
	private DefaultRegistryProfile profile;
	
	private ValueCacheManager valueCacheMgr;
	private ItemCacheManager itemCacheMgr;

	private ExecutorService scheduleService;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		this.context = context;
		valueCacheMgr = new ValueCacheManager();
		itemCacheMgr = new ItemCacheManager();
		
		int cpuCount = Runtime.getRuntime().availableProcessors();
		scheduleService = Executors.newFixedThreadPool(Math.max(cpuCount, 2));
	}
	
	@Override
	public void makeReady(ServiceContext callContext) throws RegistryException {
		
		// 获取数据源
		String dataSource = ServiceUtils.getParamTextValue(context, "dataSource");
		if (StringKit.isEmpty(dataSource)){
			throw new RegistryException(callContext.getServiceName() + "服务缺少dataSource参数。");
		}
		
		logger.log(Level.INFO, "连接注册表数据库 " + dataSource + " ...");
		
		// 连接数据库，只能使用jndi查询，不能使用jsfboot的数据源服务
		try{
			Context ic = new InitialContext();
	        DataSource source = (DataSource)ic.lookup(dataSource);
	        
			// 初使化表名称及DAO
			String tablePrefix = ServiceUtils.getParamTextValue(context, "tablePrefix", "jsfboot_");
			TranstaionJdbcTemplate jdbcTemplate = new TranstaionJdbcTemplate(source);
	        itemDao = new RegistryItemDaoImpl (jdbcTemplate, tablePrefix);
	        valueDao = new RegistryValueDaoImpl (jdbcTemplate, tablePrefix);
	        
	        // 创建数据表
	        createRegistryTables(source, tablePrefix);
	        
			// 从数据库中读取字段长度
	        SqlRowSet rowSet = itemDao.getJdbcTemplate().queryForRowSet(
	        		"select item_name,item_path,default_value,comment from " + itemDao.getTableName() + " limit 0");
	        SqlRowSetMetaData metaData = rowSet.getMetaData();  
			profile = new DefaultRegistryProfile();
			profile.setMaxNameLength(metaData.getColumnDisplaySize(1));
			profile.setMaxNodeDepth(metaData.getColumnDisplaySize(2)/profile.getMaxNameLength()*2);
			profile.setMaxValueLength(metaData.getColumnDisplaySize(3));
			profile.setMaxCommentLength(metaData.getColumnDisplaySize(4));
			
			callContext.sendAsyncNotify(NOTIFY_LOADED);

        }catch (Exception e){
        	throw new RegistryException (e.getMessage());
        }
		
        context.getAppMessage().add(Level.INFO, "已连接注册表数据库，数据源：" + dataSource);
	}
	
	/**
	 * 取得cache管理
	 * @return
	 */
	public ValueCacheManager getCacheManager(){
		return valueCacheMgr;
	}
	
	@Override
	public RegistryPath getRootPath() {
		return RegistryPath.valueOfne(RegistryPath.SPARATOR_CHAR);
	}

	@Override
	public RegistryItem createItem(RegistryPath path) throws RegistryException{
		RegistryItemEntry entry = getItemEntryOfPath (path, true);
		if (entry == null)
			return null;

		return new MysqlRegistryItem(valueCacheMgr, valueDao, entry, path);
	}

	@Override
	public void removeItem(RegistryPath path) throws RegistryException {
		RegistryItemEntry entry = getItemEntryOfPath (path, false);
		if (entry == null){
			return;
		}
		
		itemDao.delete(entry);
		valueCacheMgr.clearAll();
		itemCacheMgr.clearAll();
	}

	@Override
	public RegistryItem getItem(RegistryPath path) {
		// 返回ROOT
		if (path.isRootPath()){
			return new MysqlRegistryItem(valueCacheMgr, valueDao, new RegistryItemEntry(), path);
		}
		
		try {
			// 先取缓存
			RegistryItemEntry entry = itemCacheMgr.getCache(path.getFullPath());
			if (entry == null){
				entry = getItemEntryOfPath (path, false);
				if (entry == null)
					return null;
				itemCacheMgr.setCache(path.getFullPath(), entry);
			}
			return new MysqlRegistryItem(valueCacheMgr, valueDao, entry, path);
		} catch (RegistryException e) {
			return null;
		}
	}
	
	@Override
	public RegistryProfile getProfile() {
		return profile;
	}

	@Override
	public RegistryItem useItem(RegistryPath path) throws RegistryException {
		if (path.isRootPath()){
			return new MysqlRegistryItem(valueCacheMgr, valueDao, new RegistryItemEntry(), path);
		}
		
		RegistryItemEntry entry = getItemEntryOfPath (path, false);
		if (entry == null)
			throw new RegistryException (path + "路径不存在。");
		
		return new MysqlRegistryItem(valueCacheMgr, valueDao, entry, path);
	}


	@Override
	public void updateItem(RegistryItem item) throws RegistryException {
		if (!(item instanceof MysqlRegistryItem)){
			throw new RegistryException("不支持的结点类型。");
		}
		
		MysqlRegistryItem regItem = (MysqlRegistryItem) item;
		boolean bNeedRename = false;
		
		// 判断改名是否符合条件
		if (!StringKit.equals(regItem.getName(), regItem.getEntry().getItemName())){
			bNeedRename = true;
			
			String newName = regItem.getName();
			if (!RegistryPath.isValidName(newName))
				throw new RegistryException(newName + "不符合名称规范。");
			
			if (newName.length() > profile.getMaxNameLength())
				throw new RegistryException("名称长度超过允许值 " + profile.getMaxNameLength());
			
			// 判断重名
			RegistryPath parentPath = item.getPath().makeParent();
			for (RegistryItem sbItem : this.listChildren(parentPath)){
				if (StringKit.equals(sbItem.getName(), newName))
					throw new RegistryException(newName + "名称已存在。");
			}
		}
		
		// 判断结点值更新是否符合条件
		boolean bNeedUpdateItem = false;

		if (!StringKit.equals(regItem.getDefaultValue(), regItem.getEntry().getDefaultValue()) ||
			!StringKit.equals(regItem.getComment(), regItem.getEntry().getComment())){
			
			bNeedUpdateItem = true;
			
			if (StringKit.strlen (regItem.getComment()) > profile.getMaxCommentLength())
				throw new RegistryException("备注长度超过允许值 " + profile.getMaxCommentLength());
			if (StringKit.strlen (regItem.getDefaultValue()) > profile.getMaxValueLength())
				throw new RegistryException("缺省值长度超过允许值 " + profile.getMaxValueLength());
		}
		
		valueCacheMgr.removeCache(regItem.getEntry().getItemId());
		
		TranstaionJdbcTemplate jdbc = (TranstaionJdbcTemplate) itemDao.getJdbcTemplate();
		try{
			// 启动事务
			jdbc.beginTranstaion();
		
			// 更新值
			Map<String, ValueUpdateData> updateValues = regItem.getValueData(false);
			if (updateValues != null){
				List<String> waitDelKey = new ArrayList<String>();
				
				// 判断值条件
				for (Entry<String, ValueUpdateData> updat : updateValues.entrySet()){
					ValueUpdateData data = updat.getValue();
					if (data.getType() == ValueUpdateType.update){
						if (StringKit.strlen (data.getValue()) > profile.getMaxValueLength()){
							throw new RegistryException(data.getEntry().getName() + 
									"值长度超过允许值 " + profile.getMaxValueLength());
						}
					}else if (data.getType() == ValueUpdateType.insert){
						if (StringKit.strlen (data.getValue()) > profile.getMaxValueLength()){
							throw new RegistryException(data.getEntry().getName() + 
									"值长度超过允许值 " + profile.getMaxValueLength());
						}
					}
				}

				// 判断是否要使用多线程
				boolean needUseTask = updateValues.size() > 2;
				List<Future<Exception>> updateTasks = new ArrayList<Future<Exception>>();
				
				// 更新值
				for (Entry<String, ValueUpdateData> updat : updateValues.entrySet()){
					
					// 记录要清除的cache
					if (updat.getValue().getType() == ValueUpdateType.delete){
						waitDelKey.add(updat.getKey());
					}
					
					// 添加到任务中
					if (needUseTask){
						updateTasks.add(schedule(new UpdateValueTask(regItem, updat)));
					}else{
						new UpdateValueTask(regItem, updat).run();
					}
				}
				
				// 等待任务完成
				Exception exception = null;
				for (Future<Exception> future : updateTasks){
					Exception ret = future.get(3, TimeUnit.MINUTES);
					if (ret != null){
						exception = ret;
					}
				}
				
				if (exception != null){
					throw exception;
				}
				
				// 移除已删除的属性缓存
				while (waitDelKey.size() > 0){
					updateValues.remove(waitDelKey.remove(0));
				}
			}
			
			// 更新注册表项
			if (bNeedUpdateItem == true){
				itemCacheMgr.removeCache(regItem.getPath().getFullPath());
				
				regItem.getEntry().setComment(regItem.getComment());
				regItem.getEntry().setDefaultValue(regItem.getDefaultValue());
				itemDao.update(regItem.getEntry());
			}
			
			// 最后改名
			if (bNeedRename == true){
				itemCacheMgr.clearAll();
				
				itemDao.rename(regItem.getEntry(), regItem.getName());
			}
			
			jdbc.commit();
		}catch (Exception e){
			jdbc.rollback();
			
			if (e instanceof RegistryException)
				throw (RegistryException) e;
			else
				throw new RegistryException(e.getMessage());
		}
	}
	
	// 记录更新任务（用于批量处理）
	class UpdateValueTask implements Runnable{
		
		Entry<String, ValueUpdateData> updat;
		MysqlRegistryItem regItem;
		
		public UpdateValueTask(MysqlRegistryItem regItem, Entry<String, ValueUpdateData> updat){
			this.regItem = regItem;
			this.updat = updat;
		}

		@Override
		public void run() {
			ValueUpdateData  data = updat.getValue();
			if (data.getType() == ValueUpdateType.delete){
				valueDao.delete(data.getEntry());
			}else if (data.getType() == ValueUpdateType.update){
				data.getEntry().setValue(data.getValue());
				valueDao.save(data.getEntry());
			}else if (data.getType() == ValueUpdateType.insert){
				valueDao.createOne(regItem.getEntry(), updat.getKey(), data.getValue());
			}
		}
		
	}
	
	@Override
	public List<RegistryItem> listChildren(RegistryPath path) {
		
		List<RegistryItem> list = new ArrayList<RegistryItem>();
		
		if (path == null)
			return list;
		
		List<RegistryItemEntry> queryResult;
		if (path.isRootPath()){
			String sql = "`item_level`=0 AND `item_path`='/' ORDER BY sort_num;";
			queryResult = itemDao.queryItems(sql, null);
		}else{
			String sql = "`item_level`=? AND `item_path`=? ORDER BY sort_num;";
			Object args[] = {path.getItemCount(), path.getFullPath()};
			queryResult = itemDao.queryItems(sql, args);
		}
		
		for (RegistryItemEntry child : queryResult){
			RegistryPath childPath = RegistryPath.valueOf(path).addChild(child.getItemName());
			list.add(new MysqlRegistryItem (valueCacheMgr, valueDao, child, childPath));
		}
		
		return list;
	}

	@Override
	public Iterable<RegistryItem> allChildren(final RegistryPath path) {
		
		return listChildren(path);
	}

	/**
	 * 取得指定路径的注册表对象
	 * @param path
	 * @param create
	 * @return
	 */
	private RegistryItemEntry getItemEntryOfPath (RegistryPath path, boolean create) throws RegistryException{
		
		// 先直接查询路径
		RegistryItemEntry itemEntry = queryItemEntryOfPath(path);
		if (itemEntry != null || create == false)
			return itemEntry;
		
		if (path.isRootPath())
			return null;

		// 开始创建路径
		RegistryItemEntry parentEntry = null;
		RegistryPath queryPath = RegistryPath.valueOf(this.getRootPath());
		for (int i = 0; i < path.getItemCount(); i ++){
			
			// 生成查询路径
			String itemName = path.getItemName(i);
			queryPath.addChild(itemName);
			
			// 生成查询sql
			String sql = "`item_level`=? AND `item_path`=? AND `item_name`=?";
			Object args[] = {i, queryPath.makeParent().getFullPath(), queryPath.getLastName()};
			List<RegistryItemEntry> queryResult = itemDao.queryItems(sql, args);
			
			// 创建这个结点
			if (queryResult.isEmpty()){
				
				if (path.getItemCount() > profile.getMaxNodeDepth())
					throw new RegistryException("路径深度超过允许值 " + profile.getMaxNodeDepth());
				
				String createName = queryPath.getLastName();
				if (createName.length() > profile.getMaxNameLength())
					throw new RegistryException("名称长度超过允许值 " + profile.getMaxNameLength());
				
				parentEntry = itemDao.createOne(parentEntry, createName);

			}else{
				parentEntry = queryResult.get(0);
			}
		}
		
		return parentEntry;
	}
	
	/**
	 * 查询指定路径
	 * @param path
	 * @return
	 */
	private RegistryItemEntry queryItemEntryOfPath(RegistryPath path){

		if (path.isRootPath())
			return null;

		String level = Long.valueOf(path.getItemCount() - 1).toString();
		String sql = "`item_level`=? AND `item_path`=? AND `item_name`=?";
		
		Object args[] = {level, path.makeParent().getFullPath(), path.getLastName()};
		
		try{
			List<RegistryItemEntry> result = itemDao.queryItems(sql, args);
			
			if (result.isEmpty())
				return null;
				
			return result.get(0);
		}catch (Exception e){
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 如果数据表不存在就创建
	 * @param dataSource
	 */
	private void createRegistryTables(DataSource dataSource, String tablePrefix){
		
		// 检测表是否存在
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		String sql = "SHOW TABLES LIKE '" + itemDao.getTableName() + "';";
		if (!jdbcTemplate.queryForList(sql).isEmpty())
			return;
		
		// 创建表
		sql = "CREATE TABLE `" + itemDao.getTableName() + "` (" +
				 "`item_id` int(10) unsigned NOT NULL AUTO_INCREMENT COMMENT '项ID'," +
				 "`item_level` int(11) NOT NULL COMMENT '第几层'," +
				 "`sort_num` int(10) unsigned NOT NULL COMMENT '排序号'," +
				 "`id_chain` varchar(256) DEFAULT NULL COMMENT 'ID串'," +
				 "`item_path` varchar(1024) DEFAULT NULL COMMENT '名称路径'," +
				 "`item_name` varchar(128) DEFAULT NULL COMMENT '项名称'," +
				 "`default_value` varchar(1024) DEFAULT NULL COMMENT '缺省值'," +
				 "`comment` varchar(512) DEFAULT NULL COMMENT '备注'," +
				 "PRIMARY KEY (`item_id`)" +
				") ENGINE=InnoDB AUTO_INCREMENT=697 DEFAULT CHARSET=utf8 COMMENT='注册表项'";
				
		jdbcTemplate.execute(sql);
		
		sql = "CREATE TABLE `" + valueDao.getTableName() + "` (" +
				 "`item_id` int(10) unsigned NOT NULL COMMENT '项ID'," +
				 "`value_name` varchar(128) NOT NULL COMMENT '值名称'," +
				 "`value_content` varchar(1024) DEFAULT NULL COMMENT '值内容'," +
				 "KEY `item_index` (`item_id`)," +
				 "CONSTRAINT `" + tablePrefix + "_item_value` FOREIGN KEY (`item_id`) REFERENCES `" +
				 	itemDao.getTableName() + "` (`item_id`) ON DELETE CASCADE" +
				") ENGINE=InnoDB DEFAULT CHARSET=utf8 COMMENT='注册表值'";
		jdbcTemplate.execute(sql);
	}

	public Future<Exception> schedule(final Runnable task) {
		return scheduleService.submit(new Callable<Exception>(){
			@Override
			public Exception call() throws Exception {
				try{
					task.run();
					return null;
				}catch (Exception e){
					return e;
				}
			}
			
		});
	}
}
