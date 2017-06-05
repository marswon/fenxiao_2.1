package com.kedang.fenxiao.util.enums;

public enum PayType
{
	bankPay(1, "银行卡支付"), 
	walletPay(2, "钱包支付");
	private int type;
	private String des;

	private PayType(int type, String des)
	{
		this.des = des;
		this.type = type;
	}

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
