package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_taobao_order_record")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXTaobaoOrderRecord implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 4575891875964763099L;
	@Id
	@Column(length = 100, name = "tborderno")
	private String tbOrderNo;

	@Column(length = 50, name = "biztype")
	private String bizType;

	@Column(length = 50, name = "supplierid")
	private String supplierid;

	@Column(name = "timestart")
	private Date timeStart;

	@Column(columnDefinition = "int default 0", name = "timelimit")
	private int timeLimit;

	@Column(columnDefinition = "int default 1", name = "buynum")
	private int buyNum;

	@Column(columnDefinition = "int default 0", name = "unitprice")
	private int unitPrice;

	@Column(columnDefinition = "int default 0", name = "totalprice")
	private int totalPrice;

	@Column(columnDefinition = "int default 0", name = "tbprice")
	private int tbPrice;

	@Column(columnDefinition = "int default 0", name = "splitprice")
	private int splitPrice;

	@Column(length = 50, name = "buyerid")
	private String buyerId;

	@Column(length = 50, name = "sellerid")
	private String sellerId;

	@Column(length = 20, name = "buyerip")
	private String buyerIp;

	@Column(length = 20, name = "ts")
	private String ts;

	@Column(length = 128, name = "notifyurl")
	private String notifyUrl;

	@Column(length = 256, name = "properties")
	private String properties;

	@Column(length = 20, name = "cooporderstatus")
	private String coopOrderStatus;

	@Column(columnDefinition = "int default 0", name = "status")
	private int status;

	@Column(length = 200, name = "[desc]")
	private String desc;

	@Column(columnDefinition = "int default 0", name = "handlestatus")
	private int handleStatus;

	@Column(length = 50, name = "spuid")
	private String spuid;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "supplierid", updatable = false, insertable = false)
	private FXTaobaoShop taobaoShop;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "spuid", referencedColumnName = "spuid", updatable = false, insertable = false)
	private FXTaobaoProduct taobaoPro;

	public FXTaobaoShop getTaobaoShop()
	{
		return taobaoShop;
	}

	public void setTaobaoShop(FXTaobaoShop taobaoShop)
	{
		this.taobaoShop = taobaoShop;
	}

	public FXTaobaoProduct getTaobaoPro()
	{
		return taobaoPro;
	}

	public void setTaobaoPro(FXTaobaoProduct taobaoPro)
	{
		this.taobaoPro = taobaoPro;
	}

	public int getHandleStatus()
	{
		return handleStatus;
	}

	public void setHandleStatus(int handleStatus)
	{
		this.handleStatus = handleStatus;
	}

	public String getSpuid()
	{
		return spuid;
	}

	public void setSpuid(String spuid)
	{
		this.spuid = spuid;
	}

	public String getAccountVal()
	{
		return accountVal;
	}

	public void setAccountVal(String accountVal)
	{
		this.accountVal = accountVal;
	}

	@Column(length = 50, name = "accountval")
	private String accountVal;

	@Column(columnDefinition = "int default 0", name = "isjiachong")
	private int isJiaChong;

	@Column(name = "jiachongtime")
	private Date jiaChongTime;

	public Date getJiaChongTime()
	{
		return jiaChongTime;
	}

	public void setJiaChongTime(Date jiaChongTime)
	{
		this.jiaChongTime = jiaChongTime;
	}

	public int getIsJiaChong()
	{
		return isJiaChong;
	}

	public void setIsJiaChong(int isJiaChong)
	{
		this.isJiaChong = isJiaChong;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public String getCoopOrderStatus()
	{
		return coopOrderStatus;
	}

	public void setCoopOrderStatus(String coopOrderStatus)
	{
		this.coopOrderStatus = coopOrderStatus;
	}

	public String getTbOrderNo()
	{
		return tbOrderNo;
	}

	public void setTbOrderNo(String tbOrderNo)
	{
		this.tbOrderNo = tbOrderNo;
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

	public Date getTimeStart()
	{
		return timeStart;
	}

	public void setTimeStart(Date timeStart)
	{
		this.timeStart = timeStart;
	}

	public int getTimeLimit()
	{
		return timeLimit;
	}

	public void setTimeLimit(int timeLimit)
	{
		this.timeLimit = timeLimit;
	}

	public int getBuyNum()
	{
		return buyNum;
	}

	public void setBuyNum(int buyNum)
	{
		this.buyNum = buyNum;
	}

	public int getUnitPrice()
	{
		return unitPrice;
	}

	public void setUnitPrice(int unitPrice)
	{
		this.unitPrice = unitPrice;
	}

	public int getTotalPrice()
	{
		return totalPrice;
	}

	public void setTotalPrice(int totalPrice)
	{
		this.totalPrice = totalPrice;
	}

	public int getTbPrice()
	{
		return tbPrice;
	}

	public void setTbPrice(int tbPrice)
	{
		this.tbPrice = tbPrice;
	}

	public String getBuyerId()
	{
		return buyerId;
	}

	public void setBuyerId(String buyerId)
	{
		this.buyerId = buyerId;
	}

	public String getSellerId()
	{
		return sellerId;
	}

	public void setSellerId(String sellerId)
	{
		this.sellerId = sellerId;
	}

	public String getBuyerIp()
	{
		return buyerIp;
	}

	public void setBuyerIp(String buyerIp)
	{
		this.buyerIp = buyerIp;
	}

	public String getTs()
	{
		return ts;
	}

	public void setTs(String ts)
	{
		this.ts = ts;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getProperties()
	{
		return properties;
	}

	public void setProperties(String properties)
	{
		this.properties = properties;
	}

	public int getSplitPrice()
	{
		return splitPrice;
	}

	public void setSplitPrice(int splitPrice)
	{
		this.splitPrice = splitPrice;
	}

}