/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "admin_role")
public class AdminRole extends IdEntity {

	public AdminRole() {
		super();
	}

	public AdminRole(Long id) {
		this.id = id;
	}

	/**
	 * 管理员id
	 */
	// @Column(name="admin_id")
	// @Max(9223372036854775807L)
	// private Long adminId;
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "user_id")
	private AdminInfo adminInfo;
	/**
	 * 角色id
	 */
	// @Column(name="role_id")
	// @Max(9223372036854775807L)
	// private Long roleId;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "role_id")
	private FXRole role;
	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private java.util.Date createTime;

	/**
	 * 更新时间
	 */
	@Column(name = "update_time")
	private java.util.Date updateTime;

	
	public FXRole getRole() {
		return role;
	}

	public void setRole(FXRole role) {
		this.role = role;
	}

	public AdminInfo getAdminInfo()
	{
		return adminInfo;
	}

	public void setAdminInfo(AdminInfo adminInfo)
	{
		this.adminInfo = adminInfo;
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
		return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
				.append("Id", getId())
				.append("CreateTime", getCreateTime())
				.append("UpdateTime", getUpdateTime()).toString();
	}

	public int hashCode() {
		return new HashCodeBuilder().append(getId()).toHashCode();
	}

	public boolean equals(Object obj) {
		if (obj instanceof AdminRole == false)
			return false;
		if (this == obj)
			return true;
		AdminRole other = (AdminRole) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

}