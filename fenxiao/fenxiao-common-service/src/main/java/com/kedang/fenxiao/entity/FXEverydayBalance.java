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
@Table(name = "fx_everyday_balance")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXEverydayBalance implements Serializable
{

	private static final long serialVersionUID = 8743013129423376301L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(length = 50, nullable = false, name = "eid")
	private String eId;

	@Column(columnDefinition = "bigInt default 0")
	private long balance;

	@Column(name = "statistictime")
	private Date statisticTime;

	public Integer getId()
	{
		return id;
	}

	public void setId(Integer id)
	{
		this.id = id;
	}

	public String geteId()
	{
		return eId;
	}

	public void seteId(String eId)
	{
		this.eId = eId;
	}

	public long getBalance()
	{
		return balance;
	}

	public void setBalance(long balance)
	{
		this.balance = balance;
	}

	public Date getStatisticTime()
	{
		return statisticTime;
	}

	public void setStatisticTime(Date statisticTime)
	{
		this.statisticTime = statisticTime;
	}

}