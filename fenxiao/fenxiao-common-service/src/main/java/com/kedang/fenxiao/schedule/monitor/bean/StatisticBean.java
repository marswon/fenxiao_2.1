package com.kedang.fenxiao.schedule.monitor.bean;

import java.math.BigDecimal;
import java.util.Date;

public class StatisticBean
{
	private Date startTime;
	private Date endTime;
	private BigDecimal todayCostMoney;
	private BigDecimal lastMonthTodayCostMoney;
	private double increaseRate;
	private int businessType;
	private int dateType;
	private String businessTypeName;

	public Date getStartTime()
	{
		return startTime;
	}

	public void setStartTime(Date startTime)
	{
		this.startTime = startTime;
	}

	public Date getEndTime()
	{
		return endTime;
	}

	public void setEndTime(Date endTime)
	{
		this.endTime = endTime;
	}

	public double getIncreaseRate()
	{
		return increaseRate;
	}

	public void setIncreaseRate(double increaseRate)
	{
		this.increaseRate = increaseRate;
	}

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public int getDateType()
	{
		return dateType;
	}

	public void setDateType(int dateType)
	{
		this.dateType = dateType;
	}

	public String getBusinessTypeName()
	{
		return businessTypeName;
	}

	public void setBusinessTypeName(String businessTypeName)
	{
		this.businessTypeName = businessTypeName;
	}

	public BigDecimal getTodayCostMoney()
	{
		return todayCostMoney;
	}

	public void setTodayCostMoney(BigDecimal todayCostMoney)
	{
		this.todayCostMoney = todayCostMoney;
	}

	public BigDecimal getLastMonthTodayCostMoney()
	{
		return lastMonthTodayCostMoney;
	}

	public void setLastMonthTodayCostMoney(BigDecimal lastMonthTodayCostMoney)
	{
		this.lastMonthTodayCostMoney = lastMonthTodayCostMoney;
	}

}
