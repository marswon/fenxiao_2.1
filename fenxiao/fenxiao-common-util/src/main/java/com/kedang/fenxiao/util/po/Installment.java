package com.kedang.fenxiao.util.po;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 分期付款配置
 */
public class Installment {
	// 最低分期额度
	private BigDecimal minAmount=new BigDecimal(0);
	// 分期数
	private Integer totalNumber=0;
	// 逾期费率配置
	private OverdueRate overdueRate;
	// 分期详情
	private Map<String,InstallmentCount> installmentDetail;
/*	// 分期详情
	private List<InstallmentCount> installmentDetail;*/

	public BigDecimal getMinAmount() {
		return minAmount;
	}

	public void setMinAmount(BigDecimal minAmount) {
		this.minAmount = minAmount;
	}

	public Integer getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(Integer totalNumber) {
		this.totalNumber = totalNumber;
	}

	public OverdueRate getOverdueRate() {
		return overdueRate;
	}

	public void setOverdueRate(OverdueRate overdueRate) {
		this.overdueRate = overdueRate;
	}

	public Map<String, InstallmentCount> getInstallmentDetail() {
		return installmentDetail;
	}

	public void setInstallmentDetail(Map<String, InstallmentCount> installmentDetail) {
		this.installmentDetail = installmentDetail;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("minAmount",getMinAmount())
			.append("totalNumber",getTotalNumber())
			.append("overdueRate",getOverdueRate())
			.append("installmentDetail",getInstallmentDetail())
			.toString();
	}

}
