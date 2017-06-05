package com.kedang.fenxiao.entity;

import java.io.Serializable;

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
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_pro_opro")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXProductOperatorsProduct implements Serializable
{

	private static final long serialVersionUID = -692230921337663513L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "proid", nullable = false, length = 32)
	private String proId;

	@Column(name = "provinceid", length = 5)
	private String provinceId;

	@Column(name = "yystypeid", length = 5)
	private String yysTypeId;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "oproid")
	private FXOperatorsProduct fxOperatorsProduct;

	public String getProId()
	{
		return proId;
	}

	public void setProId(String proId)
	{
		this.proId = proId;
	}

	public FXOperatorsProduct getFxOperatorsProduct()
	{
		return fxOperatorsProduct;
	}

	public void setFxOperatorsProduct(FXOperatorsProduct fxOperatorsProduct)
	{
		this.fxOperatorsProduct = fxOperatorsProduct;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getProvinceId()
	{
		return provinceId;
	}

	public void setProvinceId(String provinceId)
	{
		this.provinceId = provinceId;
	}

	public String getYysTypeId()
	{
		return yysTypeId;
	}

	public void setYysTypeId(String yysTypeId)
	{
		this.yysTypeId = yysTypeId;
	}

}
