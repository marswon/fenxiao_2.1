package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 类描述：
 * @author: zhuwanlin
 * @date: 2017年4月14日 上午11:08:37 
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_taobao_product")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXTaobaoProduct implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 6009921508963278120L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 50)
	private String spuid;

	@Column(length = 50, name = "biztype")
	private String bizType;

	@Column(length = 50)
	private String supplierid;

	@Column(columnDefinition = "int default 0")
	private int size;

	@Column(columnDefinition = "int default 0", name = "flowtype")
	private int flowType;

	@Column(columnDefinition = "int default 0", name = "presentamount")
	private int presentAmount;
	
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

	public int getPresentAmount()
	{
		return presentAmount;
	}

	public void setPresentAmount(int presentAmount)
	{
		this.presentAmount = presentAmount;
	}

	@Column(columnDefinition = "int default 0")
	private int status;

	@Column(length = 50)
	private String name;

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "modifytime")
	private Date modifyTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSpuid()
	{
		return spuid;
	}

	public void setSpuid(String spuid)
	{
		this.spuid = spuid;
	}

	public String getBizType()
	{
		return bizType;
	}

	public void setBizType(String bizType)
	{
		this.bizType = bizType;
	}

	public String getSupplierid()
	{
		return supplierid;
	}

	public void setSupplierid(String supplierid)
	{
		this.supplierid = supplierid;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getModifyTime()
	{
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime)
	{
		this.modifyTime = modifyTime;
	}

}
