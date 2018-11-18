package com.easyeip.jsfboot.core.secutiry;

import java.security.MessageDigest;

import com.easyeip.jsfboot.utils.Utf8;

/**
 * 对密码进行加盐，用的是spring Security格式，即：密码{盐}
 * @author ihaoqi
 *
 */
public class PasswordSaltUtils {

	/**
	 * 对密码进行加密
	 * 
	 * @param password
	 * @param salt
	 * @return
	 */
	public static String encode(String password, String salt) {

		String encode = mergePasswordAndSalt(password, salt, true);

		return getMD5(encode);
		
	}

	/**
	 * 对字符串进行md5加密
	 * @param message
	 * @return
	 */
	public static String getMD5(String message) {
		String md5str = "";
		try {
			// 1 创建一个提供信息摘要算法的对象，初始化为md5算法对象
			MessageDigest md = MessageDigest.getInstance("MD5");

			// 2 将消息变成byte数组
			byte[] input = Utf8.encode(message);

			// 3 计算后获得字节数组,这就是那128位了
			byte[] buff = md.digest(input);

			// 4 把数组每一字节（一个字节占八位）换成16进制连成md5字符串
			md5str = bytesToHex(buff);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return md5str;
	}

	/**
	 * 二进制转十六进制
	 * 
	 * @param bytes
	 * @return
	 */
	public static String bytesToHex(byte[] bytes) {
		StringBuffer md5str = new StringBuffer();
		// 把数组每一字节换成16进制连成md5字符串
		int digital;
		for (int i = 0; i < bytes.length; i++) {
			digital = bytes[i];

			if (digital < 0) {
				digital += 256;
			}
			if (digital < 16) {
				md5str.append("0");
			}
			md5str.append(Integer.toHexString(digital));
		}
		return md5str.toString();
	}

	private static String mergePasswordAndSalt(String password, Object salt, boolean strict) {
		if (password == null) {
			password = "";
		}

		if (strict && (salt != null)) {
			if ((salt.toString().lastIndexOf("{") != -1) || (salt.toString().lastIndexOf("}") != -1)) {
				throw new IllegalArgumentException("Cannot use { or } in salt.toString()");
			}
		}

		if ((salt == null) || "".equals(salt)) {
			return password;
		} else {
			return password + "{" + salt.toString() + "}";
		}
	}
}
