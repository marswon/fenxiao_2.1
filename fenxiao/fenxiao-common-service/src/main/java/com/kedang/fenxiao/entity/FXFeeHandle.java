package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_fee_handle")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXFeeHandle implements Serializable
{

	private static final long serialVersionUID = 8743013129423376301L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "orderrecordkey", length = 100)
	private String orderRecordkey;

	@Column(name = "amount", columnDefinition = "bigInt default 0")
	private long amount;

	@Column(name = "type", columnDefinition = "int default 0")
	private int type;

	@Column(name = "millisecond", columnDefinition = "bigInt default 0")
	private long millisecond;

	@Column(name = "writetime")
	private Date writeTime;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getOrderRecordkey()
	{
		return orderRecordkey;
	}

	public void setOrderRecordkey(String orderRecordkey)
	{
		this.orderRecordkey = orderRecordkey;
	}

	public long getAmount()
	{
		return amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public long getMillisecond()
	{
		return millisecond;
	}

	public void setMillisecond(long millisecond)
	{
		this.millisecond = millisecond;
	}

	public Date getWriteTime()
	{
		return writeTime;
	}

	public void setWriteTime(Date writeTime)
	{
		this.writeTime = writeTime;
	}

}
