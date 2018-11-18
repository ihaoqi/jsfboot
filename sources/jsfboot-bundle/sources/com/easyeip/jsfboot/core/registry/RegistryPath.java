package com.easyeip.jsfboot.core.registry;


import com.easyeip.jsfboot.utils.NameUtils;
import com.easyeip.jsfboot.utils.StringKit;
import com.easyeip.jsfboot.core.registry.service.RegistryPathImpl;

/**
 * 注册表路径，只读
 * @author ihaoqi
 *
 */
public abstract class RegistryPath{
	public static final String SPARATOR_CHAR = "/";	// 路径分割符
	
	/**
	 * 根据路径文本转换成路径对象，应该为 /[name]/……/[name]。也可以以“/”结尾。
	 * @param path 文本路径对象，要符合路径格式
	 * @return 返回对应的路径对象
	 * @exception 出错会抛出异常
	 */
	public static RegistryPath valueOf (String path) throws RegistryException{
		return new RegistryPathImpl(path);
	}
	
	/**
	 * 根据路径文本转换成路径对象，应该为 /[name]/……/[name]。也可以以“/”结尾。
	 * @param path 文本路径对象，要符合路径格式
	 * @return 返回对应的路径对象，出错返回null
	 */
	public static RegistryPath valueOfne (String path){
		try {
			return new RegistryPathImpl(path);
		} catch (RegistryException e) {
			return null;
		}
	}
	
	public static RegistryPath valueOf (RegistryPath path){
		return new RegistryPathImpl(path);
	}

	/**
	 * 检查名称是否符合要求
	 * @param name
	 * @return 无问题返回null，有问题返回错误信息
	 */
	public static boolean isValidName(String name){
		
		return NameUtils.isXMLName(name);
	}
	
	/**
	 * 把非标准名称转换成标准名称
	 * @param name 要格式化的名称，如果为空则直接返回null
	 * @return
	 */
	public static String normalizeName (String name){
		
		if (StringKit.isEmpty(name))
			return null;
		
		if (RegistryPath.isValidName(name))
			return name;

		// 把不标准的字符转换成十六进制的值
		String ch; String newName = "";
		for (int i = 0; i < name.length(); i ++){
			ch = name.substring(i, i + 1);
			if (RegistryPath.isValidName("K"+ch))
				newName += ch;
			else
				newName += Integer.toHexString(ch.getBytes()[0]);
		}
		
		// 如果第一个字符是数值则在前面加上一个有效的字符
		if (newName.length() > 0 && Character.isDigit(newName.charAt(0)))
			return "_" + newName;
		else
			return newName;
	}

	/**
	 * 判断是否是根路径
	 * @return
	 */
	public abstract boolean isRootPath();

	/**
	 * 取得路径中的名称数量
	 * @return
	 */
	public abstract int getItemCount();
	
	/**
	 * 取得指定索引的名称
	 * @param index
	 * @return
	 */
	public abstract String getItemName (int index);
	
	/**
	 * 取得名称数组
	 * @return
	 */
	public abstract String[] toNameArray();
	
	/**
	 * 取得完整路径，如/easyeip/jsfboot
	 * @return
	 */
	public abstract String getFullPath ();
	
	/**
	 * 取得最后的名称
	 * @return 如 /easyeip/jsfboot 返回jsfboot。如果是根路径返回null
	 */
	public abstract String getLastName ();
	
	/**
	 * 创建上级结点路径对象
	 * @return 如 /easyeip/jsfboot 返回/easyeip。如果是根路径返回null
	 */
	public abstract RegistryPath makeParent();
	
	/**
	 * 创建新子结点路径对象
	 * @param name
	 * @return
	 */
	public abstract RegistryPath makeChild(String name);
	
	/**
	 * 添加子结点路径
	 * @param name
	 * @return
	 */
	public abstract RegistryPath addChild (String name);
}
