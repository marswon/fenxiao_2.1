package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

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

import com.fasterxml.jackson.annotation.JsonFormat;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_founds_flow")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXFoundsFlow implements Serializable
{
	private static final long serialVersionUID = 2154255920754173488L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "amount", columnDefinition = "bigInt default 0")
	private long amount;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;//1订单，2加款

	@Column(name = "flowtype", columnDefinition = "bigInt default 0")
	private long flowType;//1流入，2流出

	@ManyToOne
	@JoinColumn(name = "order_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private FXOrderRecord fxOrderRecord;//订单ID

	@ManyToOne
	@JoinColumn(name = "recharge_id")
	@NotFound(action = NotFoundAction.IGNORE)
	private FXRecharge fxRecharge;//加款记录ID

	@Column(name = "creattime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date creatTime;//创建时间

	@JoinColumn(name = "eid")
	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	private FXEnterprise enterprise;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public long getAmount()
	{
		return amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businesType)
	{
		this.businessType = businesType;
	}

	public FXEnterprise getEnterprise()
	{
		return enterprise;
	}

	public void setEnterprise(FXEnterprise enterprise)
	{
		this.enterprise = enterprise;
	}

	public long getFlowType()
	{
		return flowType;
	}

	public void setFlowType(long flowType)
	{
		this.flowType = flowType;
	}

	public FXOrderRecord getFxOrderRecord()
	{
		return fxOrderRecord;
	}

	public void setFxOrderRecord(FXOrderRecord fxOrderRecord)
	{
		this.fxOrderRecord = fxOrderRecord;
	}

	public FXRecharge getFxRecharge()
	{
		return fxRecharge;
	}

	public void setFxRecharge(FXRecharge fxRecharge)
	{
		this.fxRecharge = fxRecharge;
	}

	public Date getCreatTime()
	{
		return creatTime;
	}

	public void setCreatTime(Date creatTime)
	{
		this.creatTime = creatTime;
	}
}
