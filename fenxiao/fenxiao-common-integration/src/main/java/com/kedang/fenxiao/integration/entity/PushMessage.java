/*
 * Copyright (c) 2012.  Hangzhou Leftbeach, Inc.  All rights reserved.
 */

package com.kedang.fenxiao.integration.entity;

import javax.persistence.Transient;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class PushMessage {		

	public PushMessage(){
		super();
	}

    private Long id;
    private Long version;
    /**
	*消息类型 1:消息 2:奖励 
	*/
	private Integer type;
    /**
     * 消息具体类型
     */
	private Integer detailType;
    /**
	* 接收人id
	*/
	private String recipient;
    
    /**
     * 接收人类型
     */
    private Integer recipientType;
    /**
	*标题
	*/
	private String title;
	
    /**
	*状态 0:新 1:已读
	*/
	private Integer status;
	
    /**
	*发送次数
	*/
	private Integer sendCount;
	
    /**
	*内容
	*/
	private String content;
	
    /**
	*更新时间
	*/
	private java.util.Date updateTime;
	
    /**
	*创建时间
	*/
	private java.util.Date createTime;
	
	//columns END
    //other Field
    @Transient
    private Long badge;
    
    @Transient
    private String sound = "付壹代通知";
	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public Long getVersion()
	{
		return version;
	}

	public void setVersion(Long version)
	{
		this.version = version;
	}

	public void setType(Integer value) {
		this.type = value;
	}
	
	public Integer getType() {
		return this.type;
	}
	public void setRecipient(String value) {
		this.recipient = value;
	}
	
	public String getRecipient() {
		return this.recipient;
	}
	public void setTitle(String value) {
		this.title = value;
	}
	
	public String getTitle() {
		return this.title;
	}
	public void setStatus(Integer value) {
		this.status = value;
	}
	
	public Integer getStatus() {
		return this.status;
	}
	public void setSendCount(Integer value) {
		this.sendCount = value;
	}
	
	public Integer getSendCount() {
		return this.sendCount;
	}
	public void setContent(String value) {
		this.content = value;
	}
	
	public String getContent() {
		return this.content;
	}
	public void setUpdateTime(java.util.Date value) {
		this.updateTime = value;
	}
	
	public java.util.Date getUpdateTime() {
		return this.updateTime;
	}
	public void setCreateTime(java.util.Date value) {
		this.createTime = value;
	}
	
	public java.util.Date getCreateTime() {
		return this.createTime;
	}
	
	public Integer getRecipientType() {
		return recipientType;
	}

	public void setRecipientType(Integer recipientType) {
		this.recipientType = recipientType;
	}

	public Integer getDetailType() {
		return detailType;
	}

	public void setDetailType(Integer detailType) {
		this.detailType = detailType;
	}

	public Long getBadge() {
		return badge;
	}

	public void setBadge(Long badge) {
		this.badge = badge;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public String toString() {
		return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
			.append("Id",getId())
			.append("Type",getType())
			.append("Recipient",getRecipient())
			.append("Title",getTitle())
			.append("Status",getStatus())
			.append("Version",getVersion())
			.append("SendCount",getSendCount())
			.append("Content",getContent())
			.append("UpdateTime",getUpdateTime())
			.append("CreateTime",getCreateTime())
			.toString();
	}
	
	public int hashCode() {
		return new HashCodeBuilder()
			.append(getId())
			.toHashCode();
	}




	
	public boolean equals(Object obj) {
		if(obj instanceof PushMessage == false) return false;
		if(this == obj) return true;
		PushMessage other = (PushMessage)obj;
		return new EqualsBuilder()
			.append(getId(),other.getId())
			.isEquals();
	}


}