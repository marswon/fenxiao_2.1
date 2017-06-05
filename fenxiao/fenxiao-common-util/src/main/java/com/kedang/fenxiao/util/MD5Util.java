package com.kedang.fenxiao.util;

import java.security.MessageDigest;



public class MD5Util {


	private MD5Util() {
	}

	public static String encrypt(String str) {
		if (str == null) {
			return null;
		}
		String _res = null;
		try {
			_res = new String(str);
			MessageDigest md = MessageDigest.getInstance("MD5");
			_res = byte2hexString(md.digest(_res.getBytes()));
		} catch (Exception e) {
	e.getMessage();
		}
		return _res;
	}

	private static String byte2hexString(byte[] byteArray) {
		StringBuffer _sb = new StringBuffer(byteArray.length * 2);
		for (int i = 0; i < byteArray.length; i++) {
			if ((byteArray[i] & 0xff) < 0x10) {
				_sb.append("0");
			}
			_sb.append(Long.toString(byteArray[i] & 0xff, 16));
		}
		return _sb.toString();
	}
	
	//与ASP兼容的MD5加密算法
//	  public static String GetMD5(String s)
//	  {
//	    MD5 md5 = new MD5CryptoServiceProvider();
//	    byte[] t = md5.ComputeHash(Encoding.GetEncoding("utf-8").GetBytes(s));
//	    StringBuilder sb = new StringBuilder(32);
//	    for (int i = 0; i < t.length; i++)
//	    {
//	      sb.Append(t[i].toString("x").PadLeft(2, '0'));
//	    }
//	    return sb.toString();
//	  }

	
}
