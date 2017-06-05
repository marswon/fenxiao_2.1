package com.kedang.fenxiao.util.enums;

public enum FoundsFlowEnum
{
	lr(1, "流入"), lc(2, "流出");
	private FoundsFlowEnum(int type, String des)
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
