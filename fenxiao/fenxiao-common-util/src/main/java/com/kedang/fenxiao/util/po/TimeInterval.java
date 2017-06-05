package com.kedang.fenxiao.util.po;

import java.util.Date;

import com.kedang.fenxiao.util.DateUtils;

public class TimeInterval {
	private String start;//开始时间，时分例如10:20
	private String end;//结束时间，时分例如10:20
	private Integer last;//持续时间，分钟为单位
	
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getEnd() {
		return end;
	}
	public void setEnd(String end) {
		this.end = end;
	}
	public Integer getLast() {
		return last;
	}
	public void setLast(Integer last) {
		this.last = last;
	}
	public Date getFullStartTime(){
		return DateUtils.getFormatDateTime(DateUtils.getFormatDate(new Date(), "yyyy-MM-dd")+" "+start+":00");
	}
	public Date getFullEndTime(){
		return DateUtils.getFormatDateTime(DateUtils.getFormatDate(new Date(), "yyyy-MM-dd")+" "+end+":00");
	}
}
