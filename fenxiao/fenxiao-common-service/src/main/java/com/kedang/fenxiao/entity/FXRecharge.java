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

import com.fasterxml.jackson.annotation.JsonFormat;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_recharge")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXRecharge implements Serializable, Cloneable
{

	private static final long serialVersionUID = -2627720967178369064L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne
	@JoinColumn(name = "eid", updatable = false, insertable = false, nullable = true)
	private FXEnterprise fxEnterprise;

	@Column(name = "eid", nullable = false)
	private String eId;

	public FXEnterprise getFxEnterprise()
	{
		return fxEnterprise;
	}

	public void setFxEnterprise(FXEnterprise fxEnterprise)
	{
		this.fxEnterprise = fxEnterprise;
	}

	@Column(name = "type", columnDefinition = "int default 0")
	private int type;

	@Column(name = "bankname", nullable = true, length = 100)
	private String bankName;

	@Column(name = "account", nullable = false, length = 100)
	private String account;

	@Column(name = "accountname", nullable = false, length = 100)
	private String accountName;

	@Column(name = "amount", columnDefinition = "bigint default 0")
	private long amount;

	@Column(name = "submittime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date submitTime;

	@ManyToOne
	@JoinColumn(name = "submituserid")
	private AdminInfo adminInfo;

	@Column(name = "description", nullable = false, length = 500)
	private String description;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;

	@Column(name = "confirmtime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date confirmTime;

	@ManyToOne
	@JoinColumn(name = "confirmuserid")
	private AdminInfo confirmUser;

	@Column(name = "beforerechargebalance", columnDefinition = "bigint default 0")
	private long beforeRechargeBalance;

	@Column(name = "afterrechargebalance", columnDefinition = "bigint default 0")
	private long afterRechargeBalance;

	@Column(name = "bankaccountid", nullable = false, length = 32)
	private String bankAccountId;

	/**
	 * 债务，单位：厘
	 */
	@Column(columnDefinition = "bigInt default 0")
	private long debt;

	/**
	 * 加款类型1加款2授信
	 */
	@Column(columnDefinition = "int default 1", name = "addbalancetype")
	private int addBalanceType;

	//	@Override
	//	protected FXRecharge clone() throws CloneNotSupportedException
	//	{
	//		return (FXRecharge) super.clone();
	//	}
	public FXRecharge clone() throws CloneNotSupportedException
	{
		return (FXRecharge) super.clone();
	}

	public long getDebt()
	{
		return debt;
	}

	public void setDebt(long debt)
	{
		this.debt = debt;
	}

	public int getAddBalanceType()
	{
		return addBalanceType;
	}

	public void setAddBalanceType(int addBalanceType)
	{
		this.addBalanceType = addBalanceType;
	}

	public long getAmount()
	{
		return amount;
	}

	public void setAmount(long amount)
	{
		this.amount = amount;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String description)
	{
		this.description = description;
	}

	public long getAfterRechargeBalance()
	{
		return afterRechargeBalance;
	}

	public void setAfterRechargeBalance(long afterRechargeBalance)
	{
		this.afterRechargeBalance = afterRechargeBalance;
	}

	public long getBeforeRechargeBalance()
	{
		return beforeRechargeBalance;
	}

	public void setBeforeRechargeBalance(long beforeRechargeBalance)
	{
		this.beforeRechargeBalance = beforeRechargeBalance;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getBankName()
	{
		return bankName;
	}

	public void setBankName(String bankName)
	{
		this.bankName = bankName;
	}

	public String getAccount()
	{
		return account;
	}

	public void setAccount(String account)
	{
		this.account = account;
	}

	public String getAccountName()
	{
		return accountName;
	}

	public void setAccountName(String accountName)
	{
		this.accountName = accountName;
	}

	public Date getSubmitTime()
	{
		return submitTime;
	}

	public void setSubmitTime(Date submitTime)
	{
		this.submitTime = submitTime;
	}

	public AdminInfo getAdminInfo()
	{
		return adminInfo;
	}

	public void setAdminInfo(AdminInfo adminInfo)
	{
		this.adminInfo = adminInfo;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getConfirmTime()
	{
		return confirmTime;
	}

	public void setConfirmTime(Date confirmTime)
	{
		this.confirmTime = confirmTime;
	}

	public AdminInfo getConfirmUser()
	{
		return confirmUser;
	}

	public void setConfirmUser(AdminInfo confirmUser)
	{
		this.confirmUser = confirmUser;
	}

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getBankAccountId()
	{
		return bankAccountId;
	}

	public void setBankAccountId(String bankAccountId)
	{
		this.bankAccountId = bankAccountId;
	}

	public String geteId()
	{
		return eId;
	}

	public void seteId(String eId)
	{
		this.eId = eId;
	}

}