package com.easyeip.jsfboot.web.faces;

import java.io.File;

import javax.faces.application.Resource;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;

import org.apache.myfaces.application.DefaultResourceHandlerSupport;
import org.apache.myfaces.application.ResourceHandlerImpl;
import org.apache.myfaces.shared.resource.ResourceLoader;

/**
 * 模块开发阶段资源定位
 * @author 成慧
 *
 */
public class DebugResourceHandler extends ResourceHandlerWrapper {

	private ResourceHandler handler;
	private ResourceHandlerImpl impl;
	
	public DebugResourceHandler(ResourceHandler handler){
		this.handler = handler;
		impl = new ResourceHandlerImpl();
		// 设置它的资源加载器
		impl.setResourceHandlerSupport(new MySupport());
	}

	@Override
	public ResourceHandler getWrapped() {
		return handler;
	}

	@Override
	public Resource createResource(String resourceName, String libraryName) {
		// 判断库名称是否以模块开头，如果是则转到开发路径
		File file = DebugModuleResources.getInstance().getResourcesPath(libraryName);
		if (file != null){
			// 走自苹果和要求的资源处理器，它支持加载开发目录下的资源
			return impl.createResource(resourceName, libraryName);
		}
		
		return super.createResource(resourceName, libraryName);
	}
	
	/**
	 * 支持更多的资源加载器
	 * @author 成慧
	 *
	 */
	class MySupport extends DefaultResourceHandlerSupport{
		
		private ResourceLoader[] loaders;

		@Override
		public ResourceLoader[] getResourceLoaders() {
			if (loaders != null)
				return loaders;
			
			String directory = "resources";
			
			// 增加可加载外部文件的加载器
			ResourceLoader[] tmp = super.getResourceLoaders();
			loaders = new ResourceLoader[tmp.length+1];
			loaders[0] = new DebugResourceLoader("/" + directory);
			for (int i = 0; i < tmp.length; i ++){
				loaders[i+1] = tmp[i];
			}
			
			return loaders;
		}
		
	}
}
