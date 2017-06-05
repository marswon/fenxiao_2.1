package com.kedang.fenxiao.util;

/**
 *
 * @author Ocean
 *
 */
public class NumUtils {

	public static boolean isNum(String str) {
		return str.matches("^[-+]?(([0-9]+)([.]([0-9]+))?|([.]([0-9]+))?)$");
	}
}
