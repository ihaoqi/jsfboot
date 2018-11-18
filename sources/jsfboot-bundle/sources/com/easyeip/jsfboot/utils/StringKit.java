package com.easyeip.jsfboot.utils;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * jsfboot通用工具类
 * @author ihaoqi
 *
 */
public class StringKit {
	
	/**
	 * 判断字符串是否为空
	 * @param text
	 * @return
	 */
	public static boolean isEmpty (String text){
		if (text == null || text.length() == 0)
			return true;
		return false;
	}
	
	/**
	 * 判断字符串是否不为空
	 * @param text
	 * @return
	 */
	public static boolean notEmpty(String text){
		return !isEmpty(text);
	}
	
	/**
	 * 截取字符串左边的子串
	 * @param text
	 * @param len
	 * @return
	 */
	public static String left(String text, int len){
		if (isEmpty(text))
			return "";
		return text.substring(0, Math.min(len, text.length()));
	}
	
	/**
	 * 计算字符串长度
	 * @param str
	 * @return
	 */
	public static int strlen(String str){
		if(str == null)
			return 0;
		return str.length();
	}
	
	/**
	 * 判断字符串是否是true
	 * @param text
	 * @return
	 */
	public static boolean isTrue(String text){
		return isTrue(text, false);
	}
	
	public static boolean isTrue(String text, boolean defValue){
		if (isEmpty(text))
			return defValue;
		
		return text.toLowerCase().equals("true");
	}
	
	/**
	 * 两字符串是否相同
	 * @param text1
	 * @param text2
	 */
	public static boolean equals(String text1, String text2) {
		
		if (text1 == null && text2 == null)
			return true;
		
		if (text1 != null && text2 != null)
			return text1.equals(text2);
		
		return false;
	}
	
	/**
	 * 转换成数值
	 * @param text
	 * @return
	 */
	public static int toInteger(String text, int defValue){
		if (StringKit.isEmpty(text))
			return defValue;
		return Integer.valueOf(text).intValue();
	}
	
	/**
	 * 转换成数值
	 * @param text
	 * @return
	 */
	public static long toLong(String text, int defValue){
		if (StringKit.isEmpty(text))
			return defValue;
		return Long.valueOf(text).longValue();
	}
	
	/**
	 * 把以CRLF为换行为文本转换成行
	 * @param text
	 * @return
	 */
	public static List<String> readCrlfLines(String text){
		
		List<String> lines = new ArrayList<String>();
		
		// 先把crlf换成行
		try{	
			ByteArrayInputStream input = new ByteArrayInputStream(text.getBytes("utf-8"));
			InputStreamReader read = new InputStreamReader(input,"utf-8");
			BufferedReader bufferedReader = new BufferedReader(read);
	        String lineTxt = null;
	        while((lineTxt = bufferedReader.readLine()) != null){
	        	lines.add(lineTxt);
	        }
	        read.close();
		}catch (Exception e){
			lines.clear();
		}
		
		return lines;
	}
	
	/**
	 * 把字符串转换成key value键值对，如aa=23;bb=34;
	 * @param lines
	 * @param kvJoinChar
	 * @return
	 */
	public static List<KeyValuePair> readKeyValueLines(String text, String kvJoinChar){
		List<String> kvLines = readCrlfLines(text);
		
		List<KeyValuePair> pairList = new ArrayList<KeyValuePair>();
		
		for (String line : kvLines){
			String[] lvpair = line.split(";");
			for (String pair : lvpair){
				pair = pair.trim();
				if (StringKit.isEmpty(pair))
					continue;
				pairList.add(KeyValuePair.parse(pair, kvJoinChar, true));
			}
		}
		
		return pairList;
	}

	/**
	 * 去除头尾空白字符，如果输入为null也返回null
	 * @param params
	 * @return
	 */
	public static String trim(String params) {
		if (params == null)
			return params;
		return params.trim();
	}
	
	/**
	 * 把字符串根据分割符转换成列表，有重复的会去除
	 * @param str
	 * @param splitChar
	 * @return
	 */
	public static List<String> stringSplit(String str, String splitChar){
		List<String> list = new ArrayList<String>();
		
		if (StringKit.isEmpty(str))
			return list;
		
		for (String s : str.split(splitChar)){
			String ss = s.trim();
			if (StringKit.notEmpty(ss) && !list.contains(ss)){
				list.add(ss);
			}
		}
		
		return list;
	}
	
	public static String listToString (List<String> list, String splitChar){
		StringBuilder sb = new StringBuilder();
		for (String s : list){
			if (sb.length() > 0)
				sb.append(splitChar);
			sb.append(s);
		}
		
		return sb.toString();
	}
}
