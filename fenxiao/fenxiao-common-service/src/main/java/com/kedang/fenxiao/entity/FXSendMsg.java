package com.kedang.fenxiao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "fx_send_msg")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXSendMsg
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "sendphone")
	private String sendPhone;//发送手机号

	@Column(name = "tophone")
	private String toPhone;//接收手机号

	@Column(name = "downstreamorderno")
	private String downstreamOrderNo;//下游订单号

	@Column(name = "account")
	private String account;//加油卡账号

	@Column(name="accountsuffix")
	private String accountSuffix;//加油卡后六位
	
	@Column(name = "amount")
	private Long amount;//金额 元

	@Column(name = "commport")
	private String commPort;//发送端口

	@Column(name = "content")
	private String content;//短信内容

	@Column(name = "cami")
	private String cami;//卡密

	@Column(name = "trynum")
	private Integer tryNum;//重试次数

	@Column(name = "success")
	private Integer success;//0成功，1失败，2.充值中 3.提交成功

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "updatetime")
	private Date updateTime;

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getSendPhone()
	{
		return sendPhone;
	}

	public void setSendPhone(String sendPhone)
	{
		this.sendPhone = sendPhone;
	}

	public String getToPhone()
	{
		return toPhone;
	}

	public void setToPhone(String toPhone)
	{
		this.toPhone = toPhone;
	}

	public String getDownstreamOrderNo()
	{
		return downstreamOrderNo;
	}

	public void setDownstreamOrderNo(String downstreamOrderNo)
	{
		this.downstreamOrderNo = downstreamOrderNo;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public Long getAmount()
	{
		return amount;
	}

	public void setAmount(Long amount)
	{
		this.amount = amount;
	}

	public String getCommPort()
	{
		return commPort;
	}

	public void setCommPort(String commPort)
	{
		this.commPort = commPort;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getCami()
	{
		return cami;
	}

	public void setCami(String cami)
	{
		this.cami = cami;
	}

	public Integer getTryNum()
	{
		return tryNum;
	}

	public void setTryNum(Integer tryNum)
	{
		this.tryNum = tryNum;
	}

	public Integer getSuccess()
	{
		return success;
	}

	public void setSuccess(Integer success)
	{
		this.success = success;
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

	public String getAccountSuffix()
	{
		return accountSuffix;
	}

	public void setAccountSuffix(String accountSuffix)
	{
		this.accountSuffix = accountSuffix;
	}

}
