package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

/**
 * 分期数配置
 */
public class InstallmentCount {

	//分期数
	private Integer number=0;
	//费率
	private BigDecimal rate=new BigDecimal(0);
	
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	
}
