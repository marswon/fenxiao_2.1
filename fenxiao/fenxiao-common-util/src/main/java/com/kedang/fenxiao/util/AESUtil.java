package com.kedang.fenxiao.util;

import java.lang.reflect.Method;
import java.security.MessageDigest;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Encoder;

public class AESUtil {

	/**
	 * 加密
	 * @param encData 要加密的数据
	 * @param secretKey 密钥 ,16位的数字和字母
	 * @param vector 初始化向量,16位的数字和字母
	 * @return
	 * @throws Exception
	 */
	public static String Encrypt(String encData ,String secretKey,String vector) throws Exception {
		
		if(secretKey == null) {
			return null;
		}
		if(secretKey.length() != 16) {
			return null;
		}
		byte[] raw = secretKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");// "算法/模式/补码方式"
		IvParameterSpec iv = new IvParameterSpec(vector.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(encData.getBytes());
		return ObjectSerializer.encodeBytes( encrypted );
		//return ObjectSerializer.bytesToHexString(encrypted);
	}
	
	/**
	 * 解密
	 * @param decData 要解密的数据
	 * @param secretKey 密钥 ,16位的数字和字母
	 * @param vector 初始化向量,16位的数字和字母
	 * @return
	 * @throws Exception
	 */
	public static String Decrypt(String decData, String secretKey, String vector) throws Exception {
		if(secretKey == null) {
			return null;
		}
		if(secretKey.length() != 16) {
			return null;
		}
		try {
			byte[] raw = secretKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(decodeBytes(decData));
			
			return new String(original);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 解密
	 * @param decData 要解密的数据
	 * @param secretKey 密钥 ,16位的数字和字母
	 * @param vector 初始化向量,16位的数字和字母
	 * @return
	 * @throws Exception
	 */
	public static String DecryptHex(String decData, String secretKey, String vector) throws Exception {
		if(secretKey == null) {
			return null;
		}
		if(secretKey.length() != 16) {
			return null;
		}
		try {
			byte[] raw = secretKey.getBytes("ASCII");
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec(vector.getBytes());
			cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
			byte[] original = cipher.doFinal(hexStringToByte(decData));
			
			return new String(original);
		} catch (Exception ex) {
			return null;
		}
	}
	
	/**
	 * 加密(base64)
	 * @param sSrc 要加密的数据
	 * @param sKey 密钥 ,16位的数字和字母
	 * @param ivParameter 初始化向量,16位的数字和字母
	 * @return
	 * @throws Exception
	 */
	public static String EncryptForBase64(String sSrc, String sKey, String ivParameter) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		byte[] raw = sKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
		IvParameterSpec iv = new IvParameterSpec(ivParameter.getBytes());// 使用CBC模式，需要一个向量iv，可增加加密算法的强度
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
		byte[] encrypted = cipher.doFinal(sSrc.getBytes("utf-8"));
		return new BASE64Encoder().encode(encrypted);// 此处使用BASE64做转码。
	}

	/**
	 * 16位MD5加密
	 * @param data
	 * @return
	 */
	public final static String MD5(String data) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','a','b','c','d','e','f'};       
        try {
            byte[] btInput = data.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
		
	/**
	 * 转字节数组
	 * @param str
	 * @return
	 */
	public static byte[] decodeBytes(String str) {
		byte[] bytes = new byte[str.length() / 2];
		for (int i = 0; i < str.length(); i += 2) {
			char c = str.charAt(i);
			bytes[i / 2] = (byte) ((c - 'a') << 4);
			c = str.charAt(i + 1);
			bytes[i / 2] += (c - 'a');
		}
		return bytes;
	}
	
	public static byte[] hexStringToByte(String hex) {   
	    int len = (hex.length() / 2);   
	    byte[] result = new byte[len];   
	    char[] achar = hex.toCharArray();   
	    for (int i = 0; i < len; i++) {   
	     int pos = i * 2;   
	     result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));   
	    }   
	    return result;   
	} 
	
	private static byte toByte(char c) {   
	    byte b = (byte) "0123456789abcdef".indexOf(c);   
	    return b;   
	} 
	
	/**
	 * Base64加密
	 */
	public static String encodeBase64(byte[]input) throws Exception{
		Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("encode", byte[].class);
		mainMethod.setAccessible(true);
		 Object retObj=mainMethod.invoke(null, new Object[]{input});
		 return (String)retObj;
	}
	/**
	 * Base64解密
	 */
	public static byte[] decodeBase64(String input) throws Exception{
		Class<?> clazz=Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
		Method mainMethod= clazz.getMethod("decode", String.class);
		mainMethod.setAccessible(true);
		 Object retObj=mainMethod.invoke(null, input);
		 return (byte[])retObj;
	}
}
