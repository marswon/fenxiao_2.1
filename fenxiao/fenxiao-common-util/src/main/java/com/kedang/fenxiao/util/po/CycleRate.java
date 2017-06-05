package com.kedang.fenxiao.util.po;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 还款周期/费率配置
 */
public class CycleRate {
	//账期天数
	private Integer billDay=0;
	//还款天数
	private Integer repayDay=0;
	//逾期费率配置
	private OverdueRate overdueRate;
	
	public Integer getBillDay() {
		return billDay;
	}
	public void setBillDay(Integer billDay) {
		this.billDay = billDay;
	}
	public Integer getRepayDay() {
		return repayDay;
	}
	public void setRepayDay(Integer repayDay) {
		this.repayDay = repayDay;
	}
	public OverdueRate getOverdueRate() {
		return overdueRate;
	}
	public void setOverdueRate(OverdueRate overdueRate) {
		this.overdueRate = overdueRate;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("billDay",getBillDay())
			.append("repayDay",getRepayDay())
			.append("overdueRate",getOverdueRate())
			.toString();
	}
	
}
