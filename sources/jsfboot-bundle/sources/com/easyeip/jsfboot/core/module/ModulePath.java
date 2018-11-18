package com.easyeip.jsfboot.core.module;

import java.net.URL;

/**
 * 分解URL文件名称
 * @author ihaoqi
 *
 */
public class ModulePath {
	
	private URL fileUrl;

	public ModulePath(URL fileUrl){
		this.fileUrl = fileUrl;
	}
	
	public static ModulePath valueOf(URL fileUrl){
		return new ModulePath(fileUrl);
	}
	
	public URL getRawUrl(){
		return fileUrl;
	}
	
	/**
	 * 判断文件是否在jar中
	 * @return
	 */
	public boolean isInJarFile(){
		return "jar".equals(fileUrl.getProtocol()) && (fileUrl.getFile().indexOf('!') > 0);
	}
	
	/**
	 * 取得内部文件名（不包含内部路径），如 xxx.xml
	 * @return E:/easyeip-tsms/WEB-INF/lib/jsfboot-bundle-1.0.0.jar
	 */
	public String getJarFile(){
		//jar格式的file： file:/E:/worksapce/easyeip-tsms/WEB-INF/lib/jsfboot-bundle-1.0.0.jar!/META-INF/jsfboot-module.xml
		if (isInJarFile ()){
			String file = fileUrl.getFile();
			int index = file.indexOf('!');
			if (index > 0){
				file = file.substring(0, index);
			}
			if (file.startsWith("file:")){
				file = file.substring("file:".length()+1);
			}
			return file;
		}
		
		return null;
	}
	
	/**
	 * 返回内部文件所有的路径
	 * @return 返回jar内部文件名称，如/META-INF/jsfboot-module.xml
	 */
	public String getFilename(){
		String file = fileUrl.getFile();
		if (isInJarFile()){
			int index = file.indexOf('!');
			if (index > 0){
				file = file.substring(index+1);
				return file;
			}
		}
		
		if (file.startsWith("/")){
			file = file.substring(1);
		}
		return file;
	}
	
	@Override
	public String toString() {
		if (isInJarFile()){
			return getJarFile () + "!" + getFilename();
		}else{
			return getFilename();
		}
	}
}
