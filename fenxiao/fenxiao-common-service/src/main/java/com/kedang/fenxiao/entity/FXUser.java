package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_user")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXUser implements Serializable {

	private static final long serialVersionUID = -2681013787603021696L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="roles",nullable = false, length = 500)
	private String roles;

	@Column(name="eid",nullable = false, length = 32)
	private String eId;

	@Column(name="name",nullable = false, length = 20)
	private String name;

	@Column(name="mobile",nullable = false, length = 11)
	private String mobile;

	@Column(name="email",nullable = false, length = 50)
	private String email;

	@Column(name="account",nullable = false, length = 50)
	private String account;

	@Column(name="password",nullable = false, length = 50)
	private String password;

	@Column(name="status",columnDefinition = "int default 0")
	private int status;
	
	@Column(name = "lastlogintime")
	private Date lastLoginTime;
	
	@Column(name = "createtime")
	private Date createTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRoles() {
		return roles;
	}

	public void setRoles(String roles) {
		this.roles = roles;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getLastLoginTime() {
		return lastLoginTime;
	}

	public void setLastLoginTime(Date lastLoginTime) {
		this.lastLoginTime = lastLoginTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

}
