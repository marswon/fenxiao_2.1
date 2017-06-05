package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

/**
 * 逾期费率配置
 */
public class OverdueRate {
	// 逾期天数范围
	private Integer withinDay=0;
	// 逾期天数范围内费率
	private BigDecimal withinDayRate=new BigDecimal(0);
	// 增加的天数范围
	private Integer increaseDay=0;
	// 增加的费率
	private BigDecimal increaseDayRate=new BigDecimal(0);

	public Integer getWithinDay() {
		return withinDay;
	}

	public void setWithinDay(Integer withinDay) {
		this.withinDay = withinDay;
	}

	public BigDecimal getWithinDayRate() {
		return withinDayRate;
	}

	public void setWithinDayRate(BigDecimal withinDayRate) {
		this.withinDayRate = withinDayRate;
	}

	public Integer getIncreaseDay() {
		return increaseDay;
	}

	public void setIncreaseDay(Integer increaseDay) {
		this.increaseDay = increaseDay;
	}

	public BigDecimal getIncreaseDayRate() {
		return increaseDayRate;
	}

	public void setIncreaseDayRate(BigDecimal increaseDayRate) {
		this.increaseDayRate = increaseDayRate;
	}


}
