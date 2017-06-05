package com.kedang.fenxiao.integration.bestpay.enums;

public enum ArpTypeEnum
{
	DS("01", "代收"), DF("02", "代付");
	private final String code;
	private final String msg;

	ArpTypeEnum(String code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public String getCode()
	{
		return code;
	}

	public String getMsg()
	{
		return msg;
	}

	public static boolean isArpType(String code)
	{
		for (ArpTypeEnum s : ArpTypeEnum.values())
		{
			if (s.getCode().equalsIgnoreCase(code))
			{
				return true;
			}
		}
		return false;
	}
}
