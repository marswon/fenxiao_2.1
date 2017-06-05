/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.integration.entity;

import java.math.BigDecimal;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.kedang.fenxiao.util.DateUtils;

public class WithdrawApply
{
	public final static int ED = 1;
	public final static int QB = 2;

	public WithdrawApply()
	{
		super();
	}

	private Long id;
	/**
	 * 用户id
	 */
	private Long userId;
	/**
	 * 用户姓名
	 */
	private String userName;
	/**
	 * 系统订单id
	 */
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderCode;
	/**
	 * 提现申请类型 1:额度提现 2:钱包提现
	 */
	private Integer type;
	/**
	 * 金额
	 */
	private java.math.BigDecimal amount;

	/**
	 * 提现手续费
	 */
	private BigDecimal withdrawAmount;
	/**
	 * 可用金额
	 */
	private java.math.BigDecimal availableAmount;
	/**
	 * 提现总额度
	 */
	private java.math.BigDecimal creditAmount;
	/**
	 * 银行编号
	 */
	private String bankCode;

	/**
	 * 银行名
	 */
	private String bankName;

	/**
	 * 卡号
	 */
	private String cardNum;
	/**
	 * 开户行所在省份编号
	 */
	private String provinceCode;
	/**
	 * 开户行所在城市编号
	 */
	private String cityCode;
	/**
	 * 开户行支行名称
	 */
	private String brabankName;
	private String prcptcd;
	/**
	 * 操作员id
	 */
	private Long adminId;

	/**
	 * 状态 0:新申请 1:通过 2:失败
	 */
	private Integer status;

	/**
	 * 审核错误信息
	 */
	private String errMsg;

	/**
	 * 创建时间
	 */
	private java.util.Date createTime;

	/**
	 * 更新时间
	 */
	private java.util.Date updateTime;

	// columns END
	public void setUserId(Long value)
	{
		this.userId = value;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getUserId()
	{
		return this.userId;
	}

	public void setAmount(java.math.BigDecimal value)
	{
		this.amount = value;
	}

	public java.math.BigDecimal getAmount()
	{
		return this.amount;
	}

	public void setStatus(Integer value)
	{
		this.status = value;
	}

	public Integer getStatus()
	{
		return this.status;
	}

	public void setCreateTime(java.util.Date value)
	{
		this.createTime = value;
	}

	public java.util.Date getCreateTime()
	{
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date value)
	{
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime()
	{
		return this.updateTime;
	}

	public String getErrMsg()
	{
		return errMsg;
	}

	public void setErrMsg(String errMsg)
	{
		this.errMsg = errMsg;
	}

	public Long getOrderId()
	{
		return orderId;
	}

	public void setOrderId(Long orderId)
	{
		this.orderId = orderId;
	}

	public String getBankCode()
	{
		return bankCode;
	}

	public void setBankCode(String bankCode)
	{
		this.bankCode = bankCode;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getCardNum()
	{
		return cardNum;
	}

	public void setCardNum(String cardNum)
	{
		this.cardNum = cardNum;
	}

	public String getProvinceCode()
	{
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode)
	{
		this.provinceCode = provinceCode;
	}

	public String getCityCode()
	{
		return cityCode;
	}

	public void setCityCode(String cityCode)
	{
		this.cityCode = cityCode;
	}

	public String getBrabankName()
	{
		return brabankName;
	}

	public void setBrabankName(String brabankName)
	{
		this.brabankName = brabankName;
	}

	public Long getAdminId()
	{
		return adminId;
	}

	public void setAdminId(Long adminId)
	{
		this.adminId = adminId;
	}

	public String getOrderCode()
	{
		return orderCode;
	}

	public void setOrderCode(String orderCode)
	{
		this.orderCode = orderCode;
	}

	public BigDecimal getWithdrawAmount()
	{
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount)
	{
		this.withdrawAmount = withdrawAmount;
	}

	public java.math.BigDecimal getAvailableAmount()
	{
		return availableAmount;
	}

	public void setAvailableAmount(java.math.BigDecimal availableAmount)
	{
		this.availableAmount = availableAmount;
	}

	public java.math.BigDecimal getCreditAmount()
	{
		return creditAmount;
	}

	public void setCreditAmount(java.math.BigDecimal creditAmount)
	{
		this.creditAmount = creditAmount;
	}

	public String getUserName()
	{
		return userName;
	}

	public void setUserName(String userName)
	{
		this.userName = userName;
	}

	public String getPrcptcd()
	{
		return prcptcd;
	}

	public void setPrcptcd(String prcptcd)
	{
		this.prcptcd = prcptcd;
	}

	public Integer getType()
	{
		return type;
	}

	public void setType(Integer type)
	{
		this.type = type;
	}

	public int hashCode()
	{
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public String toString()
	{
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("UserId", getUserId())
				.append("amount", getAmount()).append("Status", getStatus()).append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime()).append("errMsg", getErrMsg()).append("orderId", getOrderId())
				.append("bankCode", getBankCode()).append("bankName", getBankName()).append("cardNum", getCardNum())
				.append("provinceCode", getProvinceCode()).append("cityCode", getCityCode())
				.append("brabankName", getBrabankName()).append("orderCode", getOrderCode())
				.append("withdrawAmount", getWithdrawAmount()).append("AvailableAmount", getAvailableAmount())
				.append("creditAmount", getCreditAmount()).append("userName", getUserName())
				.append("prcptcd", getPrcptcd()).append("type", getType()).toString();
	}

	public boolean equals(Object obj)
	{
		if (obj instanceof WithdrawApply == false)
			return false;
		if (this == obj)
			return true;
		WithdrawApply other = (WithdrawApply) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	public String getCreateTimeString()
	{
		return DateUtils.getFormatDateTime(createTime, "yyyy-MM-dd HH:mm:ss");
	}
}