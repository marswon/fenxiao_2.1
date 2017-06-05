package com.kedang.fenxiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.Max;
import java.util.Date;

/**
 * 审核纪录表
 * @author gegongxian
 *
 */
@Entity
@Table(name = "fx_wait_validate_record")
public class FXWaitValidateRecord
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "mid")
	private FXEnterprise enterprise;

	@Column(name = "phone")
	private String phone;

	@Column(name = "size")
	private String size;

	@Column(name = "snum")
	private String snum;

	@Column(name = "notifyurl")
	private String notifyUrl;

	@Column(name = "demo")
	private String demo;

	@Column(name = "buynum")
	private String buyNum;

	@Max(5)
	@Column(name = "flowtype")
	private Integer flowType;

	@Max(5)
	@Column(name = "status")
	private Integer status;

	@Max(5)
	@Column(name = "businesstype")
	private Integer businessType;

	@Column(name = "handwork")
	private String handWork;

	@Column(name = "applyoperator")
	private String applyOperator;

	@Column(name = "checkoperator")
	private String checkOperator;

	@Column(name = "description")
	private String description;

	@Column(name = "createtime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;

	@Column(name = "updatetime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateTime;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public FXEnterprise getEnterprise()
	{
		return enterprise;
	}

	public void setEnterprise(FXEnterprise enterprise)
	{
		this.enterprise = enterprise;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getSize()
	{
		return size;
	}

	public void setSize(String size)
	{
		this.size = size;
	}

	public String getSnum()
	{
		return snum;
	}

	public void setSnum(String snum)
	{
		this.snum = snum;
	}

	public String getNotifyUrl()
	{
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl)
	{
		this.notifyUrl = notifyUrl;
	}

	public String getDemo()
	{
		return demo;
	}

	public void setDemo(String demo)
	{
		this.demo = demo;
	}

	public String getBuyNum()
	{
		return buyNum;
	}

	public void setBuyNum(String buyNum)
	{
		this.buyNum = buyNum;
	}

	public Integer getFlowType()
	{
		return flowType;
	}

	public void setFlowType(Integer flowType)
	{
		this.flowType = flowType;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(Integer businessType)
	{
		this.businessType = businessType;
	}

	public String getHandWork()
	{
		return handWork;
	}

	public void setHandWork(String handWork)
	{
		this.handWork = handWork;
	}

	public String getApplyOperator()
	{
		return applyOperator;
	}

	public void setApplyOperator(String applyOperator)
	{
		this.applyOperator = applyOperator;
	}

	public String getCheckOperator()
	{
		return checkOperator;
	}

	public void setCheckOperator(String checkOperator)
	{
		this.checkOperator = checkOperator;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
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
