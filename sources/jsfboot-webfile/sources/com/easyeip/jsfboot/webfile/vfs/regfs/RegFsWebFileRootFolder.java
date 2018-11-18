package com.easyeip.jsfboot.webfile.vfs.regfs;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;

public class RegFsWebFileRootFolder extends RegFsWebFileFolder {
	
	private RegistryPath rootPath;
	private RegistryFileSystem rfs;
	
	public RegFsWebFileRootFolder(RegistryFileSystem rfs, RegistryPath rootPath){
		super(rfs, rootPath,null);
		this.rfs = rfs;
		this.rootPath = rootPath;
	}
	
	@Override
	public boolean isRoot() {
		return true;
	}

	@Override
	public List<WebFileEntry> listFiles() {
		return listFiles(rfs, rootPath, this);
	}
	
	@Override
	public WebFileResource createResource(String fileName) throws IOException {
		throw new IOException("根目录不支持创建资源。");
	}

	@Override
	public void deleteResource(String fileName) throws IOException {
		throw new IOException("根目录不支持删除资源。");
	}
	
	@Override
	public void rename(String newName) throws IOException {
		throw new IOException("根目录不支持删除资源。");
	}

	@Override
	public String getFileName() {
		return "";
	}

	@Override
	public String getFullPath() {
		return RegistryPath.SPARATOR_CHAR;
	}

	@Override
	public Date getCreateTime() {
		return new Date();
	}

	@Override
	public Date getModifyTime() {
		return new Date();
	}

	@Override
	public boolean isDirectory() {
		return true;
	}
}
