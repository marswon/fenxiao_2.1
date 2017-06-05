package com.kedang.fenxiao.integration.bestpay.enums;

import com.kedang.fenxiao.integration.bestpay.enums.CardTypeEnum;

/**
 * 银行卡卡类型枚举
 * @author Ocean
 *
 */
public enum CardTypeEnum
{
	JJK((short)1, "借记卡"), XYK((short)2, "信用卡"), CZ((short)4, "存折"), GSZH((short)8, "公司账户");
	private final short code;
	private final String msg;

	CardTypeEnum(short code, String msg)
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

	public static boolean isCardType(short code)
	{
		for (CardTypeEnum s : CardTypeEnum.values())
		{
			if (s.getCode() == code)
			{
				return true;
			}
		}
		return false;
	}
}
