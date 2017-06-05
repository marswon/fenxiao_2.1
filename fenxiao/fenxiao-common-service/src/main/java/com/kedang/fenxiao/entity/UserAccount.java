/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@Table(name = "user_account")
public class UserAccount extends IdEntity {		

	public UserAccount(){
		super();
	}

	public UserAccount(
		Long id
	){
		this.id = id;
	}



    /**
	*userId
	*/
    @Column(name="user_id") 	 
	@Max(9223372036854775807L)
	private Long userId;
	
    /**
	*可用金额
	*/
    @Column(name="available_amount") 	 
	
	private BigDecimal availableAmount;
    /**
  	*钱包金额
  	*/
    @Column(name="wallet_amount") 	 
  	private BigDecimal walletAmount;
  	
    /**
	*帐户状态 0:冻结中 1:可用
	*/
    @Column(name="status") 	 
	private Integer status;
	
    /**
	*冻结金额
	*/
    @Column(name="free_amount") 	 
	private BigDecimal freeAmount;
	
    /**
	*授信额度
	*/
    @Column(name="credit_amount") 	 
	private BigDecimal creditAmount;
	
    /**
	*创建时间
	*/
    @Column(name="create_time") 	 
	private Date createTime;
	
    /**
	*更新时间
	*/
    @Column(name="update_time") 	 
	private java.util.Date updateTime;
	
    /**
     * 总消费金额
     */
    @Transient
    private BigDecimal totalAmount;
    
    /**
     * 总逾期笔数
     */
    @Transient
    private Integer totalOverdue;
	//columns END

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setAvailableAmount(java.math.BigDecimal value) {
		this.availableAmount = value;
	}
	
	public java.math.BigDecimal getAvailableAmount() {
		return this.availableAmount;
	}
	
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setFreeAmount(java.math.BigDecimal value) {
		this.freeAmount = value;
	}
	
	public java.math.BigDecimal getFreeAmount() {
		return this.freeAmount;
	}
	public void setCreditAmount(java.math.BigDecimal value) {
		this.creditAmount = value;
	}
	
	public java.math.BigDecimal getCreditAmount() {
		return this.creditAmount;
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
	
	public java.math.BigDecimal getWalletAmount() {
		return walletAmount;
	}

	public void setWalletAmount(java.math.BigDecimal walletAmount) {
		this.walletAmount = walletAmount;
	}

	public BigDecimal getTotalAmount()
	{
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount)
	{
		this.totalAmount = totalAmount;
	}

	public Integer getTotalOverdue()
	{
		return totalOverdue;
	}

	public void setTotalOverdue(Integer totalOverdue)
	{
		this.totalOverdue = totalOverdue;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("AvailableAmount",getAvailableAmount())
			.append("Status",getStatus())
			.append("FreeAmount",getFreeAmount())
			.append("CreditAmount",getCreditAmount())
			.append("totalAmount", getTotalAmount())
			.append("totalOverdue", getTotalOverdue())
			.append("CreateTime",getCreateTime())
			.append("UpdateTime",getUpdateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}




	
	public boolean equals(Object obj) {
		if(obj instanceof UserAccount == false) return false;
		if(this == obj) return true;
		UserAccount other = (UserAccount)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}