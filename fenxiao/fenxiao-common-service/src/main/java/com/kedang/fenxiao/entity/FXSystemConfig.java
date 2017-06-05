package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_system_config")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXSystemConfig implements Serializable
{

	private static final long serialVersionUID = -2681013787603021696L;

	@Id
	@Column(length = 32, name = "systemkey")
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String systemKey;

	@Column(length = 1000, name = "systemvalue")
	private String systemValue;

	@Column(length = 1000)
	private String description;

	public String getSystemKey()
	{
		return systemKey;
	}

	public void setSystemKey(String systemKey)
	{
		this.systemKey = systemKey;
	}

	public String getSystemValue()
	{
		return systemValue;
	}

	public void setSystemValue(String systemValue)
	{
		this.systemValue = systemValue;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

}
