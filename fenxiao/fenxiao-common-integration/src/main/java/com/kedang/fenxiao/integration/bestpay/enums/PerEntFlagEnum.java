package com.kedang.fenxiao.integration.bestpay.enums;

import com.kedang.fenxiao.integration.bestpay.enums.PerEntFlagEnum;

/**
 * 对公对私标识枚举
 * @author Ocean
 *
 */
public enum PerEntFlagEnum
{
	DG((short)0, "对公"), DS((short)1, "对私");
	private final short code;
	private final String msg;

	PerEntFlagEnum(short code, String msg)
	{
		this.code = code;
		this.msg = msg;
	}

	public short getCode()
	{
		return code;
	}

	public String getMsg()
	{
		return msg;
	}

	public static boolean isPerEntFlag(short code)
	{
		for (PerEntFlagEnum s : PerEntFlagEnum.values())
		{
			if (s.getCode() == code)
			{
				return true;
			}
		}
		return false;
	}
}
