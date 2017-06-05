package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_discount")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXDiscount implements Serializable {

	private static final long serialVersionUID = -7836216843155480607L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name="eid",nullable = false, length = 32)
	private String eId;

	@Column(name="provinceid",length = 5)
	private String provinceId;

	@Column(name="yystypeid",length = 5)
	private String yysTypeId;

	@Column(name="discount",columnDefinition = "int default 0")
	private int discount;

	@Column(name="flowtype",columnDefinition = "int default 0")
	private int flowType;

	@Column(name="size",columnDefinition = "int default 0")
	private int size;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;
	
	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public String getProvinceId() {
		return provinceId;
	}

	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}

	public String getYysTypeId() {
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId) {
		this.yysTypeId = yysTypeId;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getFlowType() {
		return flowType;
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}

	public int getBusinessType()
	{
		return businessType;
	}

	public void setBusinessType(int businessType)
	{
		this.businessType = businessType;
	}
}
