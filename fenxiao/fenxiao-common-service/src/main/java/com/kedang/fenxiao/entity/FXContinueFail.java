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
@Table(name = "fx_continue_fail")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXContinueFail implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = -2839716646490045072L;

	@Id
	@Column(length = 100)
	@GenericGenerator(name = "assigned", strategy = "assigned")
	@GeneratedValue(generator = "assigned")
	private String id;

	@Column(columnDefinition = "int default 0", name = "continuefailcount")
	private int continueFailCount;

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public int getContinueFailCount()
	{
		return continueFailCount;
	}

	public void setContinueFailCount(int continueFailCount)
	{
		this.continueFailCount = continueFailCount;
	}

}