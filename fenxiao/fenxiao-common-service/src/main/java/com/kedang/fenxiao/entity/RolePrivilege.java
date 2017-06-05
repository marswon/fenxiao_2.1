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

@Entity
@Table(name = "role_privilege")
public class RolePrivilege extends IdEntity {		

	public RolePrivilege(){
		super();
	}

	public RolePrivilege(
		Long id
	){
		this.id = id;
	}



    /**
	*roleId
	*/
    @Column(name="role_id") 	 
	@Max(9223372036854775807L)
	private Long roleId;
	
    /**
	*priId
	*/
//    @Column(name="pri_id") 	 
//	@Max(9223372036854775807L)
    @ManyToOne
	@NotFound(action=NotFoundAction.IGNORE)
	@JoinColumn(name="pri_id")
	private Privilege privilege;
	
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

	public void setRoleId(Long value) {
		this.roleId = value;
	}
	
	public Long getRoleId() {
		return this.roleId;
	}

	public Privilege getPrivilege() {
		return privilege;
	}

	public void setPrivilege(Privilege privilege) {
		this.privilege = privilege;
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
			.append("RoleId",getRoleId())
		//	.append("PriId",getPriId())
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
		if(obj instanceof RolePrivilege == false) return false;
		if(this == obj) return true;
		RolePrivilege other = (RolePrivilege)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}