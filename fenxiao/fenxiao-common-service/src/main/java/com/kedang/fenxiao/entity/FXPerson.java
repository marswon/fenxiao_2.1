package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_person")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXPerson implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2839716646490045072L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50, name = "weichatid")
	private String weichatId;

	@Column(length = 50, name = "name")
	private String name;

	@Column(length = 50, name = "mobile")
	private String mobile;

	@Column(name = "openmessagestatus", columnDefinition = "int default 0")
	private int openMessageStatus;

	@Column(name = "openweichatstatus", columnDefinition = "int default 0")
	private int openWeichatStatus;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;

	@Column(length = 50, name = "workno")
	private String workNo;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getWeichatId()
	{
		return weichatId;
	}

	public void setWeichatId(String weichatId)
	{
		this.weichatId = weichatId;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public int getOpenMessageStatus()
	{
		return openMessageStatus;
	}

	public void setOpenMessageStatus(int openMessageStatus)
	{
		this.openMessageStatus = openMessageStatus;
	}

	public int getOpenWeichatStatus()
	{
		return openWeichatStatus;
	}

	public void setOpenWeichatStatus(int openWeichatStatus)
	{
		this.openWeichatStatus = openWeichatStatus;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getWorkNo()
	{
		return workNo;
	}

	public void setWorkNo(String workNo)
	{
		this.workNo = workNo;
	}

}