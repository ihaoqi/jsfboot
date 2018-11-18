package com.easyeip.jsfboot.webfile.vfs.regfs;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.utils.DateUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.vfs.AbstractWebFileSystem;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public class RegFsWebFileFolder extends AbstractRegFsWebFile implements WebFileFolder {
	
	public RegFsWebFileFolder(RegistryFileSystem rfs, RegistryPath filePath, WebFileFolder parent){
		super(rfs, filePath, parent);
	}
	
	@Override
	public boolean isRoot() {
		return false;
	}

	@Override
	public List<WebFileEntry> listFiles() {
		return listFiles(rfs, this.getRegistryPath(), this);
	}
	
	@Override
	public WebFileFolder createFolder(String fileName) throws Exception {
		if (!RegistryPath.isValidName(fileName)){
			throw new IOException (fileName + "不符合名称规范。");
		}
		RegistryPath filePath = getRegistryPath().makeChild(getPathNameByFileName (fileName, true));
		RegistryItem fileItem = rfs.getRegistry().getItem(filePath);
		if (fileItem != null){
			throw new IOException (fileName + "目录已存在。");
		}
		
		try {
			String initDate = DateUtils.dateToStr(new Date());
			fileItem = rfs.getRegistry().createItem(filePath);
			fileItem.setDefaultValue(fileName);
			fileItem.setValue("entryType", ENTRY_DIRECTORY);
			fileItem.setValue("createTime", initDate);
			fileItem.setValue("modifyTime", initDate);
			rfs.getRegistry().updateItem(fileItem);
			
			rfs.sendNotify(this, AbstractWebFileSystem.CREATE_NOTIFY);
			
		} catch (RegistryException e) {
			throw new IOException (e.getMessage());
		}
		
		return new RegFsWebFileFolder(rfs, filePath, this);
	}
	
	@Override
	public void deleteFolder(String fileName) throws IOException {
		
		// 先找下目录，没找则就直接生成注册表路径
		RegistryPath delPath = null;
		for (WebFileEntry entry : this.listFiles()){
			if (entry.isDirectory() && StringKit.equals(fileName, entry.getFileName())){
				delPath = ((AbstractRegFsWebFile)entry).getRegistryPath();
				break;
			}
		}
		if (delPath == null){
			delPath = getRegistryPath().makeChild(getPathNameByFileName (fileName, true));
		}
		
		try {
			rfs.getRegistry().removeItem(delPath);
			rfs.sendNotify(this, AbstractWebFileSystem.REMOVE_NOTIFY);
		} catch (RegistryException e) {
			throw new IOException (e.getMessage());
		}
	}

	@Override
	public WebFileResource createResource(String fileName) throws IOException {
		fileName = fileName.trim();
		if (fileName.indexOf("\\") >= 0 || fileName.indexOf("/") >= 0){
			throw new IOException (fileName + "文件名不能包含路径。");
		}
		RegistryPath filePath = getRegistryPath().makeChild(getPathNameByFileName (fileName, false));
		RegistryItem fileItem = rfs.getRegistry().getItem(filePath);
		if (fileItem != null){
			throw new IOException (fileName + "文件已存在。");
		}
		
		try {
			String initDate = DateUtils.dateToStr(new Date());
			fileItem = rfs.getRegistry().createItem(filePath);
			fileItem.setDefaultValue(fileName);
			fileItem.setValue("entryType", ENTRY_RESOURCE);
			fileItem.setValue("fileSize", 0);
			fileItem.setValue("createTime", initDate);
			fileItem.setValue("modifyTime", initDate);
			rfs.getRegistry().updateItem(fileItem);
			rfs.sendNotify(this, AbstractWebFileSystem.CREATE_NOTIFY);
		} catch (RegistryException e) {
			throw new IOException (e.getMessage());
		}
		
		return new RegFsWebFileResource(rfs, filePath, this);
	}

	@Override
	public void deleteResource(String fileName) throws IOException {
		
		// 先找下同名文件，没找则就直接生成注册表路径
		RegistryPath delPath = null;
		for (WebFileEntry entry : this.listFiles()){
			if (!entry.isDirectory() && StringKit.equals(fileName, entry.getFileName())){
				delPath = ((AbstractRegFsWebFile)entry).getRegistryPath();
				break;
			}
		}
		if (delPath == null){
			delPath = getRegistryPath().makeChild(getPathNameByFileName (fileName, false));
		}
		
		try {
			rfs.getRegistry().removeItem(delPath);
			rfs.sendNotify(this, AbstractWebFileSystem.REMOVE_NOTIFY);
		} catch (RegistryException e) {
			throw new IOException (e.getMessage());
		}
	}
	
	/**
	 * 取得指定目录下的文件与目录列表
	 * @param registry
	 * @param filePath
	 * @param parent
	 * @return
	 */
	public static List<WebFileEntry> listFiles(RegistryFileSystem rfs,
							RegistryPath filePath, WebFileFolder parent){
		
		List<WebFileEntry> result1 = new ArrayList<WebFileEntry>();
		List<WebFileEntry> result2 = new ArrayList<WebFileEntry>();
		
		for (RegistryItem child : rfs.getRegistry().allChildren(filePath)){
			
			String type = AbstractRegFsWebFile.getEntryType(child);
			if (StringKit.equals(type, AbstractRegFsWebFile.ENTRY_DIRECTORY)){
				result1.add(new RegFsWebFileFolder(rfs, child.getPath(), parent));
			}else if (StringKit.equals(type, AbstractRegFsWebFile.ENTRY_RESOURCE)){
				result2.add(new RegFsWebFileResource(rfs, child.getPath(), parent));
			}
		}
		
		// 目录在前，文件在后
		result1.addAll(result2);
		
		return result1;
	}
}
