package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


/**
 * 提现配置
 */
public class Withdraw {
	private BigDecimal lowestCredits=new BigDecimal(0);//最低额度
	private BigDecimal creditsPercentage=new BigDecimal(0);//额度百分比
	private BigDecimal rate=new BigDecimal(0);//手续费百分比
	private Integer count=0;//每日可提现次数
	public BigDecimal getCreditsPercentage() {
		return creditsPercentage;
	}
	public void setCreditsPercentage(BigDecimal creditsPercentage) {
		this.creditsPercentage = creditsPercentage;
	}
	public BigDecimal getRate() {
		return rate;
	}
	public void setRate(BigDecimal rate) {
		this.rate = rate;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public BigDecimal getLowestCredits() {
		return lowestCredits;
	}
	public void setLowestCredits(BigDecimal lowestCredits) {
		this.lowestCredits = lowestCredits;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("creditsPercentage",getCreditsPercentage())
			.append("rate",getRate())
			.append("count",getCount())
			.append("lowestCredits",getLowestCredits())
			.toString();
	}
	

}
