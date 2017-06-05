package com.kedang.fenxiao.schedule.monitor.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年3月16日 下午1:47:08 
 */
public class CommonUtil
{
	private CommonUtil()
	{
	}

	private static Logger logger = LogManager.getLogger(CommonUtil.class);

	public static boolean isBlank(String string)
	{
		return string == null || "".equals(string);
	}

	public static boolean isMessageTime(String startStr, String endStr) throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar startTime = Calendar.getInstance();
		startTime.setTime(sdf.parse(startStr));
		Calendar endTime = Calendar.getInstance();
		endTime.setTime(sdf.parse(endStr));
		Calendar now = Calendar.getInstance();
		startTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
		startTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
		startTime.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
		endTime.set(Calendar.YEAR, now.get(Calendar.YEAR));
		endTime.set(Calendar.MONTH, now.get(Calendar.MONTH));
		endTime.set(Calendar.DAY_OF_MONTH, now.get(Calendar.DAY_OF_MONTH));
		logger.info("非消息时间为：[" + sdf.format(startTime.getTime()) + "," + sdf.format(endTime.getTime()) + "]");
		if (startTime.getTimeInMillis() <= now.getTimeInMillis() && now.getTimeInMillis() <= endTime.getTimeInMillis())
		{
			return false;
		}
		return true;
	}

}
