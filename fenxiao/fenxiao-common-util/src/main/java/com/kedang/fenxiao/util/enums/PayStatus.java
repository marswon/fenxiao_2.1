package com.kedang.fenxiao.util.enums;

/**
 * 支付状态
 * @author Administrator
 * @date 2015年11月20日
 */
public enum PayStatus
{
	csh(0, "初始化"), 
	cg(1, "成功"), 
	sb(2, "失败"), 
	clz(3, "处理中");
	private int type;
	private String des;

	private PayStatus(int type, String des)
	{
		this.type = type;
		this.des = des;
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
