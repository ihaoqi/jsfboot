package com.easyeip.jsfboot.webfile.vfs.regfs;

import java.util.ArrayList;
import java.util.List;

import org.primefaces.util.Base64;

public class ResourceEncodeUtils {
	
	/**
	 * 把数组编码成base64字符串，每rowBytes字节就换行
	 * @param buffer 要转换成的字节 
	 * @param rowBytes 每行字节数
	 * @return
	 */
	public static List<String> encodeResource (byte[] buffer, int rowBytes){
		String b64str = Base64.encodeToString(buffer, false);
		List<String> result = new ArrayList<String>();
		int curIndex = 0;
		while (curIndex < b64str.length()){
			int newIndex = curIndex + rowBytes;
			newIndex = Math.min(newIndex, b64str.length());
			if (newIndex <= curIndex)
				break;
			String line = b64str.substring(curIndex, newIndex);
			if (line.length() > 0){
				result.add(line);
				curIndex += line.length();
			}else{
				break;
			}
		}
		return result;
	}
	
	/**
	 * 把 encodeResource 转换成base64内容再还原成字节
	 * @param b64rows
	 * @return
	 */
	public static byte[] decodeResource(List<String> b64rows){
		StringBuilder sb = new StringBuilder();
		for (String b64 : b64rows){
			sb.append(b64);
		}
		
		return decodeResource(sb);
	}
	
	public static byte[] decodeResource(StringBuilder b64rows){
		return Base64.decode(b64rows.toString());
	}
}
