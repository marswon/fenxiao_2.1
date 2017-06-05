package com.kedang.fenxiao.util;

import java.util.HashMap;
import java.util.Map;

public class UrlFormatUtils
{
	/**
	 * 解析出url参数中的键值对 如 "index.jsp?Action=del&id=123"，解析出Action:del,id:123存入map中
	 * 
	 * @param URL
	 *            url地址
	 * @return url请求参数部分
	 */
	public static Map<String, String> urlFormat(String strUrlParam)
	{
		Map<String, String> mapRequest = new HashMap<String, String>();

		String[] arrSplit = null;

		if (strUrlParam == null)
		{
			return mapRequest;
		}
		// 每个键值为一组 
		arrSplit = strUrlParam.split("[&]");
		for (String strSplit : arrSplit)
		{
			String[] arrSplitEqual = null;
			arrSplitEqual = strSplit.split("[=]");

			// 解析出键值
			if (arrSplitEqual.length > 1)
			{
				// 正确解析
				mapRequest.put(arrSplitEqual[0], arrSplitEqual[1]);
			}
			else
			{
				if (arrSplitEqual[0] != "")
				{
					// 只有参数没有值，不加入
					mapRequest.put(arrSplitEqual[0], "");
				}
			}
		}
		return mapRequest;
	}
}
