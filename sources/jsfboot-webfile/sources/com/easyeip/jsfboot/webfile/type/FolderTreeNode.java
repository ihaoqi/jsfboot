package com.easyeip.jsfboot.webfile.type;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;

public class FolderTreeNode extends DefaultTreeNode {

	private static final long serialVersionUID = 1L;
	
	public FolderTreeNode(WebFileFolder folder){
		this.setData(folder);
	}
	
	/**
	 * 取得关联的目录对象
	 * @return
	 */
	public WebFileFolder getFolder(){
		return (WebFileFolder) getData();
	}

	/**
	 * 把一个文件夹添加到树中
	 * @param node
	 * @return
	 */
	public FolderTreeNode createFolderPath (WebFileFolder folder){
		
		return getTreeNodeByFolder(folder, true);
	}
	
	/**
	 * 移除目录
	 * @param folder
	 * @return 返回移除结点的父结点
	 */
	public FolderTreeNode removeFolder (WebFileFolder folder){
		
		// 找出结点
		FolderTreeNode node = getTreeNodeByFolder(folder, false);
		
		// 如果最后也找到了，表示存在
		if (node != null && node.getParent() != null){
			// 把自己移出去
			FolderTreeNode parentNode = (FolderTreeNode) node.getParent();
			parentNode.getChildren().remove(node);
			if (parentNode.getChildCount() == 0)
				parentNode.setExpanded(false);
			return parentNode;
		}else{
			return null;
		}
	}
	
	/**
	 * 取得树结点
	 * @param folder 要查询的目录
	 * @param craeteNotExist 如果不存在是否创建
	 * @return
	 */
	public FolderTreeNode getTreeNodeByFolder(WebFileFolder folder, boolean craeteNotExist){
		// 把所有父结点找出来
		List<WebFileFolder> folderChain = new ArrayList<WebFileFolder>();
		WebFileFolder test = folder;
		while (test.getParent() != null){
			if (!test.getParent().isRoot()){
				folderChain.add(0, test.getParent());
			}
			test = test.getParent();
		}
		folderChain.add(folder);

		// 找到自己的parent
		FolderTreeNode rootNode = this;
		while (rootNode.getParent() != null){
			rootNode = (FolderTreeNode) rootNode.getParent();
		}

		// 按顺序创建目录
		for (WebFileFolder child : folderChain){
			boolean findEqualName = false;
			for (TreeNode node : rootNode.getChildren()){
				FolderTreeNode treeNode = (FolderTreeNode) node;
				if (StringKit.equals(child.getFileName(), treeNode.getFolder().getFileName())){
					rootNode = treeNode;
					findEqualName = true;
					break;
				}
			}
			
			// 添加
			if (findEqualName == false){
				if (craeteNotExist){
					FolderTreeNode newNode = new FolderTreeNode(child);
					rootNode.getChildren().add(newNode);
					rootNode = newNode;
				}else{
					return null;
				}
			}
		}
		
		return rootNode;
	}

	/**
	 * 选择自己
	 */
	public void selectThisNode (){
		
		clearAllSelectedState();
		
		this.setSelected(true);
		
	}

	/**
	 * 请除所有结点的选中状态
	 */
	public void clearAllSelectedState(){
		// 去除其他选中状态
		TreeNode parent = this;
		while (parent.getParent() != null){
			parent = parent.getParent();
		}
		
		clearSelected (parent);
	}
	
	private void clearSelected(TreeNode root){
		
		root.setSelected(false);
		
		for (TreeNode child : root.getChildren()){
			clearSelected (child);
		}
		
	}
}
