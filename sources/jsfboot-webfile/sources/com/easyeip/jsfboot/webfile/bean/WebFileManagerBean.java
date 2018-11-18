package com.easyeip.jsfboot.webfile.bean;

import java.util.List;

import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.webfile.type.AddFolderForm;
import com.easyeip.jsfboot.webfile.type.CustomResUrlForm;
import com.easyeip.jsfboot.webfile.type.WebFileFolderForm;
import com.easyeip.jsfboot.webfile.type.WebFileResourceForm;

public interface WebFileManagerBean {

	/**
	 * 取得文件根目录
	 * @return
	 */
	TreeNode getFolderTree();
	
	/**
	 * 结点选中事件
	 * @param event
	 */
	void onNodeSelect(NodeSelectEvent event);
	
	/**
	 * 取得选中目录
	 * @return
	 */
	WebFileFolderForm getSelectFolder();

	/**
	 * 取处详情面板名和乐
	 * @return
	 */
	String getDetialPanelName();
	
	/**
	 * 显示当前选中的文件夹文件
	 * @param event
	 */
	void showFolderFiles(ActionEvent event);
	
	/**
	 * 取得添加文件夹表单
	 * @return
	 */
	AddFolderForm getAddFolderForm();

	void beginAddFolder(ActionEvent event);
	void endAddFolder(ActionEvent event);
	
	void beginDeleteFolder(ActionEvent event);
	void endDeleteFolder(ActionEvent event);
	
	void beginEditFolder(ActionEvent event);
	void endEditFolder(ActionEvent event);
	
	/**
	 * 取得允许上传的文件类型，正则表达式，如 /(\.|\/)(gif|jpe?g|png)$/
	 * @return
	 */
	String getFileUploadTypes();
	
	/**
	 * 取得文件上传大小限制
	 * @return
	 */
	int getFileUploadLimit();
	
	/**
	 * 文件上传事件
	 * @param event
	 */
	void handleFileUpload(FileUploadEvent event);
	
	
	/**
	 * 取得当前选中的文件
	 * @return
	 */
	WebFileResourceForm getSelectFile();
	
	/**
	 * 选择文件
	 * @param event
	 */
	void beginMoveFile (ActionEvent event);
	
	/**
	 * 取得移动到的结点
	 * @return
	 */
	TreeNode getSelectedMoveNode();
 
	/**
	 * 设置移动到的结点
	 * @param selectedNode
	 */
    void setSelectedMoveNode(TreeNode selectedNode);
    
    /**
     * 移动文件
     * @param event
     */
    void doMoveFileTo (ActionEvent event);
    
    /**
     * 删除当前文件
     * @param event
     */
    void doDeleteFile (ActionEvent event);
    
    /**
     * 开始编辑文件
     * @param event
     */
    void beginEditFile (ActionEvent event);
    
    /**
     * 执行编辑
     * @param event
     */
    void doEditFile (ActionEvent event);
    
    /**
     * 开始显示自定义url
     */
    void beginProcCustomUrls();
    
    /**
     * 取得定义url列表
     * @return
     */
    List<CustomResUrlForm> getCustomResourceUrls();
    
    /**
     * 移除指定url
     */
    void removeCustomUrl(ActionEvent event);
}
