package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "fx_product")
public class FXProduct implements Serializable {

	private static final long serialVersionUID = -1881510522287370740L;

	@Id
	@Column(length = 32)
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	private String id;

	@Column(name = "name", nullable = false, length = 100)
	private String name;

	@Column(name = "size", columnDefinition = "int default 0")
	private int size;

	@Column(name = "price", columnDefinition = "bigint default 0")
	private long price;

	@Column(name = "flowtype", columnDefinition = "int default 0")
	private int flowType;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@Column(name = "status", columnDefinition = "int default 0")
	private int status;

	@Column(name = "ordercode", nullable = false, length = 50)
	private String orderCode;
	@Column(name = "createtime")
	private Date createTime;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "businesstype", columnDefinition = "int default 0")
	private int businessType;

	@ManyToOne
	@JoinColumn(name="groupid")
	private FXProductGroup fxProductGroup;
	
	@Transient
	private String productFlowName;
	
	@Transient
	private String productGroup_id;

	@Column(name="selfproducttype")
	private Integer selfProductType;//1自营，0 非自营

	public int getBusinessType() {
		return businessType;
	}

	public void setBusinessType(int businessType) {
		this.businessType = businessType;
	}

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

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	public int getFlowType() {
		return flowType;
	}

	public void setFlowType(int flowType) {
		this.flowType = flowType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getOrderCode() {
		return orderCode;
	}

	public void setOrderCode(String orderCode) {
		this.orderCode = orderCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
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

	public String getProductFlowName() {
		return productFlowName;
	}

	public void setProductFlowName(String productFlowName) {
		this.productFlowName = productFlowName;
	}

	public FXProductGroup getFxProductGroup()
	{
		return fxProductGroup;
	}

	public void setFxProductGroup(FXProductGroup fxProductGroup)
	{
		this.fxProductGroup = fxProductGroup;
	}

	public String getProductGroup_id()
	{
		return productGroup_id;
	}

	public void setProductGroup_id(String productGroup_id)
	{
		this.productGroup_id = productGroup_id;
	}

	public Integer getSelfProductType() {
		return selfProductType;
	}

	public void setSelfProductType(Integer selfProductType) {
		this.selfProductType = selfProductType;
	}
}
