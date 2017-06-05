package com.kedang.fenxiao.schedule.monitor.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpException;

import com.kedang.fenxiao.entity.FXPerson;
import com.kedang.fenxiao.repository.MonitorDao;
import com.kedang.fenxiao.util.HttpUtils;
import com.kedang.fenxiao.util.MD5Util;

public class MessageUtil
{

	private static String userid = "149";
	private static String username = "KDHY";
	private static String password = "112233";
	private static String url = "http://123.56.234.185:7878/v2sms.aspx";

	public static String sendMessage(String receivers, String content) throws HttpException, IOException
	{
		String result = null;
		String timestamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		Map<String, String> params = new HashMap<String, String>();
		params.put("userid", userid);
		params.put("timestamp", timestamp);
		params.put("mobile", receivers);
		params.put("content", content);
		params.put("action", "send");
		String sign = MD5Util.encrypt(username + password + timestamp);
		params.put("sign", sign);
		result = HttpUtils.sendGet(url, params);
		return result;
	}

	public static void main(String[] args) throws HttpException, IOException
	{
		System.out.println(sendMessage("18621764382", "【流量加油站】上游资源[         ]48小时内失败率为,总订单量为"));
		//		System.out.println(getMessageReceivers(MonitorDao monitorDao));
	}

	/**
	  * 方法描述：
	  * @return
	  * @author: zhuwanlin
	  * @date: 2017年3月15日 下午1:49:33
	  */
	public static String getMessageReceivers(MonitorDao monitorDao)
	{
		try
		{
			StringBuffer sb = new StringBuffer();
			List<FXPerson> list = monitorDao.getMessagePerson();
			if (list != null && list.size() > 0)
			{
				for (FXPerson person : list)
				{
					sb.append(person.getMobile() + ",");
				}
			}
			String result = sb.toString();
			if (result.length() > 0)
			{
				return result.substring(0, result.length() - 1);
			}
			else
			{
				return result;
			}
		}
		catch (Exception e)
		{
			throw e;
		}
	}
}
