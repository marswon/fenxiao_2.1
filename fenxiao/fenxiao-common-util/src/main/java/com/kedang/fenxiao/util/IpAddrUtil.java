package com.kedang.fenxiao.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端IP
 * @author Administrator
 * @date 2015年9月28日
 */
public class IpAddrUtil
{
	public static String getIpAddr(HttpServletRequest request)
	{
		String ip = "";
		if (null == request)
		{
			return ip;
		}
		ip = request.getHeader("x-forwarded-for");
		
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip))
		{
			ip = request.getRemoteAddr();
		}
		return ip;
	}
}