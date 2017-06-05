package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;


public class AwardNotice {
	private BigDecimal price=new BigDecimal(0);//商品单价大于price发布公告
	private Integer compositeIfNotice=0;//合成是否发布公告
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getCompositeIfNotice() {
		return compositeIfNotice;
	}
	public void setCompositeIfNotice(Integer compositeIfNotice) {
		this.compositeIfNotice = compositeIfNotice;
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("price",getPrice())
			.append("compositeIfNotice",getCompositeIfNotice())
			.toString();
	}
	
}
