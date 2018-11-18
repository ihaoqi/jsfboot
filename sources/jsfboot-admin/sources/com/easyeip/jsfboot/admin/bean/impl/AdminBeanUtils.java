package com.easyeip.jsfboot.admin.bean.impl;

import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.admin.bean.type.MenuTreeInfo;
import com.easyeip.jsfboot.core.module.JsfbootModule;
import com.easyeip.jsfboot.core.module.type.MenuPage;

public class AdminBeanUtils {
	public static void menuPage2TreeNode (JsfbootModule module, TreeNode root, List<MenuPage> menu){
		
		for (MenuPage mp : menu){
			
			TreeNode node = new DefaultTreeNode(new MenuTreeInfo(module, mp), root);
			node.setType("menu");
			
			if (mp.getSubMenu().size() > 0){
				menuPage2TreeNode (module, node, mp.getSubMenu());
			}
		}
		
	}
}
