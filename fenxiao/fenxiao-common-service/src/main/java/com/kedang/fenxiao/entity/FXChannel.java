package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_channel")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXChannel implements Serializable {

	private static final long serialVersionUID = -3017497981481376803L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name="name",nullable = false, length = 100)
	private String name;

	@Column(name="appid",nullable = false, length = 32)
	private String appId;

	@Column(name="autostart",columnDefinition = "int default 0")
	private int autoStart;

	@Column(name="yystypeid",length = 5)
	private String yysTypeId;

	@Column(name="provinceid",length = 5)
	private String provinceId;

	@Column(name="status",columnDefinition = "int default 0")
	private int status;

	@Column(name="extended",nullable = false, length = 2000)
	private String extended;

	@Column(name="javaclasspath",nullable = false, length = 500)
	private String javaClassPath;

	@Column(name="needquery",columnDefinition = "int default 0")
	private int needQuery;

	@Column(name="businesstype",columnDefinition = "int default 0")
	private int businessType;

	@Column(name="discount",columnDefinition = "int default 0")
	private int discount;

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

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public int getAutoStart() {
		return autoStart;
	}

	public void setAutoStart(int autoStart) {
		this.autoStart = autoStart;
	}

	public String getYysTypeId() {
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId) {
		this.yysTypeId = yysTypeId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExtended() {
		return extended;
	}

	public void setExtended(String extended) {
		this.extended = extended;
	}

	public String getJavaClassPath() {
		return javaClassPath;
	}

	public void setJavaClassPath(String javaClassPath) {
		this.javaClassPath = javaClassPath;
	}

	public int getNeedQuery() {
		return needQuery;
	}

	public void setNeedQuery(int needQuery) {
		this.needQuery = needQuery;
	}

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

}
