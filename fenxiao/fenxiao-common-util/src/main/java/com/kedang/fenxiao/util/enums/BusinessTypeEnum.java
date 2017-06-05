package com.kedang.fenxiao.util.enums;

public enum BusinessTypeEnum
{
	bill(1, "订单"), addBalance(2, "加款");
	private BusinessTypeEnum(int type, String des)
	{
		this.type = type;
		this.des = des;
	}

	private int type;
	private String des;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getDes()
	{
		return des;
	}

	public void setDes(String des)
	{
		this.des = des;
	}

}
