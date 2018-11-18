package com.easyeip.jsfboot.user.bean.type;

import com.easyeip.jsfboot.core.module.type.JsfbootTheme;
import com.easyeip.jsfboot.core.module.type.MenuPosition;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.core.surface.SiteMenuBar;
import com.easyeip.jsfboot.core.surface.SiteMenuManager;
import com.easyeip.jsfboot.core.surface.SurfaceService;
import com.easyeip.jsfboot.utils.StringKit;

/**
 * 主题菜单位置与关联菜单
 * @author liao
 *
 */
public class MenuPositionDetails {
	
	private PageDomainType domain;
	private JsfbootTheme theme;
	private MenuPosition position;
	private SiteMenuBar menu;
	private boolean selected = false;
	
	public MenuPositionDetails(SurfaceService ss, PageDomainType domain, String position){
		this.domain = domain;
		
		this.theme = ss.getCurrentTheme(domain);
		if (position != null){
			if (domain == PageDomainType.Admin && StringKit.equals(SiteMenuManager.MAIN_MENUBAR, position))
				this.menu = ss.getMenuManager().getAdminMenubar();
			else
				this.menu = ss.getMenuManager().getThemeMenubar(domain, position);
		}
		
		// 查找位置对象
		if (theme != null){
			for (MenuPosition mp : theme.getMenuPosition()){
				if (StringKit.equals(mp.getName(), position)){
					this.position = mp;
					break;
				}
			}
		}
	}
	
	public MenuPositionDetails(PageDomainType domain, JsfbootTheme theme,
							MenuPosition position, SiteMenuBar menubar) {
		this.domain = domain;
		this.theme = theme;
		this.position = position;
		this.menu = menubar;
	}
	
	public boolean isValid(){
		return theme != null && position != null && menu != null;
	}

	public PageDomainType getDomain(){
		return domain;
	}
	
	public JsfbootTheme getTheme(){
		return theme;
	}
	
	public MenuPosition getPosition(){
		return position;
	}

	public SiteMenuBar getMenuBar(){
		return menu;
	}
	
	public String toString(){
		return encode();
	}
	
	/**
	 * 编码对象成字符串
	 * @return
	 */
	public String encode(){
		return domain.name() + ":" + position.getName();
	}
	
	/**
	 * 解码字符串
	 * @param ss
	 * @param enstr
	 * @return
	 */
	public static MenuPositionDetails decode(SurfaceService ss, String enstr){
		String[] list = enstr.split(":");
		if (list.length != 2)
			return null;
		
		// 取得主题类型、菜单位置
		PageDomainType pdt = PageDomainType.valueOf(list[0]);
		String menuPos = list[1];

		return new MenuPositionDetails (ss, pdt, menuPos);
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}
