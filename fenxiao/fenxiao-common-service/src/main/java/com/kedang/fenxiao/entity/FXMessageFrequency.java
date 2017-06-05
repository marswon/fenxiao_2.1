package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_message_frequency")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXMessageFrequency implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2839716646490045072L;

	@Id
	@Column(length = 100)
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	private String id;
	@Column(name = "lastsendtime")
	private Date lastSendTime;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public Date getLastSendTime()
	{
		return lastSendTime;
	}

	public void setLastSendTime(Date lastSendTime)
	{
		this.lastSendTime = lastSendTime;
	}

}