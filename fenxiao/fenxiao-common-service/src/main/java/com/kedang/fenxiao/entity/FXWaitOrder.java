package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import com.fasterxml.jackson.annotation.JsonFormat;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_wait_order")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXWaitOrder implements Serializable, Cloneable
{

	private static final long serialVersionUID = -4064082392768083391L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "clientsubmittime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date clientSubmitTime;

	@Column(name = "mobile", nullable = false, length = 20)
	private String mobile;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "downstreamorderno", length = 100)
	private String downstreamOrderNo;

	@Column(name = "size", columnDefinition = "int default 0")
	private int size;

	@Column(name = "proid", nullable = false, length = 32)
	private String proId;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "eid")
	private FXEnterprise fxEnterprise;

	@Column(name = "oproid", nullable = false, length = 32)
	private String oProId;

	@Column(name = "channelid", nullable = false, length = 32)
	private String channelId;

	@Column(name = "discount", columnDefinition = "int default 0")
	private int discount;

	@Column(name = "upstreamdiscount", columnDefinition = "int default 0")
	private int upstreamDiscount;

	@Column(name = "costmoney", columnDefinition = "bigint default 0")
	private long costMoney;

	@Column(name = "beforebalance", columnDefinition = "bigint default 0")
	private long beforeBalance;

	@Column(name = "afterbalance", columnDefinition = "bigint default 0")
	private long afterBalance;

	@Column(name = "receiveservice", length = 100)
	private String receiveService;

	@Column(name = "notifyurl", length = 500)
	private String notifyUrl;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;

	@Column(name = "orderrecordkey", nullable = false, length = 100)
	private String orderRecordkey;

	@Column(name = "iscorrectcostmoney", columnDefinition = "int default 0")
	private int isCorrectCostMoney;
	
	@Column(name = "areacode", length = 10)
	private String areaCode;

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
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

	public Date getClientSubmitTime()
	{
		return clientSubmitTime;
	}

	public void setClientSubmitTime(Date clientSubmitTime)
	{
		this.clientSubmitTime = clientSubmitTime;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
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

	public String getDownstreamOrderNo()
	{
		return downstreamOrderNo;
	}

	public void setDownstreamOrderNo(String downstreamOrderNo)
	{
		this.downstreamOrderNo = downstreamOrderNo;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public String getProId()
	{
		return proId;
	}

	public void setProId(String proId)
	{
		this.proId = proId;
	}

	public FXEnterprise getFxEnterprise()
	{
		return fxEnterprise;
	}

	public void setFxEnterprise(FXEnterprise fxEnterprise)
	{
		this.fxEnterprise = fxEnterprise;
	}

	public String getoProId()
	{
		return oProId;
	}

	public void setoProId(String oProId)
	{
		this.oProId = oProId;
	}

	public String getChannelId()
	{
		return channelId;
	}

	public void setChannelId(String channelId)
	{
		this.channelId = channelId;
	}

	public int getDiscount()
	{
		return discount;
	}

	public void setDiscount(int discount)
	{
		this.discount = discount;
	}

	public long getCostMoney()
	{
		return costMoney;
	}

	public void setCostMoney(long costMoney)
	{
		this.costMoney = costMoney;
	}

	public long getBeforeBalance()
	{
		return beforeBalance;
	}

	public void setBeforeBalance(long beforeBalance)
	{
		this.beforeBalance = beforeBalance;
	}

	public long getAfterBalance()
	{
		return afterBalance;
	}

	public void setAfterBalance(long afterBalance)
	{
		this.afterBalance = afterBalance;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getReceiveService()
	{
		return receiveService;
	}

	public void setReceiveService(String receiveService)
	{
		this.receiveService = receiveService;
	}

	public String getOrderRecordkey()
	{
		return orderRecordkey;
	}

	public void setOrderRecordkey(String orderRecordkey)
	{
		this.orderRecordkey = orderRecordkey;
	}

	public int getIsCorrectCostMoney()
	{
		return isCorrectCostMoney;
	}

	public void setIsCorrectCostMoney(int isCorrectCostMoney)
	{
		this.isCorrectCostMoney = isCorrectCostMoney;
	}

	public int getUpstreamDiscount()
	{
		return upstreamDiscount;
	}

	public void setUpstreamDiscount(int upstreamDiscount)
	{
		this.upstreamDiscount = upstreamDiscount;
	}

}