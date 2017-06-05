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

@Entity
@Table(name = "customer_config")
public class CustomerConfig extends IdEntity {		

	public CustomerConfig(){
		super();
	}

	public CustomerConfig(
		Long id
	){
		this.id = id;
	}



    /**
	*类型 1:还款周期/费率配置 2:逾期账单提醒配置 3:分期付款费率配置 
	*/
    @Column(name="type") 	 
	@Max(9999999999L)
	private Integer type;
	
    /**
	*配置内容 json格式的数据
	*/
    @Column(name="config_exp") 	 
	private String configExp;
	
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
	public void setConfigExp(String value) {
		this.configExp = value;
	}
	
	public String getConfigExp() {
		return this.configExp;
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
			.append("ConfigExp",getConfigExp())
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
		if(obj instanceof CustomerConfig == false) return false;
		if(this == obj) return true;
		CustomerConfig other = (CustomerConfig)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}