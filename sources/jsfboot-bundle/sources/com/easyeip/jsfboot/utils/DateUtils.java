package com.easyeip.jsfboot.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	
	/**
	 * 日期转字符串
	 * @param date
	 * @return
	 */
	public static String dateToStr(Date date){
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		return sdf.format(date);
	}

	/**
	 * 字符串转日期
	 * @param dateStr
	 * @return
	 */
	public static Date strToDate (String dateStr){
		if (StringKit.isEmpty(dateStr))
			return null;
		
		SimpleDateFormat sdf= new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");
		try {
			return sdf.parse(dateStr);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 日期转换成秒
	 * @param date
	 * @return
	 */
	public static long dateToSecond(Date date){
		return date.getTime() / 1000;
	}
	
	/**
	 * 秒转换成日期
	 * @param decond
	 * @return
	 */
	public static Date secondToDate (long decond){
		return new Date(decond * 1000);
	}
}
