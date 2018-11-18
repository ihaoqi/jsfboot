package com.easyeip.jsfboot.webfile.vfs;

import java.util.HashMap;
import java.util.Map;

import com.easyeip.jsfboot.core.JsfbootContext;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.webfile.vfs.osfs.WinLinuxFileSystem;
import com.easyeip.jsfboot.webfile.vfs.regfs.RegistryFileSystem;

public class WebFileSystemFactory {
	
	private static Map<String, WebFileSystemBuilder> wfsbMap;
	
	static{
		wfsbMap = new HashMap<String, WebFileSystemBuilder>();
		
		putBuilder("registry", new WebFileSystemBuilder(){

			@Override
			public WebFileSystem create() {
				RegistryService registry = JsfbootContext.getDriver().getRegistryService();
				return new RegistryFileSystem (registry);
			}
			
		});
		
		putBuilder("winlinux", new WebFileSystemBuilder(){

			@Override
			public WebFileSystem create() {
				return new WinLinuxFileSystem ();
			}
			
		});
	}
	
	public static void putBuilder (String name, WebFileSystemBuilder builder){
		wfsbMap.put(name, builder);
	}
	
	public static WebFileSystemBuilder getBuilder(String name){
		return wfsbMap.get(name);
	}

}
