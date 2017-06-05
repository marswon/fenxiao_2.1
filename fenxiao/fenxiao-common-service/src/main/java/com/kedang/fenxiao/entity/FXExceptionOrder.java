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
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_exception_order")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXExceptionOrder implements Serializable
{
	private static final long serialVersionUID = 3062853697431492358L;
	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "clientsubmittime")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date clientSubmitTime;

	@Column(name = "mobile", length = 20)
	private String mobile;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "downstreamorderno", length = 100)
	private String downstreamOrderNo;

	@Column(name = "size", columnDefinition = "int default 0")
	private int size;

	@Column(name = "mid")
	private String mid;

	@Column(name = "midname")
	private String midName;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;

	@Column(name = "flowtype")
	private int flowType;

	@Column(name = "exceptiondesc", length = 500)
	private String exceptionDesc;

	@Column(name = "exceptioncode", length = 20)
	private String exceptionCode;

	@Column(name = "ip", length = 20)
	private String ip;

	@Column(name = "message", length = 2048)
	private String message;

	/**
	 * 0 默认提交异常订单，1黑名单订单，2白名单订单
	 */
	@Column(name = "ordertype", columnDefinition = "int default 0")
	private int orderType;

	@Column(name = "reporttime")
	private Date reportTime;
	
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

	public String getMid()
	{
		return mid;
	}

	public void setMid(String mid)
	{
		this.mid = mid;
	}

	public String getMidName()
	{
		return midName;
	}

	public void setMidName(String midName)
	{
		this.midName = midName;
	}

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	public String getExceptionDesc()
	{
		return exceptionDesc;
	}

	public void setExceptionDesc(String exceptionDesc)
	{
		this.exceptionDesc = exceptionDesc;
	}

	public String getExceptionCode()
	{
		return exceptionCode;
	}

	public void setExceptionCode(String exceptionCode)
	{
		this.exceptionCode = exceptionCode;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String message)
	{
		this.message = message;
	}

	public String getIp()
	{
		return ip;
	}

	public void setIp(String ip)
	{
		this.ip = ip;
	}

	public int getOrderType()
	{
		return orderType;
	}

	public void setOrderType(int orderType)
	{
		this.orderType = orderType;
	}

	public Date getReportTime()
	{
		return reportTime;
	}

	public void setReportTime(Date reportTime)
	{
		this.reportTime = reportTime;
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}
}
