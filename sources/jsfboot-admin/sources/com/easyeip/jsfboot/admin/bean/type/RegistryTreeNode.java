package com.easyeip.jsfboot.admin.bean.type;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.primefaces.model.TreeNodeChildren;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;

public class RegistryTreeNode extends DefaultTreeNode {
	private static final long serialVersionUID = 1L;
	
	private RegistryService registry;
	private RegistryItem item;
		
	public RegistryTreeNode (RegistryService registry, RegistryItem item){
		
		this.registry = registry;
		this.item = item;
		
		if (item != null){

			// 设置RegistryItem对象
			this.setData(item);
			
			// 添加child
			expandChildren(item.getPath().isRootPath());
		}
	}
	
	public void selectThisNode (){
		
		// 去除其他选中状态
		TreeNode parent = this;
		while (parent.getParent() != null){
			parent = parent.getParent();
		}
		
		clearSelected (parent);
		
		this.setSelected(true);
		
	}

	
	private void clearSelected(TreeNode root){
		
		root.setSelected(false);
		
		for (TreeNode child : root.getChildren()){
			clearSelected (child);
		}
		
	}
	
	/**
	 * 根据item来移除结点
	 * @param item
	 * @return
	 */
	public boolean removeChildOfItem (RegistryPath path){
		RegistryTreeNode node = findNodeOfItem(path);
		if (node == null)
			return false;
		
		return node.getParent().getChildren().remove(node);
	}
	
	/**
	 * 添加注册表结点
	 * @param parentPath
	 * @param item
	 */
	public RegistryTreeNode addChildOfItem(RegistryPath parentPath, RegistryItem item) {
		RegistryTreeNode node = findNodeOfItem(parentPath);
		if (node == null)
			return null;
		
		RegistryTreeNode child = new RegistryTreeNode(registry, item);
		
		node.getChildren().add(child);

		return child;
	}
	
	public RegistryTreeNode findNodeOfItem (RegistryPath path){
		
		// 按路径查询
		RegistryTreeNode curNode = this;
		for (int i = 0; i < path.getItemCount(); i ++){
			String nodeName = path.getItemName(i);
			// 在 curNode 下查找名为nodeName的结点
			boolean findOk = false;
			for (TreeNode treeNode : curNode.getChildren()){
				RegistryItem nodeItem = (RegistryItem) treeNode.getData();
				if (nodeItem == null)
					continue;
				if (StringKit.equals(nodeItem.getName(), nodeName)){
					findOk = true;
					curNode = (RegistryTreeNode) treeNode;
					break;
				}
			}
			
			if (findOk == false)
				return null;
		}
		
		return curNode;
	}

	/**
	 * 初使化子结点
	 * @param nodeExpand
	 */
	public void expandChildren (boolean nodeExpand){
		
		// 清除原来的
		TreeNodeChildren children = (TreeNodeChildren) this.getChildren();
		children.clear();
		
		Iterable<RegistryItem> allChildren = registry.allChildren(item.getPath());
		if (nodeExpand == false){
			
			// 如果有子结点，添加一个空的（用于在结点上显示扩展框
			if (allChildren.iterator().hasNext()){
				RegistryTreeNode node = new RegistryTreeNode(registry, null);
				node.setType("none");
				children.add(node);
			}
			
		}else{
			
			// 添加新子结点
			for (RegistryItem item : allChildren){
				children.add(new RegistryTreeNode(registry, item));
			}
			
		}
	}
}
