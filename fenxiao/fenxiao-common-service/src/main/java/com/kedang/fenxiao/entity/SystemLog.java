/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "system_log")
public class SystemLog extends IdEntity {		

	public SystemLog(){
		super();
	}

	public SystemLog(
		Long id
	){
		this.id = id;
	}



    /**
	*类型
	*/
    @Column(name="type") 	 
	@Max(9999999999L)
	private Integer type;
	
    /**
	*shopId
	*/
    @Column(name="shop_id") 	 
	@Max(9223372036854775807L)
	private Long shopId;
	
    /**
	*userId
	*/
    @Column(name="user_id") 	 
	@Max(9223372036854775807L)
	private Long userId;
	
    /**
	*des
	*/
    @Column(name="des") 	 
	@Length(max=5120)
	private String des;
	
    /**
	*创建时间
	*/
    @Column(name="create_time") 	 
	
	private java.util.Date createTime;
	
    /**
	*更新时间
	*/
    @Column(name="update_time") 	 
	
	private java.util.Date updateTime;
	
	//columns END

	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setShopId(Long value) {
		this.shopId = value;
	}
	
	public Long getShopId() {
		return this.shopId;
	}
	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setDes(String value) {
		this.des = value;
	}
	
	public String getDes() {
		return this.des;
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
	
	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Type",getType())
			.append("ShopId",getShopId())
			.append("UserId",getUserId())
			.append("Des",getDes())
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
		if(obj instanceof SystemLog == false) return false;
		if(this == obj) return true;
		SystemLog other = (SystemLog)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}