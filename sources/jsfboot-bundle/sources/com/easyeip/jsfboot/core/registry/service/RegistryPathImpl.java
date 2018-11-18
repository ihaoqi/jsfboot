package com.easyeip.jsfboot.core.registry.service;

import java.util.ArrayList;
import java.util.List;

import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.RegistryException;
import com.easyeip.jsfboot.core.registry.RegistryPath;

public class RegistryPathImpl extends RegistryPath {
	
	protected List<String> pathSection;

	public RegistryPathImpl (RegistryPath path){
		pathSection = new ArrayList<String> ();
		for (String name : path.toNameArray()){
			pathSection.add(name);
		}
	}
	
	/**
	 * 
	 * @param path
	 * @throws Exception
	 */
	public RegistryPathImpl(String path) throws RegistryException{
 
		if (StringKit.isEmpty(path) || path.startsWith(SPARATOR_CHAR) == false){
			throw new RegistryException ("路径必须以 '" + SPARATOR_CHAR + "'开头。");
		}
		
		pathSection = new ArrayList<String> ();
		String[] sects = path.split(SPARATOR_CHAR);
		for (String name : sects){
			name = name.trim();
			if (StringKit.isEmpty(name))
				continue;
			
			if (!isValidName(name)){
				throw new RegistryException (name + " 不符合名称规范。");
			}
			
			pathSection.add(name);
		}
	}
	
	/**
	 * 创建上级结点路径对象
	 * @return
	 */
	@Override
	public RegistryPath makeParent(){
		int cnt = pathSection.size() - 1;
		if (cnt < 0)
			return null;
		
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < cnt; i ++){
			sb.append(SPARATOR_CHAR);
			sb.append(pathSection.get(i));
		}
		
		if (sb.length() == 0){
			sb.append(SPARATOR_CHAR);
		}
		
		return valueOfne (sb.toString());
	}
	
	/**
	 * 创建新子结点路径对象
	 * @param name
	 * @return
	 */
	@Override
	public RegistryPath makeChild(String name){
		if (isValidName(name) == false)
			return null;
		
		return valueOfne (getFullPath () + (isRootPath() ? "" : SPARATOR_CHAR) + name);
	}
	
	/**
	 * 添加子结点路径
	 * @param name
	 * @return
	 */
	@Override
	public RegistryPath addChild (String name){
		if (pathSection == null || isValidName(name) == false){
			return null;
		}
		
		pathSection.add(name);
		return this;
	}
	
	@Override
	public int hashCode() {
		return getFullPath().hashCode();
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		
		String s1 = obj.toString();
		String s2 = this.toString();
		if ((s1 == null || s2 == null) && s1 != s2)
			return false;
		
		return s1.equals(s2);
	}

	@Override
	public String toString() {
		return getFullPath();
	}
	
	/**
	 * 判断是否是根结点
	 * @return
	 */
	@Override
	public boolean isRootPath(){
		return pathSection == null || pathSection.size() == 0;
	}

	@Override
	public int getItemCount() {
		return pathSection.size();
	}

	@Override
	public String getItemName(int index) {
		return pathSection.get(index);
	}

	@Override
	public String getFullPath() {
		StringBuilder sb = new StringBuilder();
		for (String s : pathSection){
			sb.append(SPARATOR_CHAR);
			sb.append(s);
		}
		
		if (sb.length() == 0){
			sb.append(SPARATOR_CHAR);
		}
		return sb.toString();
	}

	@Override
	public String[] toNameArray() {
		return pathSection.toArray(new String[0]);
	}

	@Override
	public String getLastName() {
		
		if (isRootPath ())
			return null;

		return pathSection.get(pathSection.size()-1);
	}
}
