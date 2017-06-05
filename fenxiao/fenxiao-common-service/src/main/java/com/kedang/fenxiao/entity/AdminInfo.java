/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "admin_info")
public class AdminInfo extends IdEntity {		

	public AdminInfo(){
		super();
	}

	public AdminInfo(
		Long id
	){
		this.id = id;
	}



    /**
	*登录密码
	*/
    @Column(name="pwd") 	 
	@Length(max=64)
	private String pwd;
    
    
    @Column(name="isworking") 	 
   	private Integer isWorking;
    /**
	*salt
	*/
    @Column(name="salt") 	 
	@Length(max=32)
	private String salt;
	
    /**
	*登录名
	*/
    @Column(name="login_name") 	 
	@Length(max=32)
	private String loginName;
	
    /**
	*描述
	*/
    @Column(name="des") 	 
	@Length(max=128)
	private String des;
	
    /**
	*状态 1:有效
	*/
    @Column(name="status") 	 
	@Max(127)
	private Integer status;
	
    /**
	*最后登陆ip
	*/
    @Column(name="last_login_ip") 	 
	@Length(max=32)
	private String lastLoginIp;
	
    /**
	*最后登陆时间
	*/
    @Column(name="last_login_time") 	 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private java.util.Date lastLoginTime;
	
    /**
	*真实姓名
	*/
    @Column(name="real_name") 	 
	@Length(max=16)
	private String realName;
	
    /**
     * 所属企业id
     */
    @ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "e_id")
	private FXEnterprise fxEnterprise;

	public FXEnterprise getFxEnterprise()
	{
		return fxEnterprise;
	}

	public void setFxEnterprise(FXEnterprise fxEnterprise)
	{
		this.fxEnterprise = fxEnterprise;
	}



	/**
	*手机
	*/
    @Column(name="mphone") 	 
	@Length(max=16)
	private String mphone;
	
    /**
	*创建时间
	*/
    @Column(name="create_time") 	 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private java.util.Date createTime;
	
    /**
	*更新时间
	*/
    @Column(name="update_time") 	 
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private java.util.Date updateTime;
    
    @Column(name="email",nullable = false, length = 32) 	 
	private String email;
	
	
	//columns END

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String email)
	{
		this.email = email;
	}

	public Integer getIsWorking() {
		return isWorking;
	}

	public void setIsWorking(Integer isWorking) {
		this.isWorking = isWorking;
	}

	public void setPwd(String value) {
		this.pwd = value;
	}
	
	public String getPwd() {
		return this.pwd;
	}
	public void setSalt(String value) {
		this.salt = value;
	}
	
	public String getSalt() {
		return this.salt;
	}
	public void setLoginName(String value) {
		this.loginName = value;
	}
	
	public String getLoginName() {
		return this.loginName;
	}
	public void setDes(String value) {
		this.des = value;
	}
	
	public String getDes() {
		return this.des;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setLastLoginIp(String value) {
		this.lastLoginIp = value;
	}
	
	public String getLastLoginIp() {
		return this.lastLoginIp;
	}
	public void setLastLoginTime(java.util.Date value) {
		this.lastLoginTime = value;
	}
	
	public java.util.Date getLastLoginTime() {
		return this.lastLoginTime;
	}
	public void setRealName(String value) {
		this.realName = value;
	}
	
	public String getRealName() {
		return this.realName;
	}
	public void setMphone(String value) {
		this.mphone = value;
	}
	
	public String getMphone() {
		return this.mphone;
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
			.append("Pwd",getPwd())
			.append("Salt",getSalt())
			.append("LoginName",getLoginName())
			.append("Des",getDes())
			.append("Status",getStatus())
			.append("LastLoginIp",getLastLoginIp())
			.append("LastLoginTime",getLastLoginTime())
			.append("RealName",getRealName())
			.append("Mphone",getMphone())
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
		if(obj instanceof AdminInfo == false) return false;
		if(this == obj) return true;
		AdminInfo other = (AdminInfo)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}