package com.easyeip.jsfboot.web.faces;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.myfaces.shared.resource.ExternalContextResourceLoader;
import org.apache.myfaces.shared.resource.ResourceMeta;

/**
 * 资源加载器
 * @author 成慧
 *
 */
public class DebugResourceLoader extends ExternalContextResourceLoader {

	public DebugResourceLoader(String prefix) {
		super(prefix);
	}

	/**
	 * 获取资源url
	 */
	@Override
	public URL getResourceURL(String resourceId) {
    	File file = DebugModuleResources.getInstance().getResourcesPath(resourceId);
    	if (file == null){
    		return super.getResourceURL(resourceId);
    	}
    	
    	String newPath = file.getPath() + "/" + resourceId;
    	try {
			return new URL ("file:" + newPath);
		} catch (MalformedURLException e) {
			return null;
		}
	}
	
	@Override
	public InputStream getResourceInputStream(ResourceMeta resourceMeta) {
		String resourceId = '/' + resourceMeta.getResourceIdentifier();
		URL url = getResourceURL (resourceId);
		try {
			return url.openStream();
		} catch (IOException e) {
			return null;
		}
	}
}
