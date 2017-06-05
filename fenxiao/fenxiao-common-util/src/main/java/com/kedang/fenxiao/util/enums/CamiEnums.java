package com.kedang.fenxiao.util.enums;

public enum CamiEnums
{
	wsy(0, "未使用"), ysy(1, "已使用"), wx(2, "无效"),czz(3,"充值中");

	private CamiEnums(int type, String msg)
	{
		this.type = type;
		this.msg = msg;
	}

	private int type;
	private String msg;

	public int getType()
	{
		return type;
	}

	public void setType(int type)
	{
		this.type = type;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

}
