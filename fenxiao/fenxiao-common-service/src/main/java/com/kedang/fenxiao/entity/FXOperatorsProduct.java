package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_operators_product")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXOperatorsProduct implements Serializable
{

	private static final long serialVersionUID = 8968573507175798373L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "size", columnDefinition = "int default 0")
	private int size;

	@Column(name = "price", columnDefinition = "bigint default 0")
	private long price;

	@Column(name = "flowtype", columnDefinition = "int default 0")
	private int flowType;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@ManyToOne
	@JoinColumn(name = "channelid")
	private FXChannel channel;

	@Column(name = "ordercode", nullable = false, length = 100)
	private String orderCode;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;

	@Column(name = "channeldiscount", columnDefinition = "int default 0")
	private int channelDiscount;

	@Column(name="remarks")
	private String remarks;

	@Transient
	private String productFlowName;
	public String getProductFlowName()
	{
		return productFlowName;
	}

	public void setProductFlowName(String productFlowName)
	{
		this.productFlowName = productFlowName;
	}

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getOrderCode()
	{
		return orderCode;
	}

	public void setOrderCode(String orderCode)
	{
		this.orderCode = orderCode;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public long getPrice()
	{
		return price;
	}

	public void setPrice(long price)
	{
		this.price = price;
	}

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public FXChannel getChannel()
	{
		return channel;
	}

	public void setChannel(FXChannel channel)
	{
		this.channel = channel;
	}

	public String getYysTypeId()
	{
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId)
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

	public int getChannelDiscount()
	{
		return channelDiscount;
	}

	public void setChannelDiscount(int channelDiscount)
	{
		this.channelDiscount = channelDiscount;
	}

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
}
