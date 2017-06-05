package com.kedang.fenxiao.util.enums;

public enum Relation
{
	ql(1, "情侣"), 
	sy(2, "室友"),
	py(3, "朋友"), 
	tx(4, "同学");
	private Relation(int type, String des)
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
