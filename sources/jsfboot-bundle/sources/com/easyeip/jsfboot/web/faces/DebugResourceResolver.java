package com.easyeip.jsfboot.web.faces;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import javax.faces.view.facelets.ResourceResolver;

import org.apache.myfaces.view.facelets.impl.DefaultResourceResolver;

/**
 * 查询jsf页面
 * @author 成慧
 *
 */
@SuppressWarnings("deprecation")
public class DebugResourceResolver extends ResourceResolver {
	
	private ResourceResolver defaultResolver;
	
	public DebugResourceResolver(){
		defaultResolver = new DefaultResourceResolver ();
	}

	@Override
	public URL resolveUrl(String path) {
		// 查找路径是否是模块路径，并且存在开发路径
		try {
			File file = DebugModuleResources.getInstance().getResourcesPath(path);
			if (file != null){
				String newPath = file.getPath() + path;
				File newFile = new File (newPath);
				if (newFile.exists() && newFile.isFile()){
					return new URL("file:" + newPath);
				}
			}
		} catch (MalformedURLException e) {
			return defaultResolver.resolveUrl(path);
		}
		
		return defaultResolver.resolveUrl(path);
	}

}
