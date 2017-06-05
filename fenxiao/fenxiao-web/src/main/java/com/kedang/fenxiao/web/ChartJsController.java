package com.kedang.fenxiao.web;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * chart.js hander
 * @author Administrator
 * @date 2016年2月16日
 */
@Controller
@RequestMapping("chartJs")
public class ChartJsController extends BaseController
{

	/**
	 * 周提现，周还款
	 * @param type=1:提现，type=2:还款
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "getWeekMoney", method = RequestMethod.GET)
	public String getWeekMoney(HttpServletRequest request)
	{
		String startTime = null;
		String endTime = null;
		Integer type = null;
		String typeStr = request.getParameter("type");
		if (null != typeStr)
		{
			type = Integer.valueOf(typeStr);
		}
//		TreeMap<String, Object> map = moneyDayRecordService.getWeek(startTime, endTime, type);
		TreeMap<String, Object> map=null;
		String dataValue = "";
		String dataName = "";
		if (map == null)
		{
			return "window.code=200;window.dataName=;window.data=";
		}
		for (Map.Entry<String, Object> entry : map.entrySet())
		{
			dataValue += entry.getValue().toString() + ",";
			dataName += "\"" + entry.getKey().toString() + "\",";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("window.code=").append(200).append(";window.dataName=[")
				.append(dataName.substring(0, dataName.length() - 1)).append("];window.data=[")
				//.append(" 100, 200, 30, 40, 50 ").append("]");
				.append(dataValue.substring(0, dataValue.length() - 1)).append("]");
		return sb.toString();
	}

	/**
	 * 月提现,月还款
	 * @param type=1:提现，type=2:还款
	 * @return
	 */
	@SuppressWarnings("unused")
	@ResponseBody
	@RequestMapping(value = "getMonthMoney", method = RequestMethod.GET)
	public String getMonthMoney(HttpServletRequest request)
	{
		String startTime = null;
		String endTime = null;
		Integer type = null;
		String typeStr = request.getParameter("type");
		if (null != typeStr)
		{
			type = Integer.valueOf(typeStr);
		}
	//	TreeMap<String, Object> map = moneyDayRecordService.getMonth(startTime, endTime, type);
		TreeMap<String, Object> map =null;
		String dataValue = "";
		String dataName = "";
		if (map == null)
		{
			return "window.code=200;window.dataName=;window.data=";
		}
		for (Map.Entry<String, Object> entry : map.entrySet())
		{
			dataValue += entry.getValue().toString() + ",";
			dataName += "\"" + entry.getKey().toString() + "\",";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("window.code=").append(200).append(";window.dataName=[")
				.append(dataName.substring(0, dataName.length() - 1)).append("];window.data=[")
				//.append(" 100, 200, 30, 40, 50 ").append("]");
				.append(dataValue.substring(0, dataValue.length() - 1)).append("]");
		return sb.toString();
	}

	//判断选择的日期是否是本周     
	public static boolean isThisWeek(long time)
	{
		Calendar calendar = Calendar.getInstance();
		int currentWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		System.err.println(currentWeek);
		calendar.setTime(new Date(time));
		int paramWeek = calendar.get(Calendar.WEEK_OF_YEAR);
		System.err.println(currentWeek);
		if (paramWeek == currentWeek)
		{
			return true;
		}
		return false;
	}

	//判断选择的日期是否是今天     
	public static boolean isToday(long time)
	{
		return isThisTime(time, "yyyy-MM-dd ");
	}

	//判断选择的日期是否是本月    
	public static boolean isThisMonth(long time)
	{
		return isThisTime(time, "yyyy-MM");
	}

	private static boolean isThisTime(long time, String pattern)
	{
		Date date = new Date(time);
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String param = sdf.format(date);//参数时间      
		String now = sdf.format(new Date());//当前时间       
		if (param.equals(now))
		{
			return true;
		}
		return false;
	}

//	@Test
	public void testTime() throws ParseException
	{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
		Date date = sdf.parse("2016-04-12 16:51:55");
		System.err.println(isToday(date.getTime()));
		System.err.println(isThisMonth(date.getTime()));
		System.err.println(isThisWeek(date.getTime()));
	}
}
