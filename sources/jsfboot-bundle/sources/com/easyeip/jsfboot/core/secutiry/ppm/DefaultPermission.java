package com.easyeip.jsfboot.core.secutiry.ppm;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.utils.StringKit;

public class DefaultPermission implements MenuPermission {
	
	PageDomainType pageDomain;
	String menuPosition;
	String menuName;
	boolean hasGrantAllAction;
	List<String> grantActions;
	
	public DefaultPermission(){
		grantActions = new ArrayList<String> ();
	}

	@Override
	public String getKey() {
		if (menuName == null || menuPosition == null || pageDomain == null)
			return null;
		
		return getPageDomain().name() + "_" + getMenuPosition() + "_" + getMenuName();
	}
	
	@Override
	public PageDomainType getPageDomain() {
		return pageDomain;
	}
	
	public void setPageDomain(PageDomainType pageDomain){
		this.pageDomain = pageDomain;
	}

	@Override
	public String getMenuPosition() {
		return menuPosition;
	}
	
	public void setMenuPosition(String menuPosition){
		this.menuPosition = menuPosition;
	}

	@Override
	public String getMenuName() {
		return menuName;
	}
	
	public void setMenuName (String menuName){
		this.menuName = menuName;
	}

	@Override
	public boolean isGrantAllAction() {
		return hasGrantAllAction;
	}
	
	public void setGrantAllAction(boolean grantAllAction){
		this.hasGrantAllAction = grantAllAction;
	}

	@Override
	public List<String> getGrantActions() {
		return grantActions;
	}

	/**
	 * 用逗号连接起来的动作名称
	 * @param actions
	 */
	public void setGrantActions(String actions){
		grantActions.clear();
		grantActions.addAll(StringKit.stringSplit(actions, ","));
	}
	
	/**
	 * 编码成字符串 {pageDomain}_{menuPosition}_{pageDomain}@set{_action1|action2|}
	 * @return
	 */
	public String encode(){
		String codes = getKey();
		if (hasGrantAllAction){
			codes += "@all";
		}else{
			codes += "@set";
			if (grantActions.size() > 0){
				codes += "_";
				for (String act : grantActions){
					codes += act;
					codes += "|";
				}
			}
		}
		
		return codes;
	}
	
	/**
	 * 解析编码
	 * @param permEncode
	 * @return
	 */
	static public DefaultPermission decode(String permEncode) throws Exception{
		// 先分解key和动作
		List<String> keyActions = StringKit.stringSplit(permEncode, "@");
		if (keyActions.size() != 2){
			throw new Exception("无效的权限编码格式。");
		}
		
		// 分解key参数
		List<String> keyParams = StringKit.stringSplit(keyActions.get(0), "_");
		if (keyParams.size() != 3){
			throw new Exception("无效的权限编码格式。");
		}
		
		DefaultPermission permObj = new DefaultPermission();
		permObj.setPageDomain(PageDomainType.valueOf(keyParams.get(0)));
		permObj.setMenuPosition(keyParams.get(1));
		permObj.setMenuName(keyParams.get(2));
		
		// 分解action参数
		List<String> actionParams = StringKit.stringSplit(keyActions.get(1), "_");
		String actTypes = actionParams.get(0);
		if (actTypes.equals("all")){
			permObj.setGrantAllAction(true);
		}else{
			permObj.setGrantAllAction(false);
		}
		
		if (actTypes.equals("set") && actionParams.size() > 1){
			String actions = actionParams.get(1);
			permObj.getGrantActions().clear();
			permObj.getGrantActions().addAll(StringKit.stringSplit(actions, "\\|"));
		}
		
		return permObj;
	}
}
