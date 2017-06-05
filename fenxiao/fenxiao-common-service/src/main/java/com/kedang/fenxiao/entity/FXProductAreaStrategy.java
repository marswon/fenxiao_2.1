package com.kedang.fenxiao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "fx_product_area_strategy")
public class FXProductAreaStrategy extends IdEntity
{

	public FXProductAreaStrategy()
	{
		super();
	}

	public FXProductAreaStrategy(Long id)
	{
		this.id = id;
	}

	@Column(name = "businesstype")
	private int businessType;

	@Column(name = "yystypeid")
	private int yysTypeId;

	@Column(name = "provinceid")
	private String provinceId;

	@Column(name = "areacode")
	private String areaCode;

	@Column(name = "flowtype")
	private int flowType;

	@Column(name = "size")
	private int size;

	@ManyToOne
	@JoinColumn(name = "oproid")
	private FXOperatorsProduct operatorsProduct;

	@Column(name = "channelid", columnDefinition = "int default 0")
	private String channelId;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "updatetime")
	private Date updateTime;

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public int getYysTypeId()
	{
		return yysTypeId;
	}

	public void setYysTypeId(int yysTypeId)
	{
		this.yysTypeId = yysTypeId;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public FXOperatorsProduct getOperatorsProduct()
	{
		return operatorsProduct;
	}

	public void setOperatorsProduct(FXOperatorsProduct operatorsProduct)
	{
		this.operatorsProduct = operatorsProduct;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
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
