package com.kedang.fenxiao.util;

import java.util.UUID;

/**
 * UUID生成器
 */
public class UUIDTool {
	// 默认生成32位，格式化成不含"-"的字符串
	public static String getUuid(int count) {
		String[] splits = UUID.randomUUID().toString().split("-");
		StringBuffer sBuffer = new StringBuffer();
		for (String s : splits) {
			sBuffer.append(s);
		}
		String uuid = sBuffer.toString();
		
		if (count > 0 && count <= 32) {
			return uuid.substring(32 - count);
		}else{
			return uuid;
		}
	}
}
