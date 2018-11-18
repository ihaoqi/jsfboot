package com.easyeip.jsfboot.user.registry;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.secutiry.PasswordSaltUtils;
import com.easyeip.jsfboot.core.secutiry.ppm.RoleDetails;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.user.bean.type.MenuPositionDetails;
import com.easyeip.jsfboot.user.registry.impl.QueryResultNonPaging;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountEntity;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountGroup;
import com.easyeip.jsfboot.user.registry.impl.RegistryAccountRole;
import com.easyeip.jsfboot.user.type.AccountEntity;
import com.easyeip.jsfboot.user.type.AccountGroup;
import com.easyeip.jsfboot.user.type.AccountManager;
import com.easyeip.jsfboot.user.type.OrderByType;
import com.easyeip.jsfboot.user.type.QueryCondition;
import com.easyeip.jsfboot.user.type.QueryResult;
import com.easyeip.jsfboot.utils.StringKit;

/**
 * 基于注册表的用户管理程序
 * @author ihaoqi
 *
 */
public class RegistryUserManager extends GenericService implements AccountManager {

	private static final String REGISTRY_GROUP_PATH = "/jsfboot/module/jsfboot-users/groups";
	private static final String REGISTRY_ROLE_PATH = "/jsfboot/module/jsfboot-users/roles";
	private static final String REGISTRY_USER_PATH = "/jsfboot/module/jsfboot-users/users";
	
	private static final String LINKMENU_PREFIX = "lm_";
	
	// 菜单重新配置通知
	public static final String NOTIFY_CONFMENUS = "notify_confmenus";

	@UseJsfbootService(RegistryService.class)
	private RegistryService registry;
	
	@UseJsfbootService(SurfaceService.class)
	private SurfaceService surface;
	
	private ServiceContext context;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		this.context = context;
	}
	
	/**
	 * 获取所有可配置的菜单列表
	 * @return
	 */
	public Map<String, MenuPositionDetails> getConfMenus(){
		Map<String, MenuPositionDetails> result = new HashMap<String, MenuPositionDetails>();
		RegistryItem regItem = registry.getItem(RegistryPath.valueOfne(REGISTRY_ROLE_PATH));
		if (regItem == null){
			return result;
		}
		
		for (String name : regItem.valueNames()){
			if (StringKit.isEmpty(name) || !name.startsWith(LINKMENU_PREFIX))
				continue;
			MenuPositionDetails detial = MenuPositionDetails.decode(
					JsfbootContext.getDriver().getSurfaceService(), regItem.getValue(name));
			if (detial != null && detial.isValid()){
				result.put(detial.encode(), detial);
			}
		}
		
		return result;
	}

	/**
	 * 把菜单保存起来
	 * @param list
	 * @throws RegistryException
	 */
	public void saveConfMenus(List<MenuPositionDetails> list) throws RegistryException{
		RegistryItem regItem = registry.createItem(RegistryPath.valueOfne(REGISTRY_ROLE_PATH));
		// 删除原值
		for (String name : regItem.valueNames()){
			if (StringKit.notEmpty(name) && name.startsWith(LINKMENU_PREFIX))
				regItem.removeValue(name);
		}
		
		// 写入新值
		int i = 0;
		for (MenuPositionDetails mpd : list){
			regItem.setValue(LINKMENU_PREFIX + i, mpd.encode());
			i ++;
		}
		
		registry.updateItem(regItem);
		
		// 更新通知
		context.sendAsyncNotify(NOTIFY_CONFMENUS);
		// 通知菜单管理器更新最后修改时间，让那些缓存了菜单的服务全部更新一下
		surface.getMenuManager().updateLastApplyTime();
	}
	
	@Override
	public List<AccountGroup> queryGroupList() {

		List<AccountGroup> result = new ArrayList<AccountGroup>();
		
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_GROUP_PATH);
		for (RegistryItem regItem : registry.allChildren(regPath)){
			result.add(new RegistryAccountGroup (regItem));
		}
		return result;
	}
	
	@Override
	public AccountGroup queryGroupOne(String code) {
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_GROUP_PATH).addChild(code);
		RegistryItem regItem = registry.getItem(regPath);
		if (regItem == null){
			return null;
		}
		
		return new RegistryAccountGroup(regItem);
	}

	@Override
	public AccountGroup addGroup(AccountGroup form, AccountGroup parent) throws Exception {
		
		if (parent != null){
			throw new Exception("不支持添加子级分组。");
		}
		
		if (!RegistryPath.isValidName(form.getCode())){
			throw new Exception(form.getCode() + "不符合名称规范。");
		}
		
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_GROUP_PATH).addChild(form.getCode());
		if (registry.getItem(regPath) != null){
			throw new Exception (form.getCode() + "已存在。");
		}
		
		RegistryItem regItem = registry.createItem(regPath);
		RegistryAccountGroup.save(registry, regItem, form);

		return new RegistryAccountGroup (regItem);
	}

	@Override
	public void removeGroup(String groupCode) throws Exception {
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_GROUP_PATH).addChild(groupCode);
		registry.removeItem(regPath);
	}

	@Override
	public void updateGroup(AccountGroup form) throws Exception {
		
		RegistryAccountGroup group = (RegistryAccountGroup) queryGroupOne (form.getCode());
		if (group == null){
			throw new Exception ("代码为" + form.getCode() + "的分组不存在。");
		}
		
		RegistryAccountGroup.save(registry, group.getRegistryItem(), form);
	}
	
	@Override
	public List<RoleDetails> queryRoleList() {
		List<RoleDetails> result = new ArrayList<RoleDetails>();
		
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_ROLE_PATH);
		for (RegistryItem regItem : registry.allChildren(regPath)){
			result.add(new RegistryAccountRole (registry, regItem));
		}
		return result;
	}
	
	@Override
	public RoleDetails queryRoleOne(String code) {
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_ROLE_PATH).addChild(code);
		RegistryItem regItem = registry.getItem(regPath);
		if (regItem == null){
			return null;
		}
		
		return new RegistryAccountRole(registry, regItem);
	}

	@Override
	public RoleDetails addRole(RoleDetails form) throws Exception {

		if (!RegistryPath.isValidName(form.getCode())){
			throw new Exception(form.getCode() + "不符合名称规范。");
		}
		
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_ROLE_PATH).addChild(form.getCode());
		if (registry.getItem(regPath) != null){
			throw new Exception (form.getCode() + "已存在。");
		}
		
		RegistryItem regItem = registry.createItem(regPath);
		RegistryAccountRole.save(registry, regItem, form);
		
		return new RegistryAccountRole (registry, regItem);
	}

	@Override
	public void removeRole(String roleCode) throws Exception {
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_ROLE_PATH).addChild(roleCode);
		registry.removeItem(regPath);
	}

	@Override
	public void updateRole(RoleDetails form) throws Exception {

		RegistryAccountRole role = (RegistryAccountRole) queryRoleOne (form.getCode());
		if (role == null){
			throw new Exception ("代码为" + form.getCode() + "的角色不存在。");
		}
		
		RegistryAccountRole.save(registry, role.getRegistryItem(), form);
		
		// 通知菜单管理器更新最后修改时间，让那些缓存了菜单的服务全部更新一下
		surface.getMenuManager().updateLastApplyTime();
	}

	@Override
	public AccountEntity addAccount(AccountEntity form) throws Exception {
		
		// 先把名称标准化，以适应为email、手机号等类型的登录名称
		String itemName = RegistryPath.normalizeName(form.getLoginId());
		
		if (!RegistryPath.isValidName(itemName)){
			throw new Exception(form.getLoginId() + "不符合名称规范。");
		}
		
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_USER_PATH).addChild(itemName);
		if (registry.getItem(regPath) != null){
			throw new Exception ("已存在名称为" + form.getLoginId() + "的帐号。");
		}
		
		RegistryItem regItem = registry.createItem(regPath);
		RegistryAccountEntity.saveEntry(registry, regItem, form);
		
		return new RegistryAccountEntity (regItem);
	}

	@Override
	public void removeAccount(String accountId) throws Exception {
		String itemName = RegistryPath.normalizeName(accountId);
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_USER_PATH).addChild(itemName);
		registry.removeItem(regPath);
	}

	@Override
	public void updateAccount(AccountEntity entry) throws Exception {
		String itemName = RegistryPath.normalizeName(entry.getLoginId());
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_USER_PATH).addChild(itemName);
		RegistryAccountEntity.saveEntry(registry, registry.useItem(regPath), entry);
		
		// 通知菜单管理器更新最后修改时间，让那些缓存了菜单的服务全部更新一下
		surface.getMenuManager().updateLastApplyTime();
	}
	
	@Override
	public void modifyPassword(String accountKey, String newPassword) throws Exception {
		RegistryAccountEntity user = (RegistryAccountEntity) queryAccountOne (accountKey);
		if (user == null){
			throw new Exception(accountKey + "账号不存在。");
		}

		// 更新密码，并设置为没过期
		user.setPassword(newPassword);
		user.setPasswordExpired(false);
		user.savePassword(registry);
	}
	
	@Override
	public void modifyPassword(String accountKey, String oldPassword, String newPassword) throws Exception {
		RegistryAccountEntity user = (RegistryAccountEntity) queryAccountOne (accountKey);
		if (user == null){
			throw new Exception(accountKey + "账号不存在。");
		}
		
		String oldSalt = PasswordSaltUtils.encode(oldPassword, user.getLoginId());
		String newSalt = PasswordSaltUtils.encode(newPassword, user.getLoginId());
		
		if (!StringKit.equals(user.getPassword(), oldSalt)){
			throw new Exception("旧的密码错误。");
		}

		// 更新密码，并设置为没过期
		user.setPassword(newSalt);
		user.setPasswordExpired(false);
		RegistryAccountEntity.saveEntry(registry, user.getRegistryItem(), user);
	}

	@Override
	public AccountEntity queryAccountOne(String accountId) {
		String itemName = RegistryPath.normalizeName(accountId);
		if (StringKit.isEmpty(itemName))
			return null;
		RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_USER_PATH).addChild(itemName);
		RegistryItem regItem = registry.getItem(regPath);
		if (regItem == null){
			return null;
		}
		
		return new RegistryAccountEntity (regItem);
	}
	
	private String idToStr(Object id){
		if (id == null)
			return null;
		
		if (id instanceof String)
			return (String) id;
		
		return id.toString();
	}
	
	@Override
	public QueryResult queryAccountList(int pageRow, QueryCondition filter) {
		// 创建默认的查询条件
		if (filter == null){
			filter = QueryCondition.all().order(RegistryUserDetails.FIELD_CREATETIME, OrderByType.ASC);
		}
		
		// 查询出所有记录，并按过滤条件过滤
		List<AccountEntity> result = new ArrayList<AccountEntity>();
		for (RegistryItem item : registry.allChildren(RegistryPath.valueOfne(REGISTRY_USER_PATH))){
			AccountEntity entry = new RegistryAccountEntity (item);
			if (checkCondition (entry, filter)){
				result.add(entry);
			}
		}
		
		// 按第一个排序条件进行排序
		if (filter.getOrderFields().size() > 0){
			final QueryCondition.OrderBy orderBy = filter.getOrderFields().get(0);
			Collections.sort(result, new Comparator<AccountEntity>(){
				@Override
				public int compare(AccountEntity o1, AccountEntity o2) {
					String v1 = idToStr(o1.getMoreFields().get(orderBy.getField()));
					String v2 = idToStr(o2.getMoreFields().get(orderBy.getField()));
					return compareTo(v1, v2);
				}});
		}

		return new QueryResultNonPaging (pageRow, result);
	}
	
	private int compareTo(String s1, String s2){
		if (s1 == s2) return 0;
		if (s1 == null) s1 = "";
		if (s2 == null) s2 = "";
		return s1.compareTo(s2);
	}
	
	/**
	 * 检测账号是否符合查询条件
	 * @param entry
	 * @param filter
	 * @return
	 */
	private boolean checkCondition(AccountEntity entry, QueryCondition condition){
		for (QueryCondition.Where filter : condition.getFilterFields()){
			String v1 = entry.getMoreFields().get(filter.getField());
			if (StringKit.equals(v1, filter.getValue())){
				return true;
			}
		}
		return true;
	}
}
