package com.kedang.fenxiao.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@SuppressWarnings("deprecation")
@Entity
@Table(name = "fx_message")
@org.hibernate.annotations.Entity(dynamicUpdate = true)
public class FXMessage implements Serializable
{

	/**
	 *
	 */
	private static final long serialVersionUID = 3407074082803624728L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	/**
	 *生成时间
	 */
	@Column(name = "createtime")
	private Date createTime;
	/**
	 *消息内容
	 */
	@Column(length = 500)
	private String content;
	/**
	 *接收人
	 */
	@Column(length = 500, name = "messagereceivers")
	private String messageReceivers;

	/**
	 *接收人
	 */
	@Column(length = 500, name = "weichatreceivers")
	private String weichatReceivers;
	/**
	 *状态<br>
	 *0未发送<br>
	 *1已发送<br>
	 */
	@Column(columnDefinition = "int default 0", name = "messagestatus")
	private int messageStatus;

	/**
	 *状态<br>
	 *0未发送<br>
	 *1已发送<br>
	 */
	@Column(columnDefinition = "int default 0", name = "weichatstatus")
	private int weichatStatus;

	/**
	 *消息标题
	 */
	@Column(length = 100)
	private String title;
	/**
	 *创建者
	 */
	@Column(length = 100)
	private String creator;
	/**
	 *发送方式<br>
	 *1短信<br>
	 *2微信<br>
	 */
	@Column(columnDefinition = "int default 1", name = "sendway")
	private int sendWay;

	/**
	 *备注
	 */
	@Column(name = "[desc]", length = 100)
	private String desc;

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public Date getCreateTime()
	{
		return createTime;
	}

	public void setCreateTime(Date createTime)
	{
		this.createTime = createTime;
	}

	public String getContent()
	{
		return content;
	}

	public void setContent(String content)
	{
		this.content = content;
	}

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String title)
	{
		this.title = title;
	}

	public String getCreator()
	{
		return creator;
	}

	public void setCreator(String creator)
	{
		this.creator = creator;
	}

	public int getSendWay()
	{
		return sendWay;
	}

	public void setSendWay(int sendWay)
	{
		this.sendWay = sendWay;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

	public int getMessageStatus()
	{
		return messageStatus;
	}

	public void setMessageStatus(int messageStatus)
	{
		this.messageStatus = messageStatus;
	}

	public int getWeichatStatus()
	{
		return weichatStatus;
	}

	public void setWeichatStatus(int weichatStatus)
	{
		this.weichatStatus = weichatStatus;
	}

	public String getMessageReceivers()
	{
		return messageReceivers;
	}

	public void setMessageReceivers(String messageReceivers)
	{
		this.messageReceivers = messageReceivers;
	}

	public String getWeichatReceivers()
	{
		return weichatReceivers;
	}

	public void setWeichatReceivers(String weichatReceivers)
	{
		this.weichatReceivers = weichatReceivers;
	}

}