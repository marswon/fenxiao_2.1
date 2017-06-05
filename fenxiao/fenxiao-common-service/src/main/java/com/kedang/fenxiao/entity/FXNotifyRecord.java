package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_notify_record")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXNotifyRecord implements Serializable, Cloneable
{

	private static final long serialVersionUID = -4064082392768083391L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 500, name = "notifyurl")
	private String notifyUrl;

	@Column(length = 100, name = "serialnumber")
	private String serialNumber;

	@Column(length = 100)
	private String orderid;

	@Column(columnDefinition = "int default 0", name = "dowmstreamstatus")
	private int dowmstreamStatus;

	@Column(length = 50, name = "errorcode")
	private String errorCode;

	@Column(length = 1000, name = "errordesc")
	private String errorDesc;

	@Column(columnDefinition = "int default 0", name = "pushtimes")
	private int pushTimes;

	@Column(name = "updatetime")
	private Date updateTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getSerialNumber()
	{
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber)
	{
		this.serialNumber = serialNumber;
	}

	public String getOrderid()
	{
		return orderid;
	}

	public void setOrderid(String orderid)
	{
		this.orderid = orderid;
	}

	public int getDowmstreamStatus()
	{
		return dowmstreamStatus;
	}

	public void setDowmstreamStatus(int dowmstreamStatus)
	{
		this.dowmstreamStatus = dowmstreamStatus;
	}

	public String getErrorCode()
	{
		return errorCode;
	}

	public void setErrorCode(String errorCode)
	{
		this.errorCode = errorCode;
	}

	public String getErrorDesc()
	{
		return errorDesc;
	}

	public void setErrorDesc(String errorDesc)
	{
		this.errorDesc = errorDesc;
	}

	public int getPushTimes()
	{
		return pushTimes;
	}

	public void setPushTimes(int pushTimes)
	{
		this.pushTimes = pushTimes;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

}