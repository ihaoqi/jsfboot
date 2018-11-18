package com.easyeip.jsfboot.core.module;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.easyeip.jsfboot.utils.StringKit;

/**
 * 模块MANIFEST.MF文件
 * @author 成慧
 *
 */
public class ManifestFile {
	
	private static final String MANIFEST_FILE = "/META-INF/MANIFEST.MF";
	private static final String ATTR_RESOURCES_PATH = "Resources-Path";
	
	private ModulePath modulePath;
	private Map<String,String> manifest;
	
	public ManifestFile(ModulePath modulePath){
		this.modulePath = modulePath;
	}
	
	/**
	 * 取得资源路径属性
	 * @return
	 */
	public String getResourcesPath(){
		
		if (manifest == null){
			initManifest ();
		}
		
		return manifest.get(ATTR_RESOURCES_PATH);
	}
	
	/**
	 * 取得manifest属性
	 * @param name
	 * @return
	 */
	public String getattribute(String name){
		if (manifest == null){
			initManifest ();
		}
		
		return manifest.get(name);
	}
	
	private void initManifest(){
		
		manifest = new HashMap<String,String>();
		
		if (!modulePath.isInJarFile())
			return;
		
		// 判断文件
		String file = modulePath.getRawUrl().getFile();
		file = file.substring(0, file.indexOf("!") + 1);
		String mfPath = modulePath.getRawUrl().getProtocol() + ":" + file + MANIFEST_FILE;
		try {
			URL url = new URL (mfPath);
			manifest = loadManifest (url);
		} catch (Exception e) {
		}
	}
	
	private Map<String,String> loadManifest(URL url) throws IOException{
		Map<String,String> attrs = new HashMap<String,String>();
		InputStream is = url.openStream();
		InputStreamReader isr = new InputStreamReader(is, "utf-8");
		BufferedReader reader = new BufferedReader (isr);
		
		List<String> lines = new ArrayList<String> ();
		
		String fullLine = null;
		while (true){
			String line = reader.readLine();
			if (line == null)
				break;
			if (StringKit.isEmpty(line))
				continue;
			
			if (!line.startsWith(" ")){
				// 属性开始
				if (fullLine != null)
					lines.add(fullLine);
				fullLine = line;
			}else{
				// 属性值延续
				fullLine += line.substring(1);
			}
		}
		
		if (fullLine != null)
			lines.add(fullLine);
		
		// 分析所有行
		for (String line : lines){
			int idx = line.indexOf(":");
			if (idx <= 0) continue;
			String name = line.substring(0, idx);
			String value = StringKit.trim(line.substring(idx + 1));
			attrs.put (name, value);
		}
		
		return attrs;
	}

}
