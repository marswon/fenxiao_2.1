package com.kedang.fenxiao.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

public class DateUtils {

	// /
	// 定义时间日期显示格式
	// /
	private final static String DATE_FORMAT = "yyyy-MM-dd";

	private final static String DATE_FORMAT_CN = "yyyy年MM月dd日";

	private final static String TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

	private final static String TIME_FORMAT_CN = "yyyy年MM月dd日 HH:mm:ss";

	private final static String MONTH_FORMAT = "yyyy-MM";

	private final static String DAY_FORMAT = "yyyyMMdd";
	public final static int TIME_DAY_MILLISECOND = 86400000;
	
	private final static String TIME_FORMAT_STRING = "yyyyMMddHHmmss";
	
	/**
	 * 系统日期上下限		lf1312120945
	 */
	public static final String MIN_DATE = "1901-01-01";
	public static final String MAX_DATE = "2100-01-01";
	
	public static boolean isDateValid(Date date)
	{
		if(date != null)
		{
			if(date.compareTo(getMinDate()) > 0 && date.compareTo(getMaxDate()) < 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * 最小日期		lf1312120946
	 * @return
	 */
	public static Date getMinDate()
	{
		return getFormatDate(MIN_DATE, DATE_FORMAT);
	}
	
	/**
	 * 最大日期		lf1312120947
	 * @return
	 */
	public static Date getMaxDate()
	{
		return getFormatDate(MAX_DATE, DATE_FORMAT);
	}

	// private final static String TIME_FORMAT_MILLI = "yyyy-MM-dd
	// HH:mm:ss.SSS";

	/**
	 * 取得当前系统时间，返回java.util.Date类型
	 *
	 * @see java.util.Date
	 * @return java.util.Date 返回服务器当前系统时间
	 */
	public static Date getCurrDate() {
		return new Date();
	}

	/**
	 * 取得当前系统时间戳
	 *
	 * @see java.sql.Timestamp
	 * @return java.sql.Timestamp 系统时间戳
	 */
	public static java.sql.Timestamp getCurrTimestamp() {
		return new java.sql.Timestamp(System.currentTimeMillis());
	}

	/**
	 * 将2007-12-1变成2007-12-01。将2007-9-1变为2007-09-01。
	 *
	 * @param date
	 * @return
	 */
	public static String getFormatDateV2(String date) {
		if (date == null) {
			return date;
		}

		String[] datearr = StringUtils.split(date, "-");
		if (datearr == null || datearr.length != 3) {
			return date;
		}

		StringBuffer ret = new StringBuffer();
		ret.append(datearr[0]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[1]) < 10 ? "0" + Integer.parseInt(datearr[1]) : datearr[1]);
		ret.append("-");
		ret.append(Integer.parseInt(datearr[2]) < 10 ? "0" + Integer.parseInt(datearr[2]) : datearr[2]);
		return ret.toString();
	}

	/**
	 * 从时间串中获取小时数。
	 *
	 * @param timestr
	 *            "2007-10-12 13:25:00"
	 * @return
	 */
	public static int getHourFromTimeString(String timestr) {
		if (StringUtils.isBlank(timestr)) {
			return 0;
		}

		return Integer.parseInt(timestr.substring(timestr.length() - 8, timestr.length() - 6));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
	 */
	public static String getFormatDate(Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date)
	 * @return Date 返回格式化后的日期，默认格式为为yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDateToDate(Date currDate) {
		return getFormatDate(getFormatDate(currDate));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getFormatDate_CN(Date currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate_CN(String)
	 * @return Date 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDateToDate_CN(Date currDate) {
		return getFormatDate_CN(getFormatDate_CN(currDate));
	}

	/**
	 * 得到格式化后的日期，格式为yyyy-MM-dd，如2006-02-15
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(String, String)
	 * @return Date 返回格式化后的日期，默认格式为yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT);
	}

	/**
	 * 得到格式化后的日期，格式为yyyy年MM月dd日，如2006年02月15日
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(String, String)
	 * @return 返回格式化后的日期，默认格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static Date getFormatDate_CN(String currDate) {
		return getFormatDate(currDate, DATE_FORMAT_CN);
	}

	/**
	 * 根据格式得到格式化后的日期
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的日期，格式由参数<code>format</code>
	 *         定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static String getFormatDate(Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static String getFormatDateTime(Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 *
	 * @param currDate
	 *            要格式环的时间
	 * @see #getFormatDateTime(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTimeToTime(Date currDate) {
		return getFormatDateTime(getFormatDateTime(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 */
	public static Date getFormatDateTime(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static String getFormatDateTime_CN(Date currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime_CN(String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTimeToTime_CN(Date currDate) {
		return getFormatDateTime_CN(getFormatDateTime_CN(currDate));
	}

	/**
	 * 得到格式化后的时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @see #getFormatDateTime(String, String)
	 * @return Date 返回格式化后的时间，默认格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 */
	public static Date getFormatDateTime_CN(String currDate) {
		return getFormatDateTime(currDate, TIME_FORMAT_CN);
	}

	/**
	 * 根据格式得到格式化后的时间
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#format(java.util.Date)
	 * @return String 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static String getFormatDateTime(Date currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.format(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.format(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的日期
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @param format
	 *            日期格式，如yyyy-MM-dd
	 * @see java.text.SimpleDateFormat#parse(String)
	 * @return Date 返回格式化后的日期，格式由参数<code>format</code>定义，如yyyy-MM-dd，如2006-02-15
	 */
	public static Date getFormatDate(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(DATE_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 根据格式得到格式化后的时间
	 *
	 * @param currDate
	 *            要格式化的时间
	 * @param format
	 *            时间格式，如yyyy-MM-dd HH:mm:ss
	 * @see java.text.SimpleDateFormat#parse(String)
	 * @return Date 返回格式化后的时间，格式由参数<code>format</code>定义，如yyyy-MM-dd HH:mm:ss
	 */
	public static Date getFormatDateTime(String currDate, String format) {
		SimpleDateFormat dtFormatdB = null;
		try {
			dtFormatdB = new SimpleDateFormat(format);
			return dtFormatdB.parse(currDate);
		} catch (Exception e) {
			dtFormatdB = new SimpleDateFormat(TIME_FORMAT);
			try {
				return dtFormatdB.parse(currDate);
			} catch (Exception ex) {
			}
		}
		return null;
	}

	/**
	 * 得到本日的上月时间 如果当日为2007-9-1,那么获得2007-8-1
	 *
	 *
	 */
	public static String getDateBeforeMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到本日的前几个月时间 如果number=2当日为2007-9-1,那么获得2007-7-1
	 *
	 *
	 */
	public static String getDateBeforeMonth(int number) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -number);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}
	
	public static String getDateBeforeOrAfterMonth(Date curDate,int number) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.MONTH, number);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	public static long getDaysOfDates(Date first, Date second) {
		Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
		Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

		long mils = d1.getTime() - d2.getTime();

		return mils / (TIME_DAY_MILLISECOND);
	}

	/**
	 * 获得两个Date型日期之间相差的天数（第2个减第1个）
	 *
	 * @param Date
	 *            first, Date second
	 * @return int 相差的天数
	 */
	public static int getDaysBetweenDates(Date first, Date second) {
		Date d1 = getFormatDateTime(getFormatDate(first), DATE_FORMAT);
		Date d2 = getFormatDateTime(getFormatDate(second), DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	public static int getDaysBetweenDatesV2(String first, String second) {
		Date d1 = getFormatDateTime(first, DATE_FORMAT);
		Date d2 = getFormatDateTime(second, DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	/**
	 * 获得两个String型日期之间相差的天数（第2个减第1个）
	 *
	 * @param String
	 *            first, String second
	 * @return int 相差的天数
	 */
	public static int getDaysBetweenDates(String first, String second) {
		Date d1 = getFormatDateTime(first, DATE_FORMAT);
		Date d2 = getFormatDateTime(second, DATE_FORMAT);

		Long mils = (d2.getTime() - d1.getTime()) / (TIME_DAY_MILLISECOND);

		return mils.intValue();
	}

	/**
	 *
	 *
	 */
	public static String getDateBeforeDay() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
	 *
	 * @see #getFormatDate(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
	 */
	public static String getCurrDateStr() {
		return getFormatDate(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy-MM-dd，如2006-02-15
	 *
	 * @see #getFormatDate(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统日期，格式为yyyy-MM-dd，如2006-02-15
	 */
	public static String getCurrDateStr(String formate) {
		return getFormatDate(getCurrDate(),formate);
	}

	public static String getCurrDayStr() {
		return getFormatDate(getCurrDate(),DAY_FORMAT);
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15 15:23:45
	 *
	 * @see #getFormatDateTime(java.util.Date)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy-MM-dd HH:mm:ss，如2006-02-15
	 *         15:23:45
	 */
	public static String getCurrDateTimeStr() {
		return getFormatDateTime(getCurrDate());
	}

	/**
	 * 得到格式化后的当前系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 *
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回当前服务器系统日期，格式为yyyy年MM月dd日，如2006年02月15日
	 */
	public static String getCurrDateStr_CN() {
		return getFormatDate(getCurrDate(), DATE_FORMAT_CN);
	}

	/**
	 * 得到格式化后的当前系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日 15:23:45
	 *
	 * @see #getFormatDateTime(java.util.Date, String)
	 * @return String 返回格式化后的当前服务器系统时间，格式为yyyy年MM月dd日 HH:mm:ss，如2006年02月15日
	 *         15:23:45
	 */
	public static String getCurrDateTimeStr_CN() {
		return getFormatDateTime(getCurrDate(), TIME_FORMAT_CN);
	}

	/**
	 * 得到系统当前日期的前或者后几天
	 *
	 * @param iDate
	 *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回系统当前日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(int iDate) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}

	/**
	 * 得到日期的前或者后几天
	 *
	 * @param iDate
	 *            如果要获得前几天日期，该参数为负数； 如果要获得后几天日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几天
	 */
	public static Date getDateBeforeOrAfter(Date curDate, int iDate) {
		if(null==curDate){
			return null;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.DAY_OF_MONTH, iDate);
		return cal.getTime();
	}
	
	/**
	 * 得到系统当前日期的前或者后几分钟
	 *
	 * @param iMinute
	 *            如果要获得前几分钟，该参数为负数； 如果要获得后几分钟，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回系统当前日期的前或者后几分钟
	 */
	public static Date getMinuteBeforeOrAfter(int iMinute) {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MINUTE, iMinute);
		return cal.getTime();
	}
	
	/**
	 * 得到日期的前或者后几分钟
	 * @author curDate
	 *            日期
	 * @param iMinute
	 *            如果要获得前几分钟，该参数为负数； 如果要获得后几分钟，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几分钟
	 */
	public static Date getMinuteBeforeOrAfter(Date curDate, int iMinute) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.MINUTE, iMinute);
		return cal.getTime();
	}
	
	/**
	 * 得到格式化后的月份，格式为yyyy-MM，如2006-02
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的月份，格式为yyyy-MM，如2006-02
	 */
	public static String getFormatMonth(Date currDate) {
		return getFormatDate(currDate, MONTH_FORMAT);
	}

	/**
	 * 得到格式化后的日，格式为yyyyMMdd，如20060210
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的日，格式为yyyyMMdd，如20060210
	 */
	public static String getFormatDay(Date currDate) {
		return getFormatDate(currDate, DAY_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的下月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfNextMonth() {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, +1);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月第一天，格式为yyyy-MM-dd，如2006-02-01
	 */
	public static String getFirstDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, firstDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 */
	public static String getLastDayOfMonth(Date currDate) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(currDate);
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 *
	 * @param currDate
	 *            要格式化的日期
	 * @see java.util.Calendar#getMinimum(int)
	 * @see #getFormatDate(java.util.Date, String)
	 * @return String 返回格式化后的当月最后一天，格式为yyyy-MM-dd，如2006-02-28
	 */
	public static String getLastDayOfMonth() {
		Calendar cal = Calendar.getInstance();
		int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
		cal.set(Calendar.DAY_OF_MONTH, lastDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 得到日期的前或者后几小时
	 *
	 * @param iHour
	 *            如果要获得前几小时日期，该参数为负数； 如果要获得后几小时日期，该参数为正数
	 * @see java.util.Calendar#add(int, int)
	 * @return Date 返回参数<code>curDate</code>定义日期的前或者后几小时
	 */
	public static Date getDateBeforeOrAfterHours(Date curDate, int iHour) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(curDate);
		cal.add(Calendar.HOUR_OF_DAY, iHour);
		return cal.getTime();
	}

	/**
	 * 判断日期是否在当前周内
	 *
	 * @param curDate
	 * @param compareDate
	 * @return
	 */
	public static boolean isSameWeek(Date curDate, Date compareDate) {
		if (curDate == null || compareDate == null) {
			return false;
		}

		Calendar calSun = Calendar.getInstance();
		calSun.setTime(getFormatDateToDate(curDate));
		calSun.set(Calendar.DAY_OF_WEEK, 1);

		Calendar calNext = Calendar.getInstance();
		calNext.setTime(calSun.getTime());
		calNext.add(Calendar.DATE, 7);

		Calendar calComp = Calendar.getInstance();
		calComp.setTime(compareDate);
		if (calComp.after(calSun) && calComp.before(calNext)) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * 时间查询时,结束时间的 23:59:59
	 */
	public static String addDateEndfix(String datestring) {
		if ((datestring == null) || datestring.equals("")) {
			return null;
		}
		return datestring + " 23:59:59";
	}

	/**
	 * 返回格式化的日期
	 *
	 * @param datePre
	 *            格式"yyyy-MM-dd HH:mm:ss";
	 * @return
	 */
	public static Date formatEndTime(String datePre) {
		if (datePre == null)
			return null;
		String dateStr = addDateEndfix(datePre);
		return getFormatDateTime(dateStr);
	}

	// date1加上compday天数以后的日期与当前时间比较，如果大于当前时间返回true，否则false
	public static Boolean compareDay(Date date1, int compday) {
		if (date1 == null)
			return false;
		Date dateComp = getDateBeforeOrAfter(date1, compday);
		Date nowdate = new Date();
		if (dateComp.after(nowdate))
			return true;
		else
			return false;
	}

	/**
	 * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。
	 * </li> <li>2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个8位的16进制串。</li>
	 * <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
	 *
	 * @param timespan
	 *            一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 * @return 一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
	 */
	public static String convertBinaryTime2Hex(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String hexStr = Long.toHexString(t);
				if (hexStr.length() < 8) {
					int length = hexStr.length();
					for (int n = 0; n < 8 - length; n++) {
						hexStr = "0" + hexStr;
					}
				}

				ret += hexStr;
				tmp = "";
			}
		}

		return ret;
	}

	/**
	 * 进行时段格式转换，将输入的26位的2进制串转换成48位的二进制串。
	 *
	 * @param timespan
	 *            一个16进制串，每位间以","分割。如："3fffcfff,ffffffff,fffffffc"
	 * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 */
	public static String convertHexTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 16));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}

	/**
	 * 进行时段格式转换，将输入的32位的10进制串转换成48位的二进制串。
	 *
	 * @param timespan
	 *            一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890c"
	 * @return 一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 */
	public static String convertDecTime2Binary(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String tmp = "";
		String ret = "";
		String[] strArr = timespan.split(",");
		for (int i = 0; i < strArr.length; i++) {
			String binStr = Long.toBinaryString(Long.parseLong(strArr[i], 10));
			if (binStr.length() < 32) {
				int length = binStr.length();
				for (int n = 0; n < 32 - length; n++) {
					binStr = "0" + binStr;
				}
			}
			tmp += binStr;
		}

		for (int i = 0; i < 48; i++) {
			ret += tmp.charAt(i * 2);
		}

		return ret;
	}

	/**
	 * 进行时段格式转换，对于输入的48位的01串，将进行如下操作： <li>1.先将输入中每个0变成两个0，每个1变成2个1，形成一个96位的二进制串。
	 * </li> <li>2.将上述的96位的二进制串分成3组，每组32位。</li> <li>3.将每个32位的二进制串转换成一个10位的10进制串。
	 * </li> <li>4.将3个8位的16进制串合并成一个串，中间以","分割。</li>
	 *
	 * @param timespan
	 *            一个48位的二进制串，如："011111111011111111111111111111111111111111111110"
	 * @return 一个16进制串，每位间以","分割。如："1234567890,1234567890,1234567890"
	 */
	public static String convertBinaryTime2Dec(String timespan) {
		if (timespan == null || timespan.equals("")) {
			return "";
		}

		String ret = "";
		String tmp = "";
		for (int i = 0; i < timespan.length(); i++) {
			tmp += timespan.charAt(i);
			tmp += timespan.charAt(i);
			// tmp += i;
			if ((i + 1) % 16 == 0) {
				if (!ret.equals("")) {
					ret += ",";
				}
				Long t = Long.parseLong(tmp, 2);
				String decStr = Long.toString(t);
				if (decStr.length() < 10) {
					int length = decStr.length();
					for (int n = 0; n < 10 - length; n++) {
						decStr = "0" + decStr;
					}
				}

				ret += decStr;
				tmp = "";
			}
		}

		return ret;
	}

	/**
	 * 计算指定日期+addMonth月+15号 返回格式"2008-02-15"
	 *
	 * @param date
	 * @param addMonth
	 * @param monthDay
	 * @return
	 */
	public static String genericSpecdate(Date date, int addMonth, int monthDay) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, addMonth);
		cal.set(Calendar.DAY_OF_MONTH, monthDay);
		return getFormatDate(cal.getTime(), DATE_FORMAT);
	}

	/**
	 * 获得以今天为单位若干天以前或以后的日期的标准格式"Wed Feb 20 00:00:00 CST 2008"，是0点0分0秒。
	 *
	 * @param idx
	 * @return
	 */
	public static Date getDateBeforeOrAfterV2(int idx) {
		return getDateBeforeOrAfter(getFormatDateToDate(getCurrDate()), idx);
	}

	/**
	 * 获得给定时间若干秒以前或以后的日期的标准格式。
	 *
	 * @param curDate
	 * @param seconds
	 * @return curDate
	 */
	public static Date getSpecifiedDateTimeBySeconds(Date curDate, int seconds) {
		long time = (curDate.getTime() / 1000) + seconds;
		curDate.setTime(time * 1000);
		return curDate;
	}

	/**
	 * 获得给定日期当天23点59分59秒的标准格式。
	 *
	 * @param curDate
	 * @return curDate
	 */
	public static Date getSpecifiedDateTime_235959(Date curDate) {
		return getSpecifiedDateTimeBySeconds(getFormatDateToDate(curDate), 24 * 60 * 60 - 1);
	}

	public static String getSpecifiedDateTime_month(Date curDate) {
		return getFormatDateTime(curDate, "MM.dd");
	}
	
	public static String getMonthDay(Date curDate) {
		return getFormatDateTime(curDate, "MM-dd");
	}

	// change by bbq
	public static final String dtSimple = "yyyy-MM-dd";

	/**
	 * 获取传入时间相差几天的日期
	 * 如：2014-10-15 传入整数5 得到2014-10-20 传入负数5 得到2014-10-10
	 * @param dt
	 *            传入日期，可以为空
	 * @param diff
	 *            需要获取相隔diff天的日期 如果为正则取以后的日期，否则时间往前推
	 *
	 * @return
	 */
	public static String getDiffStringDate(Date dt, int diff) {
		Calendar ca = Calendar.getInstance();

		if (dt == null) {
			ca.setTime(new Date());
		} else {
			ca.setTime(dt);
		}

		ca.add(Calendar.DATE, diff);
		return dtSimpleFormat(ca.getTime());
	}

	/**
	 * yyyy-MM-dd
	 *
	 * @param date
	 *
	 * @return
	 */
	public static final String dtSimpleFormat(Date date) {
		if (date == null) {
			return "";
		}

		return getFormat(dtSimple).format(date);
	}

	// SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final DateFormat getFormat(String format) {
		return new SimpleDateFormat(format);
	}
	
	/**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param str1 时间参数 1 格式：1990-01-01 12:00:00
     * @param str2 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分
     */
    public static String getDistanceTime(String str1, String str2) {
        if(str1==null||str2==null){
            return "";
        }
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date one;
        Date two;
        long day = 0;
        long hour = 0;
        long min = 0;
        long sec = 0;
        try {
            one = df.parse(str1);
            two = df.parse(str2);
            long time1 = one.getTime();
            long time2 = two.getTime();
            long diff ;
            if(time1<time2) {
                diff = time2 - time1;
            } else {
                diff = time1 - time2;
            }
            day = diff / (24 * 60 * 60 * 1000);
            hour = (diff / (60 * 60 * 1000) - day * 24);
            min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            sec = (diff/1000-day*24*60*60-hour*60*60-min*60);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        StringBuilder builder=new StringBuilder();
        if(day>0){
            builder.append(day).append("天 "); 
        }
        if(hour>0){
            builder.append(hour).append("小时 "); 
        }
        if(min>0){
            builder.append(min).append("分"); 
        }
        if(sec>0){
        	builder.append(sec).append("秒"); 
        }
        return builder.toString();
    }
    
    
    /**
     * 两个时间相差距离多少天多少小时多少分多少秒
     * @param one 时间参数 1 格式：1990-01-01 12:00:00
     * @param two 时间参数 2 格式：2009-01-01 12:00:00
     * @return String 返回值为：xx天xx小时xx分
     */
    public static String getDistanceTime(Date one, Date two) {
        if(one==null||two==null){
            return "";
        }
        long day = 0;
        long hour = 0;
        long min = 0;
        long time1 = one.getTime();
        long time2 = two.getTime();
        long diff;
        if (time1 < time2) {
            diff = time2 - time1;
        } else {
            diff = time1 - time2;
        }
        day = diff / (24 * 60 * 60 * 1000);
        hour = (diff / (60 * 60 * 1000) - day * 24);
        min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);

        StringBuilder builder = new StringBuilder();
        if (day > 0) {
            builder.append(day).append("天");
        }
        if (hour > 0) {
            builder.append(hour).append("小时");
        }
        if (min > 0) {
            builder.append(min).append("分钟");
        }
        return builder.toString();
    }
    
    /**
	 * 取得当前时间的年月日时分秒字符串		lf12008071058
	 * @return
	 */
	public static String getCurrentTimeStr(){
		return getFormatDate(getCurrDate(), TIME_FORMAT_STRING);
	}
	
	/**
	 * 获取当天时间（只精确到天，不含时分秒）		lf1212141154
	 * @return
	 */
	public static Date getCurrentDay(){
		return getFormatDate(getFormatDate(new Date()));
	}

    /**
     * 获取查询时间间隔时间       lf1403181643
     * @param queryDate
     * @param intervalMinutes
     * @return
     */
    public static Date getTime(Date queryDate, int intervalMinutes)
    {
        if(queryDate != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(queryDate);
            calendar.add(Calendar.MINUTE, intervalMinutes);
            return calendar.getTime();
        }

        return null;
    }

	/**
	 * 获取查询日期的间隔日期		lf1304111755
	 * @param queryDate
	 * @param interval
	 * @return
	 */
	public static Date getDate(Date queryDate, int interval)
	{
		if(queryDate != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(queryDate);
			calendar.add(Calendar.DAY_OF_MONTH, interval);
			return calendar.getTime();
		}

		return null;
	}
	
	/**
	 * 获取查询日期的间隔月份		lf1308261942
	 * @param queryDate
	 * @param interval
	 * @return
	 */
	public static Date getMonth(Date queryDate, int interval)
	{
		if(queryDate != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(queryDate);
			calendar.add(Calendar.MONTH, interval);
			return calendar.getTime();
		}
		
		return null;
	}
	
	/**
	 * 获得两个时间间隔的秒数
	 * @param startDate 开始时间
	 * @param endDate 结束时间
	 * @return
	 */
	public static long getSecond(Date startDate, Date endDate){
		Calendar start = Calendar.getInstance();
		start.setTime(startDate);
		Calendar end = Calendar.getInstance();
		end.setTime(endDate);
		return (end.getTimeInMillis() - start.getTimeInMillis())/1000;
	}
	
	/**
	 * 获取查询日期的星期描述		lf1304111818
	 * @param queryDate
	 * @return
	 */
	public static String getWeedDesc(Date queryDate)
	{
		if(queryDate != null)
		{
			String[] weekDays = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(queryDate);
			int index = calendar.get(Calendar.DAY_OF_WEEK) - 1;
			index = index < 0 ? 0 : index;
			return weekDays[index];
		}
		
		return null;
	}
	
	/**
	 * 获取查询时间起始时间		lf1306011637
	 * @param queryDate
	 * @return
	 */
	public static Date getDateBegin(Date queryDate)
	{
		if(queryDate != null)
		{
			return getFormatDate(getFormatDate(queryDate));
		}
		
		return null;
	}
	
	public static Date getDateBegin()
	{
		return getDateBegin(new Date());
	}
	
	/**
	 * 获取查询时间截止时间		lf1306011639
	 * @param queryDate
	 * @return
	 */
	public static Date getDateEnd(Date queryDate)
	{
		if(queryDate != null)
		{
			return DateUtils.getSpecifiedDateTime_235959(queryDate);
		}
		
		return null;
	}
	
	public static Date getDateEnd()
	{
		return getDateEnd(new Date());
	}
	
	/**
	 * 获取查询日期当月第一天起始时间		lf1306011645
	 * @param queryDate
	 * @return
	 */
	public static Date getFirstDayOfMonthBegin(Date queryDate)
	{
		if(queryDate != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			int firstDay = cal.getMinimum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, firstDay);
			return getDateBegin(cal.getTime());
		}
		
		return null;
	}
	
	public static Date getFirstDayOfMonthBegin()
	{
		return getFirstDayOfMonthBegin(new Date());
	}
	
	/**
	 * 查询查询日期当月最后一天截止时间		lf1306011647
	 * @param queryDate
	 * @return
	 */
	public static Date getLastDayOfMonthEnd(Date queryDate)
	{
		if(queryDate != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			int lastDay = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
			cal.set(Calendar.DAY_OF_MONTH, lastDay);
			return getDateEnd(cal.getTime());
		}
		
		return null;
	}
	
	public static Date getLastDayOfMonthEnd()
	{
		return getLastDayOfMonthEnd(new Date());
	}
	
	/**
	 * 获取查询日期本周第一天起始时间		lf1306011645
	 * @param queryDate
	 * @return
	 */
	public static Date getFirstDayOfWeekBegin(Date queryDate)
	{
		if(queryDate != null)
		{			
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			Calendar firstDay = getADayOfWeek(cal, Calendar.MONDAY);
			return getDateBegin(firstDay.getTime());
		}
		
		return null;
	}
	
	/**
	 * 获取查询日期所在的星期中第几天，周一为第一天，周日为最后一天		lf1307211034
	 * @param day
	 * @param dayOfWeek
	 * @return
	 */
	public static Calendar getADayOfWeek(Calendar day, int dayOfWeek) 
	{  
	    int week = day.get(Calendar.DAY_OF_WEEK);  
	    if (week == dayOfWeek)  
	    {
	    	return day;  
	    }
	    
	    int diffDay = dayOfWeek - week;  
	    if (week == Calendar.SUNDAY) 
	    {  
	        diffDay -= 7;  
	    } 
	    else if (dayOfWeek == Calendar.SUNDAY) 
	    {  
	        diffDay += 7;  
	    }
	    
	    day.add(Calendar.DATE, diffDay);  
	    return day;  
	}  
	
	public static Date getFirstDayOfWeekBegin()
	{
		return getFirstDayOfWeekBegin(new Date());
	}
	
	/**
	 * 查询查询日期本周最后一天截止时间		lf1306011647
	 * @param queryDate
	 * @return
	 */
	public static Date getLastDayOfWeekEnd(Date queryDate)
	{
		if(queryDate != null)
		{
			Calendar cal = Calendar.getInstance();
			cal.setTime(queryDate);
			Calendar lastDay = getADayOfWeek(cal, Calendar.SUNDAY);
			return getDateBegin(lastDay.getTime());
		}
		
		return null;
	}
	
	public static Date getLastDayOfWeekEnd()
	{
		return getLastDayOfWeekEnd(new Date());
	}
	
	/**
	 * 获取月、日格式字符串		lf1312111506
	 * @param date
	 * @return
	 */
	public static String getMonthDayString(Date date)
	{
		if(date != null)
		{
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int m = calendar.get(Calendar.MONTH)+1;
			int d = calendar.get(Calendar.DATE);
			return m+"月"+d+"日";
		}
		
		return "";
	}

    /**
     * 获取年份     lf1403251015
     * @param date
     * @return
     */
    public static int getYearOfDate(Date date)
    {
        if(date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.YEAR);
        }
        return  0;
    }

    /**
     * 获取月份     lf1403251016
     * @param date
     * @return
     */
    public static int getMonthOfDate(Date date)
    {
        if(date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.MONTH)+1;
        }
        return  0;
    }

    /**
     * 获取天      lf1403251016
     * @param date
     * @return
     */
    public static int getDayOfDate(Date date)
    {
        if(date != null)
        {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DATE);
        }
        return  0;
    }
	
	public static void main(String[] args)
	{	
//		System.out.println(getFormatDateTime(getDateBeforeOrAfter(new Date(),15)));
//		System.out.println(getDateBegin(new Date()));
//		System.out.println(getDateEnd(new Date()));
//		System.out.println(getFirstDayOfMonthBegin(new Date()));
//		System.out.println(getLastDayOfMonthEnd(new Date()));
//		System.out.println(getFirstDayOfWeekBegin(new Date()));
//		System.out.println(getLastDayOfWeekEnd(new Date()));
//		System.out.println(getMonth(DateUtils.getCurrDate(), -1));
//        System.out.println(getYearOfDate(DateUtils.getCurrDate()));
//        System.out.println(getMonthOfDate(DateUtils.getCurrDate()));
//        System.out.println(getDayOfDate(DateUtils.getCurrDate()));
//		System.out.println(DateUtils.isInDate(new Date().getTime(),"19:59","20:05")+"---");
//		System.out.println(getFormatDateTime(getMinuteBeforeOrAfter(10)));
		
		//System.out.println(getMinuteBeforeOrAfter(-10));
		//System.out.println(getSecond(getMinuteBeforeOrAfter(-10), new Date()));
	}
	
	/**
	 * 判断时间是否在时间段内
	 * 
	 * @param date
	 *            当前时间 yyyy-MM-dd HH:mm:ss
	 * @param strDateBegin
	 *            开始时间 00:00:00
	 * @param strDateEnd
	 *            结束时间 00:05:00
	 * @return
	 */
//	public static boolean isInDate(Date date, String strDateBegin,
//			String strDateEnd) {
//		
//		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//		String strDate = sdf.format(date);
//		// 截取当前时间时分秒
//		int strDateH = Integer.parseInt(strDate.substring(11, 13));
//		int strDateM = Integer.parseInt(strDate.substring(14, 16));
//		//int strDateS = Integer.parseInt(strDate.substring(17, 19));
//		// 截取开始时间时分秒
//		int strDateBeginH = Integer.parseInt(strDateBegin.substring(0, 2));
//		int strDateBeginMtemp = Integer.parseInt(strDateBegin.substring(3, 5));
//		int strDateBeginM = 0;
//		if(strDateBeginMtemp<10){
//			strDateBeginM = 50+strDateBeginMtemp;
//			strDateBeginH = strDateBeginH-1;
//		}else{
//			strDateBeginM = strDateBeginMtemp-10;
//		}
//		/*if(strDateBeginM >= 60){
//			strDateBeginH = strDateBeginH+1;
//			strDateBeginM = strDateBeginMtemp-60;
//		}else{
//			strDateBeginM = strDateBeginMtemp+10;
//		}*/
//		
//		//int strDateBeginS = Integer.parseInt(strDateBegin.substring(6, 8));
//		// 截取结束时间时分秒
//		int strDateEndH = Integer.parseInt(strDateEnd.substring(0, 2));
//		int strDateEndM = Integer.parseInt(strDateEnd.substring(3, 5));
//		//int strDateEndS = Integer.parseInt(strDateEnd.substring(6, 8));
//		if ((strDateH >= strDateBeginH && strDateH <= strDateEndH)) {// 当前时间小时数在开始时间和结束时间小时数之间
//			if (strDateH == strDateBeginH && strDateM >= strDateBeginM && strDateM <= strDateEndM) {// 当前时间小时数等于开始时间小时数，分钟数在开始和结束之间
//				return true;
//			} else if (strDateH >= strDateBeginH && strDateH == strDateEndH
//					&& strDateM <= strDateEndM) {// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数小等于结束时间分钟数
//				return true;
//			} else {
//				return false;
//			}
//		} else {
//			return false;
//		}
//	}
public static boolean isInDate(final long currTime, String strDateBegin,String strDateEnd) {   //isShift(final long currTime, String[] timeSlot)
        Calendar tempCalendar = Calendar.getInstance();   
        tempCalendar.setTimeInMillis(currTime);   
        String[] tmpArray = strDateBegin.split(":");   
        long startTime, stopTime;   
        tempCalendar.clear(Calendar.HOUR_OF_DAY);   
        tempCalendar.clear(Calendar.MINUTE);   
        tempCalendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(tmpArray[0]));   
        tempCalendar.set(Calendar.MINUTE, Integer.parseInt(tmpArray[1]));   
        startTime = tempCalendar.getTimeInMillis()-600000;
        //System.out.println(DateUtils.getFormatDateTime(new Date(startTime))+"-------");
        tmpArray = strDateEnd.split(":");   
        int stopHour  = Integer.parseInt(tmpArray[0]), stopMinute = Integer.parseInt(tmpArray[1].substring(0, 2)); 
        if (stopHour == 0) {   
            tempCalendar.add(Calendar.DAY_OF_MONTH, 1);   
        }   
        tempCalendar.clear(Calendar.HOUR_OF_DAY);   
        tempCalendar.clear(Calendar.MINUTE);   
        tempCalendar.set(Calendar.HOUR_OF_DAY, stopHour);
        tempCalendar.set(Calendar.MINUTE, stopMinute);   
        stopTime = tempCalendar.getTimeInMillis();   
        return ((startTime < currTime && currTime <= stopTime) ? true : false);   
    }   
	/*else if (strDateH >= strDateBeginH && strDateH == strDateEndH) {// 当前时间小时数大等于开始时间小时数，等于结束时间小时数，分钟数等于结束时间分钟数，秒数小等于结束时间秒数
		return true;
		
	}*/	
/*else if (strDateH >= strDateBeginH && strDateH == strDateEndH
&& strDateM == strDateEndM && strDateS <= strDateEndS) {
return true;
}*/ 
/**
 * 获取指定时间的那天 00:00:00.000 的时间
 * 
 * @param date
 * @return
 */
public static Date dayBegin(final Date date)
{
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.HOUR_OF_DAY, 0);
	c.set(Calendar.MINUTE, 0);
	c.set(Calendar.SECOND, 0);
	c.set(Calendar.MILLISECOND, 0);
	return c.getTime();
}

/**
 * 获取指定时间的那天 23:59:59.999 的时间
 * 
 * @param date
 * @return
 */
public static Date dayEnd(final Date date)
{
	Calendar c = Calendar.getInstance();
	c.setTime(date);
	c.set(Calendar.HOUR_OF_DAY, 23);
	c.set(Calendar.MINUTE, 59);
	c.set(Calendar.SECOND, 59);
	c.set(Calendar.MILLISECOND, 999);
	return c.getTime();
}
}
