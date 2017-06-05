package com.kedang.fenxiao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * 卡密
 * @author gegongxian
 *
 */
@Entity
@Table(name = "fx_cami")
public class FXCami
{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "camistr", length = 20)
	private String camiStr;

	@Column(name = "camistrcard", length = 25)
	private String camiStrCard;

	@Column(name = "flowtype")
	@Max(5)
	private Integer flowType;

	@Column(name = "size")
	private Integer size;

	@Column(name = "status")
	@Max(5)
	private Integer status;

	@Column(name="businesstype")
	@Max(5)
	private Integer businessType;

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

	public String getCamiStr()
	{
		return camiStr;
	}

	public void setCamiStr(String camiStr)
	{
		this.camiStr = camiStr;
	}

	public Integer getFlowType()
	{
		return flowType;
	}

	public void setFlowType(Integer flowType)
	{
		this.flowType = flowType;
	}

	public Integer getSize()
	{
		return size;
	}

	public void setSize(Integer size)
	{
		this.size = size;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public Integer getBusinessType() {
		return businessType;
	}

	public void setBusinessType(Integer businessType) {
		this.businessType = businessType;
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

	public String getCamiStrCard() {
		return camiStrCard;
	}

	public void setCamiStrCard(String camiStrCard) {
		this.camiStrCard = camiStrCard;
	}
}
