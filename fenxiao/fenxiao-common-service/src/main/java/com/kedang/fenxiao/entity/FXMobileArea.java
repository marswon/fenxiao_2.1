package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fx_mobile_area")
public class FXMobileArea implements Serializable
{

	private static final long serialVersionUID = 5973873349821518026L;

	@Id
	@Column(name = "prefix", length = 11)
	private String prefix;

	@Column(name = "provincename", length = 100)
	private String provinceName;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "areacode", length = 5)
	private String areaCode;

	@Column(name = "areaname", length = 50)
	private String areaName;

	public String getPrefix()
	{
		return prefix;
	}

	public void setPrefix(String prefix)
	{
		this.prefix = prefix;
	}

	public String getProvinceName()
	{
		return provinceName;
	}

	public void setProvinceName(String provinceName)
	{
		this.provinceName = provinceName;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public String getYysTypeId()
	{
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId)
	{
		this.yysTypeId = yysTypeId;
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public String getAreaName()
	{
		return areaName;
	}

	public void setAreaName(String areaName)
	{
		this.areaName = areaName;
	}

}
