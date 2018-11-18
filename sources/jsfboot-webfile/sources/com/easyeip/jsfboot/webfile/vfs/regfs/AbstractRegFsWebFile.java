package com.easyeip.jsfboot.webfile.vfs.regfs;

import java.io.IOException;
import java.util.Date;

import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.utils.DateUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.vfs.AbstractWebFileSystem;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public abstract class AbstractRegFsWebFile implements WebFileEntry {

	public static final String ENTRY_RESOURCE = "resource";
	public static final String ENTRY_DIRECTORY = "directory";
	
	private static final String RES_PATH_PREFIX = "res_";
	private static final String DIR_PATH_PREFIX = "dir_";
	
	RegistryFileSystem rfs;
	private WebFileFolder parent;
	private RegistryPath filePath;
	private RegistryItem filePathItem;
	
	public AbstractRegFsWebFile(RegistryFileSystem rfs, RegistryPath filePath, WebFileFolder parent){
		this.rfs = rfs;
		this.filePath = filePath;
		this.parent = parent;
	}

	@Override
	public String getFileName() {
		return getRegistryItem().getDefaultValue();
	}

	@Override
	public String getFullPath() {
		String path = getFileName();
		
		WebFileFolder parent = this.parent;
		while (parent != null && !parent.isRoot()){
			path = parent.getFileName() + RegistryPath.SPARATOR_CHAR + path;
			parent = parent.getParent();
		}
		
		path = RegistryPath.SPARATOR_CHAR + path;
		
		return path;
	}

	@Override
	public Date getCreateTime() {
		return DateUtils.strToDate(getRegistryItem().getValue("createTime"));
	}

	@Override
	public Date getModifyTime() {
		return DateUtils.strToDate(getRegistryItem().getValue("modifyTime"));
	}
	
	public static String getEntryType(RegistryItem item){
		return item.getValue("entryType");
	}

	@Override
	public boolean isDirectory() {
		return StringKit.equals(getEntryType (getRegistryItem()), ENTRY_DIRECTORY);
	}

	@Override
	public WebFileFolder getParent() {
		return parent;
	}
	
	@Override
	public void rename(String newName) throws IOException {
		RegistryItem item = getRegistryItem();
		String regItemName = getPathNameByFileName (newName, this.isDirectory());
		item.setName(regItemName);
		item.setDefaultValue(newName);
		try {
			rfs.getRegistry().updateItem(item);
			
			// 更新路径
			filePath = filePath.makeParent().addChild(regItemName);
			filePathItem = null;
			rfs.sendNotify(this, AbstractWebFileSystem.RENAME_NOTIFY);
		} catch (RegistryException e) {
			item.reset();
			throw new IOException (e.getMessage());
		}
	}
	
	@Override
	public WebFileResource asResource() {
		if (this.isDirectory())
			return null;
		
		return (WebFileResource) this;
	}

	@Override
	public WebFileFolder asDirectory() {
		if (this.isDirectory())
			return (WebFileFolder) this;
		
		return null;
	}

	public RegistryItem getRegistryItem (){
		if (filePathItem == null){
			filePathItem = rfs.getRegistry().getItem(filePath);
			if (filePathItem == null){
				try {
					rfs.getRegistry().createItem(filePath);
				} catch (RegistryException e) {
					e.printStackTrace();
				}
			}
		}
		
		return filePathItem;
	}
	
	public RegistryPath getRegistryPath(){
		return this.filePath;
	}
	
	/**
	 * 把文件名转换可用的路径名
	 * @param fileName
	 * @return
	 */
	String getPathNameByFileName (String fileName, boolean isFolder){
		
		if (StringKit.isEmpty(fileName))
			return fileName;
		
		fileName = fileName.replace(" ", "_");
		
		return (isFolder ? DIR_PATH_PREFIX : RES_PATH_PREFIX) +
				RegistryPath.normalizeName(fileName); 
//
//		// 如果不是标准文件名，则base64
//		if (!RegistryPath.isValidName(fileName)){
//			// 把不标准的字符转换成十六进制的值
//			String ch; String newName = "";
//			for (int i = 0; i < fileName.length(); i ++){
//				ch = fileName.substring(i, i + 1);
//				if (RegistryPath.isValidName("K"+ch))
//					newName += ch;
//				else
//					newName += "_" + Integer.toHexString(ch.getBytes()[0]) + "_";
//			}
//			fileName = newName;
//		}
//		
//		return (isFolder?DIR_PATH_PREFIX:RES_PATH_PREFIX) + fileName;
	}
}
