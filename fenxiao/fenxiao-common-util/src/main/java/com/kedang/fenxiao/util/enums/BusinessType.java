package com.kedang.fenxiao.util.enums;

public enum BusinessType
{
	liuliang(0, "流量"), 
	huafei(1, "话费"),
	wuye(2, "物业缴费"), 
	jiayouka(3, "加油卡");

	public int type;
	public String value;

	private BusinessType(int type, String value)
	{
		this.type = type;
		this.value = value;
	}

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}
	
}
