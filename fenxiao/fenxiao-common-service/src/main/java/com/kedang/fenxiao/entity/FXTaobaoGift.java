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

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_taobao_gift")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXTaobaoGift implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = -6316724162441574379L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(length = 30)
	private String spuid;

	@Column(length = 30, name = "giftspuid")
	private String giftSpuid;

	@Column(length = 50)
	private String supplierid;

	@Column(columnDefinition = "int default 0")
	private int status;

	@Column(name = "activitystarttime")
	private Date activityStartTime;

	@Column(name = "activityendtime")
	private Date activityEndTime;

	@Column(name = "createtime")
	private Date createTime;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "spuid", referencedColumnName = "spuid", updatable = false, insertable = false)
	private FXTaobaoProduct taobaoPro;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "giftspuid", referencedColumnName = "spuid", updatable = false, insertable = false)
	private FXTaobaoProduct giftPro;

	@ManyToOne
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "supplierid", updatable = false, insertable = false)
	private FXTaobaoShop taobaoShop;

	public FXTaobaoProduct getTaobaoPro()
	{
		return taobaoPro;
	}

	public void setTaobaoPro(FXTaobaoProduct taobaoPro)
	{
		this.taobaoPro = taobaoPro;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public FXTaobaoProduct getGiftPro()
	{
		return giftPro;
	}

	public void setGiftPro(FXTaobaoProduct giftPro)
	{
		this.giftPro = giftPro;
	}

	public FXTaobaoShop getTaobaoShop()
	{
		return taobaoShop;
	}

	public void setTaobaoShop(FXTaobaoShop taobaoShop)
	{
		this.taobaoShop = taobaoShop;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getSpuid()
	{
		return spuid;
	}

	public void setSpuid(String spuid)
	{
		this.spuid = spuid;
	}

	public String getGiftSpuid()
	{
		return giftSpuid;
	}

	public void setGiftSpuid(String giftSpuid)
	{
		this.giftSpuid = giftSpuid;
	}

	public String getSupplierid()
	{
		return supplierid;
	}

	public void setSupplierid(String supplierid)
	{
		this.supplierid = supplierid;
	}

	public int getStatus()
	{
		return status;
	}

	public void setStatus(int status)
	{
		this.status = status;
	}

	public Date getActivityStartTime()
	{
		return activityStartTime;
	}

	public void setActivityStartTime(Date activityStartTime)
	{
		this.activityStartTime = activityStartTime;
	}

	public Date getActivityEndTime()
	{
		return activityEndTime;
	}

	public void setActivityEndTime(Date activityEndTime)
	{
		this.activityEndTime = activityEndTime;
	}

}
