package com.kedang.fenxiao.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
	
	public static Date getDate(String date, String fmt){
		SimpleDateFormat sdf=new SimpleDateFormat(fmt);
		try {
			return sdf.parse(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public static String getDate(Date date, String fmt){
		SimpleDateFormat sdf=new SimpleDateFormat(fmt);
		return sdf.format(date);
	}

	public static String getTodayDate(){
		String today="";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		today=sdf.format(date);
		return today;
	}
	
	public static String getDayDate(){
		String today="";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("dd");
		today=sdf.format(date);
		return today;
	}
	
	public static String getMonthDate(){
		String today="";
		Date date=new Date();
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
		today=sdf.format(date);
		return today;
	}
	
	public static String getMonthDate(int month){
		String today="";
		Date date=new Date();
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.add(Calendar.MONTH, month);
		SimpleDateFormat sdf=new SimpleDateFormat("yyyyMM");
		Date thisMonth=now.getTime();
		today=sdf.format(thisMonth);
		return today;
	}
	
	public static String getDateFormat(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(date);
	}
	
	public static String getDateFormatS(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH.mm.ss");
		return sdf.format(date);
	}
	
	public static String getDateFormatDay(Date date){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(date);
	}
}