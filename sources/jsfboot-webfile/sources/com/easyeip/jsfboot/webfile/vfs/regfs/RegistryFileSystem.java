package com.easyeip.jsfboot.webfile.vfs.regfs;

import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.webfile.vfs.AbstractWebFileSystem;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;

public class RegistryFileSystem extends AbstractWebFileSystem {
	
	public static final String REGISTRY_WEBFILES_PATH = "/jsfboot/module/jsfboot-webfile/web-files";
	
	private RegistryService registry;
	private RegistryPath rootPath;
	private WebFileFolder rootFolder;
	
	public RegistryFileSystem(RegistryService registry){
		this.registry = registry;
		this.rootPath = RegistryPath.valueOfne(REGISTRY_WEBFILES_PATH);
	}
	
	public RegistryService getRegistry(){
		return registry;
	}

	@Override
	public WebFileFolder getRootFolder() {
		if (rootFolder == null){
			rootFolder = new RegFsWebFileRootFolder(this, rootPath);
		}
		
		return rootFolder;
	}
}
