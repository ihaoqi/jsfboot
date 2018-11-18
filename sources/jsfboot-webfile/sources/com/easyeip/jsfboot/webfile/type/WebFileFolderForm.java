package com.easyeip.jsfboot.webfile.type;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;

public class WebFileFolderForm {
	
	private WebfileResourceService service;
	private WebFileFolder folder;
	private List<WebFileResourceForm> fileList;
	
	private String newName;		// 文件夹新名称
	
	public WebFileFolderForm(WebfileResourceService service, WebFileFolder folder){
		this.service = service;
		this.folder = folder;
	}

	public WebFileFolder getRaw() {
		return folder;
	}
	
	/**
	 * 取得文件数量
	 * @return
	 */
	public int getFileCount(){
		return getFiles().size();
	}
	
	public void reset(){
		fileList = null;
	}

	/**
	 * 获取文件列表
	 * @return
	 */
	public List<WebFileResourceForm> getFiles(){
		
		if (fileList == null){
			fileList = new ArrayList<WebFileResourceForm>();
			
			for (WebFileEntry entry : folder.listFiles()){
				
				if (!entry.isDirectory()){
					fileList.add(new WebFileResourceForm(service, entry.asResource()));
				}
				
			}
		}
		
		return fileList;
	}

	public String getNewName() {
		return newName;
	}

	public void setNewName(String newName) {
		this.newName = newName;
	}
}
