package com.kedang.fenxiao.integration.bestpay.enums;

public enum CodeType
{
	cg("000", "成功"),
	csh("001", "初始化"), 
	clz("002", "处理中"),
	sb("004","失败"),
	czcg("010","冲正成功"),
	tkcg("021","退款成功"),
	bfcg("025","退款成功");
	private CodeType(String type, String desc)
	{
		this.type = type;
		this.desc = desc;
	}

	private String type;
	private String desc;

	public String getType()
	{
		return type;
	}

	public void setType(String type)
	{
		this.type = type;
	}

	public String getDesc()
	{
		return desc;
	}

	public void setDesc(String desc)
	{
		this.desc = desc;
	}

}
