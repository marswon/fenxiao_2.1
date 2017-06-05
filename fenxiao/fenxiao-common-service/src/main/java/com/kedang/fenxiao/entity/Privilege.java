/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.entity;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Max;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "privilege")
public class Privilege extends IdEntity {		

	public Privilege(){
		super();
	}

	public Privilege(
		Long id
	){
		this.id = id;
	}



    /**
	*名称
	*/
    @Column(name="name") 	 
	@Length(max=64)
	private String text;
	
    /**
	*资源 可以是url等
	*/
    @Column(name="resource") 	 
	@Length(max=512)
	private String resource;
	
    /**
	*权限组id
	*/
    @Column(name="parent_id") 	 
	@Max(127)
	private Long parentId;
	
    /**
	*是否权限组 1:是 0:否
	*/
    @Column(name="is_group") 	 
	@Max(127)
	private Integer isGroup;
	
    /**
	*状态1:有效 2:无效
	*/
    @Column(name="status") 	 
	@Max(127)
	private Integer status;
	
    /**
	*权限编号
	*/
    @Column(name="pri_code") 	 
	@Length(max=64)
	private String priCode;
	
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
    
	@Transient
    private List<Privilege> children;
	//columns END


	public List<Privilege> getChildren() {
		return children;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public void setChildren(List<Privilege> children) {
		this.children = children;
	}


	public void setResource(String value) {
		this.resource = value;
	}
	
	public String getResource() {
		return this.resource;
	}
	
	public Long getParentId() {
		return parentId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setIsGroup(Integer value) {
		this.isGroup = value;
	}
	
	public Integer getIsGroup() {
		return this.isGroup;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setPriCode(String value) {
		this.priCode = value;
	}
	
	public String getPriCode() {
		return this.priCode;
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
			.append("Resource",getResource())
			.append("ParentId",getParentId())
			.append("IsGroup",getIsGroup())
			.append("Status",getStatus())
			.append("PriCode",getPriCode())
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
		if(obj instanceof Privilege == false) return false;
		if(this == obj) return true;
		Privilege other = (Privilege)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}