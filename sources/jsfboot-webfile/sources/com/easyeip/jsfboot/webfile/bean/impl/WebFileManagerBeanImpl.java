package com.easyeip.jsfboot.webfile.bean.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ActionEvent;

import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.model.TreeNode;

import com.easyeip.jsfboot.core.beans.JsfbootBean;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.utils.FacesUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.CustomResourceUrl;
import com.easyeip.jsfboot.webfile.ResourceUrlManager;
import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.bean.WebFileManagerBean;
import com.easyeip.jsfboot.webfile.type.WebFileFolderForm;
import com.easyeip.jsfboot.webfile.type.WebFileResourceForm;
import com.easyeip.jsfboot.webfile.type.AddFolderForm;
import com.easyeip.jsfboot.webfile.type.CustomResUrlForm;
import com.easyeip.jsfboot.webfile.type.FolderTreeNode;
import com.easyeip.jsfboot.webfile.type.WebFileDetailPanel;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public class WebFileManagerBeanImpl extends JsfbootBean implements WebFileManagerBean {

	@UseJsfbootService(WebfileResourceService.class)
	WebfileResourceService webfileService;
	
	private FolderTreeNode treeRoot;

	private WebFileFolderForm selectFolder;
	private WebFileDetailPanel curDetialPanel;
	
	private AddFolderForm addFolderForm;
	
	private WebFileResourceForm selectFile;
	private TreeNode selectMoveNode;
	
	private List<CustomResUrlForm> customResUrls;
	
	@PostConstruct
	void initFileFolder(){
		treeRoot = new FolderTreeNode(webfileService.getFileSystem().getRootFolder());
		loadAllFolder (treeRoot);
		curDetialPanel = WebFileDetailPanel.viewFiles;
	}

	@Override
	public TreeNode getFolderTree() {
		return treeRoot;
	}

	@Override
	public void onNodeSelect(NodeSelectEvent event) {
		FolderTreeNode treeNode = (FolderTreeNode) event.getTreeNode();
		treeNode.selectThisNode();
		selectFolder = new WebFileFolderForm(webfileService, treeNode.getFolder());
		showFolderFiles(null);
	}

	@Override
	public WebFileFolderForm getSelectFolder() {
		return selectFolder;
	}

	/**
	 * 加载目录列表
	 * @param tree
	 */
	private void loadAllFolder (FolderTreeNode tree){
		WebFileFolder folder = tree.getFolder();
		for (WebFileEntry entry : folder.listFiles()){
			if (!entry.isDirectory()){
				continue;
			}
			
			// 加载目录
			FolderTreeNode child = new FolderTreeNode(entry.asDirectory());
			tree.getChildren().add(child);
			loadAllFolder (child);
		}
		
		// 展开第一个根目录
		if (tree.getChildCount() == 1 && tree.getParent() == null){
			tree.getChildren().get(0).setExpanded(true);
		}
	}

	@Override
	public String getDetialPanelName() {
		return curDetialPanel.name();
	}
	
	@Override
	public void showFolderFiles(ActionEvent event) {
		curDetialPanel = WebFileDetailPanel.viewFiles;
	}

	@Override
	public AddFolderForm getAddFolderForm() {
		if (addFolderForm == null){
			addFolderForm = new AddFolderForm();
		}
		return addFolderForm;
	}
	
	@Override
	public void beginAddFolder(ActionEvent event) {
		curDetialPanel = WebFileDetailPanel.addFolder;
		addFolderForm = null;
	}

	@Override
	public void endAddFolder(ActionEvent event) {
		// 检测名称是否存在
		String name = addFolderForm.getName();
		WebFileFolder folder = (selectFolder == null ? treeRoot.getFolder() : selectFolder.getRaw());
	
		try {
			// 添加文件夹
			FolderTreeNode node = treeRoot.createFolderPath(folder.createFolder(name));
			
			// 选中文件夹
			if (node.getParent() != null && node.getParent().isExpanded() == false){
				node.getParent().setExpanded(true);
			}
			if (selectFolder == null){
				node.selectThisNode ();
				selectFolder = new WebFileFolderForm(webfileService, node.getFolder());
			}else{
				FolderTreeNode treeNode = treeRoot.getTreeNodeByFolder(selectFolder.getRaw(), false);
				if (treeNode != null) treeNode.selectThisNode();
			}
			showFolderFiles(event);
		} catch (Exception e) {
			FacesUtils.addMessageError("添加文件夹", e.getMessage());
			return;
		}
	}

	@Override
	public void beginDeleteFolder(ActionEvent event) {
		curDetialPanel = WebFileDetailPanel.delFolder;
	}

	@Override
	public void endDeleteFolder(ActionEvent event) {
		try {
			WebFileFolder selectParent = selectFolder.getRaw().getParent();
			selectParent.deleteFolder(selectFolder.getRaw().getFileName());
			FolderTreeNode parentNode = treeRoot.removeFolder(selectFolder.getRaw());
			// 选中父结点
			if (parentNode != null && !parentNode.getFolder().isRoot()){
				selectFolder = new WebFileFolderForm (webfileService, selectParent);
				parentNode.selectThisNode();
			}else{
				selectFolder = null;
			}
			showFolderFiles(event);
		} catch (IOException e) {
			FacesUtils.addMessageError("删除文件夹", e.getMessage());
			return;
		}
	}

	@Override
	public void beginEditFolder(ActionEvent event) {
		curDetialPanel = WebFileDetailPanel.editFolder;
		selectFolder.setNewName(selectFolder.getRaw().getFileName());
	}

	@Override
	public void endEditFolder(ActionEvent event) {
		try {
			selectFolder.getRaw().rename(selectFolder.getNewName());
			showFolderFiles(event);
			FolderTreeNode treeNode = treeRoot.getTreeNodeByFolder(selectFolder.getRaw(), false);
			if (treeNode != null) treeNode.selectThisNode();
		} catch (IOException e) {
			FacesUtils.addMessageError("删除文件夹", e.getMessage());
			return;
		}
	}
	
	@Override
	public String getFileUploadTypes() {
		return webfileService.getProfile().getFileUploadTypes();
	}

	@Override
	public int getFileUploadLimit() {
		// kb
		return webfileService.getProfile().getFileUploadLimit();
	}
	
	@Override
	public void handleFileUpload(FileUploadEvent event) {
		// 保存文件
		try {
			// 取得文件名
			String fileName = event.getFile().getFileName();
			WebFileResource resFile = selectFolder.getRaw().createResource(fileName);
			resFile.save(event.getFile().getContents());
			// 重置
			selectFolder = new WebFileFolderForm(webfileService, selectFolder.getRaw());
			FacesUtils.addMessageInfo("上传文件", fileName + "上传成功。");
		} catch (IOException e) {
			FacesUtils.addMessageError("上传文件", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public WebFileResourceForm getSelectFile() {
		return selectFile;
	}

	@Override
	public void beginMoveFile(ActionEvent event) {
		selectFile = (WebFileResourceForm) event.getComponent().getAttributes().get("file");
		// 先清除所有选中状态
		selectMoveNode = null;
		treeRoot.clearAllSelectedState();
	}

	@Override
	public TreeNode getSelectedMoveNode() {
		return selectMoveNode;
	}

	@Override
	public void setSelectedMoveNode(TreeNode selectedNode) {
		selectMoveNode = selectedNode;
		treeRoot.clearAllSelectedState();
	}

	@Override
	public void doMoveFileTo(ActionEvent event) {
		if (selectMoveNode == null || selectFile == null){
			FacesUtils.addMessageWarn("移动文件", "请选择要移动到的文件夹。");
			return;
		}
		
		WebFileFolder toFolder = (WebFileFolder) selectMoveNode.getData();
		if (StringKit.equals(toFolder.getFullPath(), selectFolder.getRaw().getFullPath())){
			FacesUtils.setAllowCloseDialog(true);
			return;
		}
		
		// 移返回文件到
		try {
			// 创建新文件
			String fileName = selectFile.getRaw().getFileName();
			WebFileResource descFile = toFolder.createResource(fileName);
			
			// 复制文件
			ByteArrayOutputStream fileData = new ByteArrayOutputStream();
			selectFile.getRaw().load(fileData);
			descFile.save(fileData.toByteArray());
			fileData.close();
			
			// 删除原文件
			selectFile.getRaw().getParent().deleteResource(fileName);
			
			selectFolder.reset();
			FacesUtils.setAllowCloseDialog(true);
			
		} catch (IOException e) {
			FacesUtils.addMessageError("移动文件", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void doDeleteFile(ActionEvent event) {
		selectFile = (WebFileResourceForm) event.getComponent().getAttributes().get("file");
		String fileName = selectFile.getRaw().getFileName();
		selectFolder.reset();
		try {
			selectFile.getRaw().getParent().deleteResource(fileName);
			FacesUtils.addMessageInfo("删除文件", fileName + "已删除。");
		} catch (IOException e) {
			FacesUtils.addMessageError("删除文件", e.getMessage());
			e.printStackTrace();
		}
	}

	@Override
	public void beginEditFile(ActionEvent event) {
		// 重新生成文件表单
		selectFile = (WebFileResourceForm) event.getComponent().getAttributes().get("file");
		selectFile = new WebFileResourceForm(webfileService, selectFile.getRaw());
		FacesUtils.clearInputInvalidState();
	}

	@Override
	public void doEditFile(ActionEvent event) {
		// 判断名称
		boolean needRename = false;
		if (!StringKit.equals(selectFile.getNewFileName(), selectFile.getRaw().getFileName())){
			if (!RegistryPath.isValidName(selectFile.getNewFileName())){
				FacesUtils.addMessageWarn("编辑文件", "新文件名不符合名称规范。");
				return;
			}
			needRename = true;
		}
		
		// 判断新路径
		RegistryPath newPath = null;
		if (StringKit.notEmpty(selectFile.getCustomDownPath())){
			newPath = RegistryPath.valueOfne(selectFile.getCustomDownPath());
			if (newPath == null){
				FacesUtils.addMessageWarn("编辑文件", "自定义URL不符合规范。");
				return;
			}
		}
		
		// 执行
		try {
			// 先删除原数据
			ResourceUrlManager urlmgr = webfileService.getCustomUrlManager();
			String oldCustomPath = urlmgr.findResourceCustomUrl(selectFile.getRaw());
			if (StringKit.notEmpty(oldCustomPath)){
				urlmgr.removeResourceUrl(selectFile.getRaw().getFullPath());
			}
			
			// 再改名
			if (needRename){
				selectFile.getRaw().rename(selectFile.getNewFileName());
			}
			
			// 添加新的自定义数径
			if (newPath != null){
				urlmgr.setResourceUrl(selectFile.getRaw(), newPath.getFullPath());
			}
			
			selectFolder.reset();
			FacesUtils.setAllowCloseDialog(true);
		} catch (Exception e) {
			FacesUtils.addMessageError("编辑文件", e.getMessage());
			e.printStackTrace();
			return;
		}
	}
	
    /**
     * 开始显示自定义url
     */
	@Override
	public void beginProcCustomUrls(){
		customResUrls = new ArrayList<CustomResUrlForm>();
		for (CustomResourceUrl url : webfileService.getCustomUrlManager().getResourceList()){
			WebFileEntry existRes = webfileService.findResource(url.getSourcePath(), false);
			customResUrls.add(new CustomResUrlForm (url, existRes == null));
		}
    }

	@Override
	public List<CustomResUrlForm> getCustomResourceUrls() {

		return customResUrls;
	}

	@Override
	public void removeCustomUrl(ActionEvent event) {
		CustomResUrlForm url = (CustomResUrlForm) event.getComponent().getAttributes().get("url");
		if (webfileService.getCustomUrlManager().removeResourceUrl(url.getSourcePath())){
			for (CustomResUrlForm uu : customResUrls){
				if (uu.getKey() == url.getKey()){
					customResUrls.remove(uu);
					break;
				}
			}
		}
	}
}
