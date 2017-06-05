package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_taobao_shop")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXTaobaoShop implements Serializable
{

	private static final long serialVersionUID = 8743013129423376301L;

	@Id
	@Column(length = 30)
	private String shopid;

	@Column(length = 30, name = "biztype")
	private String bizType;

	@Column(length = 50)
	private String name;

	@Column(columnDefinition = "int default 0")
	private int status;

	@Column(length = 100)
	private String remark;

	@Column(columnDefinition = "int default 0", name = "businesstype")
	private int businessType;

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public String getShopid()
	{
		return shopid;
	}

	public void setShopid(String shopid)
	{
		this.shopid = shopid;
	}

	public String getBizType()
	{
		return bizType;
	}

	public void setBizType(String bizType)
	{
		this.bizType = bizType;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getRemark()
	{
		return remark;
	}

	public void setRemark(String remark)
	{
		this.remark = remark;
	}

}
