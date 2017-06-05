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

public class WithdrawApplyShop {

	public WithdrawApplyShop() {
		super();
	}

	/**
	 * 主键编号
	 */
	private Long id;
	
	/**
	 * 商户id
	 */
	private Long shopId;
	/**
	 * 商户店主id
	 */
	private Long shopManagerId;
	/**
	 * 商户名称
	 */
	private String shopName;
	/**
	 * 开户人姓名
	 */
	private String acctName;
	/**
	 * 系统订单id
	 */
	private Long orderId;
	/**
	 * 订单编号
	 */
	private String orderCode;
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
	/**
	 * 大额行号
	 */
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
	public void setAmount(java.math.BigDecimal value) {
		this.amount = value;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getShopManagerId() {
		return shopManagerId;
	}

	public void setShopManagerId(Long shopManagerId) {
		this.shopManagerId = shopManagerId;
	}

	public String getShopName() {
		return shopName;
	}

	public void setShopName(String shopName) {
		this.shopName = shopName;
	}

	public java.math.BigDecimal getAmount() {
		return this.amount;
	}

	public void setStatus(Integer value) {
		this.status = value;
	}

	public Integer getStatus() {
		return this.status;
	}

	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}

	public java.util.Date getCreateTime() {
		return this.createTime;
	}

	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}

	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}

	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getCardNum() {
		return cardNum;
	}

	public void setCardNum(String cardNum) {
		this.cardNum = cardNum;
	}

	public String getProvinceCode() {
		return provinceCode;
	}

	public void setProvinceCode(String provinceCode) {
		this.provinceCode = provinceCode;
	}

	public String getCityCode() {
		return cityCode;
	}

	public void setCityCode(String cityCode) {
		this.cityCode = cityCode;
	}

	public String getBrabankName() {
		return brabankName;
	}

	public void setBrabankName(String brabankName) {
		this.brabankName = brabankName;
	}

	public Long getAdminId() {
		return adminId;
	}

	public void setAdminId(Long adminId) {
		this.adminId = adminId;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public BigDecimal getWithdrawAmount() {
		return withdrawAmount;
	}

	public void setWithdrawAmount(BigDecimal withdrawAmount) {
		this.withdrawAmount = withdrawAmount;
	}

	public java.math.BigDecimal getAvailableAmount() {
		return availableAmount;
	}

	public void setAvailableAmount(java.math.BigDecimal availableAmount) {
		this.availableAmount = availableAmount;
	}

	public String getPrcptcd() {
		return prcptcd;
	}

	public void setPrcptcd(String prcptcd) {
		this.prcptcd = prcptcd;
	}

	public String getAcctName() {
		return acctName;
	}

	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}

	public Long getShopId() {
		return shopId;
	}

	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("shopManagerId",getShopManagerId())
			.append("shopName",getShopName())
			.append("amount",getAmount())
			.append("status",getStatus())
			.append("createTime",getCreateTime())
			.append("updateTime",getUpdateTime())
			.append("errMsg",getErrMsg())
			.append("orderId",getOrderId())
			.append("bankCode",getBankCode())
			.append("bankName",getBankName())
			.append("cardNum",getCardNum())
			.append("provinceCode",getProvinceCode())
			.append("cityCode",getCityCode())
			.append("brabankName",getBrabankName())
			.append("adminId()",getAdminId())
			.append("orderCode",getOrderCode())
			.append("withdrawAmount",getWithdrawAmount())
			.append("availableAmount",getAvailableAmount())
			.append("prcptcd()",getPrcptcd())
			.append("acctName",getAcctName())
			.append("shopId",getShopId())
			.append("id",getId())
			.toString();
	}

	public boolean equals(Object obj) {
		if (obj instanceof WithdrawApplyShop == false)
			return false;
		if (this == obj)
			return true;
		WithdrawApplyShop other = (WithdrawApplyShop) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}
	public String getCreateTimeString(){
		return DateUtils.getFormatDateTime(createTime, "yyyy-MM-dd HH:mm:ss");
	}
}