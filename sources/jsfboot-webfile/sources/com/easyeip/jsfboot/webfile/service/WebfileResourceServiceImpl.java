package com.easyeip.jsfboot.webfile.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.easyeip.jsfboot.admin.AdminHelpViewService;
import com.easyeip.jsfboot.admin.help.HelpBuilder;
import com.easyeip.jsfboot.admin.help.HelpCatalog;
import com.easyeip.jsfboot.core.annotation.UseJsfbootService;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryItem;
import com.easyeip.jsfboot.core.registry.RegistryPath;
import com.easyeip.jsfboot.core.registry.RegistryService;
import com.easyeip.jsfboot.core.services.GenericService;
import com.easyeip.jsfboot.core.services.ServiceContext;
import com.easyeip.jsfboot.core.services.ServiceUtils;
import com.easyeip.jsfboot.core.surface.PageDomainType;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.webfile.CustomResourceUrl;
import com.easyeip.jsfboot.webfile.ResourceUrlManager;
import com.easyeip.jsfboot.webfile.WebfileResourceProfile;
import com.easyeip.jsfboot.webfile.WebfileResourceService;
import com.easyeip.jsfboot.webfile.vfs.AbstractWebFileSystem;
import com.easyeip.jsfboot.webfile.vfs.WebFileEntry;
import com.easyeip.jsfboot.webfile.vfs.WebFileFolder;
import com.easyeip.jsfboot.webfile.vfs.WebFileListener;
import com.easyeip.jsfboot.webfile.vfs.WebFileResource;
import com.easyeip.jsfboot.webfile.vfs.WebFileSystem;
import com.easyeip.jsfboot.webfile.vfs.WebFileSystemBuilder;
import com.easyeip.jsfboot.webfile.vfs.WebFileSystemFactory;

public class WebfileResourceServiceImpl extends GenericService
		implements WebfileResourceService, ResourceUrlManager, WebFileListener {

	private static final String REGISTRY_CUSTOM_PATH = "/jsfboot/module/jsfboot-webfile/custom-url";
	
	@UseJsfbootService (RegistryService.class)
	RegistryService registry;
	
	@UseJsfbootService (AdminHelpViewService.class)
	AdminHelpViewService helpView;
	
	private WebFileSystemBuilder builder;
	private WebFileSystem wfs = null;
	
	private WebfileResourceProfile profile;
	private Map<String,WebFileEntry> resourceCache;

	private List<CustomResourceUrl> customUrlCache;
	private Map<String,CustomResourceUrl> customUrlMap;
	
	@Override
	public void startService(ServiceContext context) throws Exception {
		// 取得适配器名称
		String providerParam = ServiceUtils.getParamTextValue(context, "provider");
		builder = WebFileSystemFactory.getBuilder(providerParam);
		if (builder == null){
			throw new Exception("不支持的适配器 " + providerParam);
		}
		
		profile = new WebfileResourceProfile(registry);
		resourceCache = new HashMap<String,WebFileEntry>();
		
		// 注册帮助
		initHelpPage(context);
	}
	
	private void initHelpPage(ServiceContext context){
		HelpBuilder hb = helpView.getHelpBuilder();
		HelpCatalog root = helpView.addHelpDocument(PageDomainType.Admin,
				hb.newCatalog(context.getModule().getName(),
				context.getModule().getModuleInfo().getModuleTitle(),
				hb.includePage(hb.fullModulePath(context.getModule(), "/help/readme.xhtml"))));
		
		root.addChild(hb.newCatalog("multimedia", "文件资源", hb.includePage("multimedia.xhtml")));
	}

	@Override
	public WebFileSystem getFileSystem() {
		
		if (wfs == null){
			wfs = builder.create();
			wfs.addFileListener(this);
		}
		
		return wfs;
	}

	@Override
	public WebfileResourceProfile getProfile() {
		return profile;
	}
	
	@Override
	public void webFileNotify(WebFileEntry entry, String notify) {
		// 清除缓存
		if (!StringKit.equals(notify, AbstractWebFileSystem.CREATE_NOTIFY)){
			if (entry.isDirectory()){
				resourceCache.clear();
			}else{
				resourceCache.remove(entry.getFullPath());
			}
		}
	}
	
	@Override
	public WebFileEntry findResource(String filePath, boolean findFolder) {
		
		WebFileEntry cache = resourceCache.get(filePath);
		if (cache != null)
			return cache;
		
		WebFileFolder folder = getFileSystem().getRootFolder();
		
		// 查询目录
		String[] paths = filePath.split("/");
		if (paths.length == 0)
			return null;

		for (int i = 0; i < paths.length - (findFolder?0:1); i ++){
			
			String itemName = paths[i];
			if (StringKit.isEmpty(itemName))
				continue;
			
			boolean find = false;
			for (WebFileEntry entry : folder.listFiles()){
				if (entry.isDirectory() && entry.getFileName().equals(itemName)){
					folder = entry.asDirectory();
					find = true;
					break;
				}
			}
			
			if (find == false)
				return null;

		}
		
		if (findFolder){
			return folder;
		}
		
		// 查询文件
		String fileName = paths[paths.length-1];
		for (WebFileEntry entry : folder.listFiles()){
			if (!entry.isDirectory() && entry.getFileName().equals(fileName)){
				
				resourceCache.put(filePath, entry);
				
				return entry.asResource();
			}
		}
		
		return null;
	}

	@Override
	public List<CustomResourceUrl> getResourceList() {
		if (customUrlCache == null){
			customUrlCache = new ArrayList<CustomResourceUrl>();
			customUrlMap = null;
			for (RegistryItem item : registry.allChildren(RegistryPath.valueOfne(REGISTRY_CUSTOM_PATH))){
				String key = item.getName();
				String url = item.getValue("customUrl");
				String path = item.getValue("filePath");
				
				customUrlCache.add(new CustomResourceUrlImpl(key, url, path));
			}
			
		}
		return Collections.unmodifiableList(customUrlCache);
	}

	@Override
	public CustomResourceUrl lookupUrlResource(String customUrl) {
		if (customUrlCache == null || customUrlMap == null){
			List<CustomResourceUrl> urlList = getResourceList();
			customUrlMap = new HashMap<String,CustomResourceUrl>();
			for (CustomResourceUrl resu : urlList){
				customUrlMap.put(resu.getCustomUrl(), resu);
			}
		}
		return customUrlMap.get(customUrl);
	}

	@Override
	public boolean setResourceUrl(WebFileResource res, String newUrl) {
		
		RegistryPath regPath = null;
		
		// 如果已存在就使用原来的结点
		for (CustomResourceUrl resu : getResourceList()){
			if (StringKit.equals(resu.getSourcePath(), res.getFullPath())){
				regPath = RegistryPath.valueOfne(REGISTRY_CUSTOM_PATH).addChild(resu.getKey());
			}
		}

		if (regPath == null){
			String key = "UK" + UUID.randomUUID().toString().replace("-", "");
			regPath = RegistryPath.valueOfne(REGISTRY_CUSTOM_PATH).addChild(key);
		}
		
		try{
			customUrlCache = null;
			RegistryItem item = registry.createItem(regPath);
			item.setValue("customUrl", newUrl);
			item.setValue("filePath", res.getFullPath());
			registry.updateItem(item);
			return true;
		}catch (Exception e){
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean removeResourceUrl(String filePath) {
		for (CustomResourceUrl resu : getResourceList()){
			if (StringKit.equals(filePath, resu.getSourcePath())){
				RegistryPath regPath = RegistryPath.valueOfne(REGISTRY_CUSTOM_PATH).addChild(resu.getKey());
				try {
					registry.removeItem(regPath);
					customUrlCache = null;
					return true;
				} catch (RegistryException e) {
					e.printStackTrace();
				}
				
			}
		}
		return false;
	}

	@Override
	public ResourceUrlManager getCustomUrlManager() {
		return this;
	}

	@Override
	public String findResourceCustomUrl(WebFileResource res) {
		for (CustomResourceUrl resu : getResourceList()){
			if (StringKit.equals(resu.getSourcePath(), res.getFullPath()))
				return resu.getCustomUrl();
		}
		return null;
	}
}
