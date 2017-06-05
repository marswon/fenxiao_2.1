package com.kedang.fenxiao.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "v_fx_enterprise_product_discount")
public class v_fx_enterprise_product_discount implements Serializable{
	private static final long serialVersionUID = -5464833626401517581L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "eid", nullable = false, length = 100)
	private String eId;
	
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@Column(name = "provinceid", length = 5)
	private String provinceId;
	
	@Column(name = "flowtype", columnDefinition = "int default 0")
	private String flowType;
	
	@Column(name = "businesstype", columnDefinition = "int default 0")
	private String businessType;
	
	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;
	
	@Column(name = "size", columnDefinition = "int default 0")
	private String size;
	
	@Column(name="discount",columnDefinition = "int default 0")
	private String discount;
	
//	@Column(name = "demo", nullable = false, length = 100)
//	private String demo;

	@Column(name = "status", columnDefinition = "int default 0")
	private String status;
	public String geteId() {
		return eId;
	}
	public void seteId(String eId) {
		this.eId = eId;
	}
//	public String getDemo() {
//		return demo;
//	}
//
//	public void setDemo(String demo) {
//		this.demo = demo;
//	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getProvinceId() {
		return provinceId;
	}
	public void setProvinceId(String provinceId) {
		this.provinceId = provinceId;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getYysTypeId() {
		return yysTypeId;
	}
	public void setYysTypeId(String yysTypeId) {
		this.yysTypeId = yysTypeId;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	public String getDiscount() {
		return discount;
	}
	public void setDiscount(String discount) {
		this.discount = discount;
	}
}
