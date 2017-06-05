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

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_order_record")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXOrderRecord implements Serializable
{

	private static final long serialVersionUID = -4064082392768083391L;

	@Id
	@Column(name = "id", length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "clientsubmittime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date clientSubmitTime;

	@Column(name = "systemsubmittime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date systemSubmitTime;

	@Column(name = "reporttime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date reportTime;

	@Column(name = "mobile", nullable = false, length = 20)
	private String mobile;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "downstreamorderno", length = 100)
	private String downstreamOrderNo;

	@Column(name = "upstreamorderno", length = 100)
	private String upstreamOrderNo;

	@Column(name = "size", columnDefinition = "int default 0")
	private int size;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "proid", nullable = false)
	private FXProduct fxProduct;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "eid")
	private FXEnterprise fxEnterprise;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "oproid")
	private FXOperatorsProduct operatorProduct;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "channelid")
	private FXChannel fxChannel;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "downstreamorderno", updatable = false, insertable = false)
	private FXTaobaoOrderRecord fxTaobaoOrderRecord;

	@Column(name = "downstreamstatus", columnDefinition = "int default 0")
	private int downstreamStatus;

	@Column(name = "upstreamstatus", columnDefinition = "int default 0")
	private int upstreamStatus;

	@Column(name = "errorcode", length = 50)
	private String errorCode;

	@Column(name = "errordesc", length = 1000)
	private String errorDesc;

	@Column(name = "discount", columnDefinition = "int default 0")
	private int discount;

	@Column(name = "costmoney", columnDefinition = "bigint default 0")
	private long costMoney;

	@Column(name = "beforebalance", columnDefinition = "bigint default 0")
	private long beforeBalance;

	@Column(name = "afterbalance", columnDefinition = "bigint default 0")
	private long afterBalance;

	@Column(name = "notifyurl", length = 500)
	private String notifyUrl;

	@Column(name = "receiveservice", length = 100)
	private String receiveService;

	@Column(name = "submitservice", length = 100)
	private String submitService;

	@Column(name = "reportservice", length = 100)
	private String reportService;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;

	@Column(name = "orderrecordkey", nullable = false, length = 100)
	private String orderRecordkey;

	@Column(name = "isnormal", columnDefinition = "int default 0")
	private int isNormal;

	@Column(name = "handwork", columnDefinition = "int default 0")
	private int handWork;

	@Column(name = "upstreamdiscount", columnDefinition = "int default 0")
	private int upstreamDiscount;

	@Column(name = "areacode", length = 10)
	private String areaCode;

	@Column(name = "repeatrechargecount", columnDefinition = "int default 0")
	private int repeatRechargeCount;

	@Column(name = "demo")
	private String demo;

	public FXTaobaoOrderRecord getFxTaobaoOrderRecord()
	{
		return fxTaobaoOrderRecord;
	}

	public void setFxTaobaoOrderRecord(FXTaobaoOrderRecord fxTaobaoOrderRecord)
	{
		this.fxTaobaoOrderRecord = fxTaobaoOrderRecord;
	}

	public int getRepeatRechargeCount()
	{
		return repeatRechargeCount;
	}

	public void setRepeatRechargeCount(int repeatRechargeCount)
	{
		this.repeatRechargeCount = repeatRechargeCount;
	}

	public String getAreaCode()
	{
		return areaCode;
	}

	public void setAreaCode(String areaCode)
	{
		this.areaCode = areaCode;
	}

	public int getIsNormal()
	{
		return isNormal;
	}

	public void setIsNormal(int isNormal)
	{
		this.isNormal = isNormal;
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

	public Date getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(Date reportTime)
	{
		this.reportTime = reportTime;
	}

	public String getMobile()
	{
		return mobile;
	}

	public void setMobile(String mobile)
	{
		this.mobile = mobile;
	}

	public Date getSystemSubmitTime()
	{
		return systemSubmitTime;
	}

	public void setSystemSubmitTime(Date systemSubmitTime)
	{
		this.systemSubmitTime = systemSubmitTime;
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

	public String getUpstreamOrderNo()
	{
		return upstreamOrderNo;
	}

	public void setUpstreamOrderNo(String upstreamOrderNo)
	{
		this.upstreamOrderNo = upstreamOrderNo;
	}

	public int getSize()
	{
		return size;
	}

	public void setSize(int size)
	{
		this.size = size;
	}

	public FXProduct getFxProduct()
	{
		return fxProduct;
	}

	public void setFxProduct(FXProduct fxProduct)
	{
		this.fxProduct = fxProduct;
	}

	public FXEnterprise getFxEnterprise()
	{
		return fxEnterprise;
	}

	public void setFxEnterprise(FXEnterprise fxEnterprise)
	{
		this.fxEnterprise = fxEnterprise;
	}

	public FXOperatorsProduct getOperatorProduct()
	{
		return operatorProduct;
	}

	public void setOperatorProduct(FXOperatorsProduct operatorProduct)
	{
		this.operatorProduct = operatorProduct;
	}

	public FXChannel getFxChannel()
	{
		return fxChannel;
	}

	public void setFxChannel(FXChannel fxChannel)
	{
		this.fxChannel = fxChannel;
	}

	public int getDownstreamStatus()
	{
		return downstreamStatus;
	}

	public void setDownstreamStatus(int downstreamStatus)
	{
		this.downstreamStatus = downstreamStatus;
	}

	public int getUpstreamStatus()
	{
		return upstreamStatus;
	}

	public void setUpstreamStatus(int upstreamStatus)
	{
		this.upstreamStatus = upstreamStatus;
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

	public String getSubmitService()
	{
		return submitService;
	}

	public void setSubmitService(String submitService)
	{
		this.submitService = submitService;
	}

	public String getReportService()
	{
		return reportService;
	}

	public void setReportService(String reportService)
	{
		this.reportService = reportService;
	}

	public String getOrderRecordkey()
	{
		return orderRecordkey;
	}

	public void setOrderRecordkey(String orderRecordkey)
	{
		this.orderRecordkey = orderRecordkey;
	}

	public int getHandWork()
	{
		return handWork;
	}

	public void setHandWork(int handWork)
	{
		this.handWork = handWork;
	}

	public int getUpstreamDiscount()
	{
		return upstreamDiscount;
	}

	public void setUpstreamDiscount(int upstreamDiscount)
	{
		this.upstreamDiscount = upstreamDiscount;
	}

	public String getDemo()
	{
		return demo;
	}

	public void setDemo(String demo)
	{
		this.demo = demo;
	}
}