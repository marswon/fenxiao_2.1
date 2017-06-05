package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_repeat_recharge")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXRepeatRecharge implements Serializable
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2968170097257358257L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, name = "curchannelid")
	private String curChannelId;

	@Column(length = 50, name = "targetchannelid")
	private String targetChannelId;

	@Column(length = 50, name = "prechannelid")
	private String preChannelId;

	@Column(columnDefinition = "int default 0")
	private int status;

	@Column(columnDefinition = "text", name = "passeid")
	private String passEid;

	@Column(length = 50, name = "yystypeid")
	private String yysTypeId;

	@Column(length = 50, name = "provinceid")
	private String provinceId;

	@Column(columnDefinition = "int default 0", name = "flowtype")
	private int flowType;

	@Column(columnDefinition = "int default 0", name = "ishead")
	private int isHead;

	@Column(columnDefinition = "int default 0", name = "serialnum")
	private int serialNum;

	@Column(columnDefinition = "int default 0", name = "curflowtype")
	private int curFlowType;

	@Column(columnDefinition = "int default 0", name = "targetflowtype")
	private int targetFlowType;

	@Column(columnDefinition = "int default 0", name = "preflowtype")
	private int preFlowType;
	
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "curchannelid", updatable = false, insertable = false)
	private FXChannel curChannel;
	
	public FXChannel getCurChannel()
	{
		return curChannel;
	}

	public void setCurChannel(FXChannel curChannel)
	{
		this.curChannel = curChannel;
	}

	public String getPreChannelId()
	{
		return preChannelId;
	}

	public void setPreChannelId(String preChannelId)
	{
		this.preChannelId = preChannelId;
	}

	public int getPreFlowType()
	{
		return preFlowType;
	}

	public void setPreFlowType(int preFlowType)
	{
		this.preFlowType = preFlowType;
	}

	public int getCurFlowType()
	{
		return curFlowType;
	}

	public void setCurFlowType(int curFlowType)
	{
		this.curFlowType = curFlowType;
	}

	public int getTargetFlowType()
	{
		return targetFlowType;
	}

	public void setTargetFlowType(int targetFlowType)
	{
		this.targetFlowType = targetFlowType;
	}

	public int getSerialNum()
	{
		return serialNum;
	}

	public void setSerialNum(int serialNum)
	{
		this.serialNum = serialNum;
	}

	public int getIsHead()
	{
		return isHead;
	}

	public void setIsHead(int isHead)
	{
		this.isHead = isHead;
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

	public int getFlowType()
	{
		return flowType;
	}

	public void setFlowType(int flowType)
	{
		this.flowType = flowType;
	}

	public String getPassEid()
	{
		return passEid;
	}

	public void setPassEid(String passEid)
	{
		this.passEid = passEid;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getCurChannelId()
	{
		return curChannelId;
	}

	public void setCurChannelId(String curChannelId)
	{
		this.curChannelId = curChannelId;
	}

	public String getTargetChannelId()
	{
		return targetChannelId;
	}

	public void setTargetChannelId(String targetChannelId)
	{
		this.targetChannelId = targetChannelId;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

}