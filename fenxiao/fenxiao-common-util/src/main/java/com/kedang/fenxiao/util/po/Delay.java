package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

public class Delay
{
	private BigDecimal delayRate=new BigDecimal(0);//账单延期费率
	private String delayName;//名称
	private Integer delayTimes;//最高延期次数
	private Integer delayType;//表示逾期这个类型：18
	public BigDecimal getDelayRate()
	{
		return delayRate;
	}
	public void setDelayRate(BigDecimal delayRate)
	{
		this.delayRate = delayRate;
	}
	public String getDelayName()
	{
		return delayName;
	}
	public void setDelayName(String delayName)
	{
		this.delayName = delayName;
	}
	public Integer getDelayTimes()
	{
		return delayTimes;
	}
	public void setDelayTimes(Integer delayTimes)
	{
		this.delayTimes = delayTimes;
	}
	public Integer getDelayType()
	{
		return delayType;
	}
	public void setDelayType(Integer delayType)
	{
		this.delayType = delayType;
	}
	
}
