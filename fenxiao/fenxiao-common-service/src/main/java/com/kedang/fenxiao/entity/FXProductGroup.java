package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

import com.fasterxml.jackson.annotation.JsonFormat;

@Entity
@Table(name = "fx_product_group")
public class FXProductGroup implements Serializable
{

	private static final long serialVersionUID = -1881510522287370740L;

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "description", nullable = false, length = 100)
	private String description;
	
	@Column(name = "businesstype")
	private Integer businessType;
	
	@Column(name = "create_time", nullable = false, length = 100)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date create_time;

	@Column(name= "selfproducttype" )
	private Integer selfProductType;

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public Date getCreate_time()
	{
		return create_time;
	}

	public void setCreate_time(Date date)
	{
		this.create_time = date;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public Integer getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(Integer businessType)
	{
		this.businessType = businessType;
	}

	public Integer getSelfProductType() {
		return selfProductType;
	}

	public void setSelfProductType(Integer selfProductType) {
		this.selfProductType = selfProductType;
	}
}
