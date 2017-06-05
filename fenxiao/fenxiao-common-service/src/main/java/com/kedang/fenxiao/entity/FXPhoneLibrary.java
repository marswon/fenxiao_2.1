package com.kedang.fenxiao.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

import javax.persistence.*;

/**
 * 号码库
 * @author gegongxian
 *
 */
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_phone_library")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXPhoneLibrary
{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(length = 50, name = "phone")
	private String phone;//手机号

	@Column(name = "remarks")
	private String remarks;

	@Column(name = "createtime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date createTime;//创建时间

	@Column(name = "updatetime")
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+08:00")
	private Date updateTime;//最后更新时间

	@Column(name = "commport")
	private String commPort;//发送端口

	@Column(name = "tophone")
	private String toPhone;//接收手机号

	@Column(name = "status", columnDefinition = "int default 0")
	private Integer status;//状态 1锁定，0释放

	@Column(name = "phonegroup", columnDefinition = "int default 1")
	private Integer phoneGroup;//1,一组，2，二组 3，3组

    @Transient
    private String sendCountToDay;//当日发送条数

	public Long getId()
	{
		return id;
	}

	public void setId(Long id)
	{
		this.id = id;
	}

	public String getPhone()
	{
		return phone;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public String getRemarks()
	{
		return remarks;
	}

	public void setRemarks(String remarks)
	{
		this.remarks = remarks;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public Date getUpdateTime()
	{
		return updateTime;
	}

	public void setUpdateTime(Date updateTime)
	{
		this.updateTime = updateTime;
	}

	public Integer getStatus()
	{
		return status;
	}

	public void setStatus(Integer status)
	{
		this.status = status;
	}

	public String getCommPort()
	{
		return commPort;
	}

	public void setCommPort(String commPort)
	{
		this.commPort = commPort;
	}

	public String getToPhone()
	{
		return toPhone;
	}

	public void setToPhone(String toPhone)
	{
		this.toPhone = toPhone;
	}

	public Integer getPhoneGroup() {
		return phoneGroup;
	}

	public void setPhoneGroup(Integer phoneGroup) {
		this.phoneGroup = phoneGroup;
	}

    public String getSendCountToDay() {
        return sendCountToDay;
    }

    public void setSendCountToDay(String sendCountToDay) {
        this.sendCountToDay = sendCountToDay;
    }
}
