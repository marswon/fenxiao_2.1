/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "device")
public class Device extends IdEntity {

	public final static int  DEVICE_TYPE_IOS=2;
	public final static int DEVICE_TYPE_ANDROID=1;

	public Device(){
		super();
	}

	public Device(
		Long id
	){
		this.id = id;
	}



    /**
	*用户id
	*/
    @Column(name="user_id") 	 
	@Max(9223372036854775807L)
	private Long userId;
    /**
     * 用户类型 1:会员 2:商户
     */
    @Column(name="type")
	private Integer type;
    /**
	*token
	*/
    @Column(name="token") 	 
	@Length(max=128)
	private String token;
	
    /**
	*设备类型
	*/
    @Column(name="device_type") 	 
	@Max(9999999999L)
	private Integer deviceType;
	
    /**
	*系统版本
	*/
    @Column(name="os_version") 	 
	@Length(max=32)
	private String osVersion;
	
    /**
	*频道
	*/
    @Column(name="channe_id") 	 
	@Length(max=32)
	private String channeId;
	
    /**
	*标签
	*/
    @Column(name="tag") 	 
	@Length(max=64)
	private String tag;
	
    /**
	*创建时间
	*/
    @Column(name="create_time") 	 
	@NotNull 
	private java.util.Date createTime;
	
    /**
	*更新时间
	*/
    @Column(name="update_time") 	 
	@NotNull 
	private java.util.Date updateTime;
	
	//columns END

	public void setUserId(Long value) {
		this.userId = value;
	}
	
	public Long getUserId() {
		return this.userId;
	}
	public void setToken(String value) {
		this.token = value;
	}
	
	public String getToken() {
		return this.token;
	}
	public void setDeviceType(Integer value) {
		this.deviceType = value;
	}
	
	public Integer getDeviceType() {
		return this.deviceType;
	}
	public void setOsVersion(String value) {
		this.osVersion = value;
	}
	
	public String getOsVersion() {
		return this.osVersion;
	}
	public void setChanneId(String value) {
		this.channeId = value;
	}
	
	public String getChanneId() {
		return this.channeId;
	}
	public void setTag(String value) {
		this.tag = value;
	}
	
	public String getTag() {
		return this.tag;
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
	
	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("UserId",getUserId())
			.append("Token",getToken())
			.append("DeviceType",getDeviceType())
			.append("OsVersion",getOsVersion())
			.append("ChanneId",getChanneId())
			.append("Tag",getTag())
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
		if(obj instanceof Device == false) return false;
		if(this == obj) return true;
		Device other = (Device)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}