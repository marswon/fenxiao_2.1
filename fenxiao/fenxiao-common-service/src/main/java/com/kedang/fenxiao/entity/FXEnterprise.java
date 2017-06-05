package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fx_enterprise")
public class FXEnterprise implements Serializable {

	private static final long serialVersionUID = -2681013787603021696L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", length = 200)
	private String name;

	@Column(name = "address", length = 1000)
	private String address;

	@Column(name = "balance", columnDefinition = "bigint default 0")
	private Long balance;

	@Column(name = "authip", length = 1000)
	private String authIP;

	@Column(length = 32)
	private String superiorid;

	@Column(name = "mid", length = 200)
	private String mid;

	@Column(name = "privatekey", length = 100)
	private String privateKey;

	@Column(name = "notifyurl", length = 500)
	private String notifyUrl;

	@Column(name = "linkphone", length = 20)
	private String linkPhone;

	@Column(name = "linkman", length = 20)
	private String linkMan;

	@Column(name = "email", length = 50)
	private String email;

	@Column(name = "balancereminder", columnDefinition = "bigint default 0")
	private Long balanceReminder;// 余额提醒

	@Column(name = "credittopbalance", columnDefinition = "bigint default 0")
	private Long creditTopBalance;// 授信上限余额

	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;
	
	@Column(name="islocksnumformat")
	private Integer isLockSnumFormat;
	
	@Column(name="crestvalue",columnDefinition = "int default 0")
	private Integer crestValue;


	public Integer getCrestValue() {
		return crestValue;
	}

	public void setCrestValue(Integer crestValue) {
		this.crestValue = crestValue;
	}

	@Column(name="businesstype")
	private Integer businessType;

	@Column(name="openareastrategy",columnDefinition = "int default 0")
	private Integer openAreaStrategy;//0关闭，1开启地市路由策略

	@Column(name="selfproducttype")
	private Integer selfProductType; //0非自营，1自营
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public Long getBalance() {
		return balance;
	}

	public void setBalance(Long balance) {
		this.balance = balance;
	}

	public String getAuthIP() {
		return authIP;
	}

	public void setAuthIP(String authIP) {
		this.authIP = authIP;
	}

	public String getSuperiorid() {
		return superiorid;
	}

	public void setSuperiorid(String superiorid) {
		this.superiorid = superiorid;
	}

	public String getMid() {
		return mid;
	}

	public void setMid(String mid) {
		this.mid = mid;
	}

	public String getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}

	public String getNotifyUrl() {
		return notifyUrl;
	}

	public void setNotifyUrl(String notifyUrl) {
		this.notifyUrl = notifyUrl;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getLinkPhone() {
		return linkPhone;
	}

	public void setLinkPhone(String linkPhone) {
		this.linkPhone = linkPhone;
	}

	public String getLinkMan() {
		return linkMan;
	}

	public void setLinkMan(String linkMan) {
		this.linkMan = linkMan;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Long getBalanceReminder() {
		return balanceReminder;
	}

	public void setBalanceReminder(Long balanceReminder) {
		this.balanceReminder = balanceReminder;
	}

	public Long getCreditTopBalance() {
		return creditTopBalance;
	}

	public void setCreditTopBalance(Long creditTopBalance) {
		this.creditTopBalance = creditTopBalance;
	}

	public Integer getIsLockSnumFormat()
	{
		return isLockSnumFormat;
	}

	public void setIsLockSnumFormat(Integer isLockSnumFormat)
	{
		this.isLockSnumFormat = isLockSnumFormat;
	}

	public Integer getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(Integer businessType)
	{
		this.businessType = businessType;
	}

	public Integer getOpenAreaStrategy() {
		return openAreaStrategy;
	}

	public void setOpenAreaStrategy(Integer openAreaStrategy) {
		this.openAreaStrategy = openAreaStrategy;
	}

	public Integer getSelfProductType() {
		return selfProductType;
	}

	public void setSelfProductType(Integer selfProductType) {
		this.selfProductType = selfProductType;
	}
}
