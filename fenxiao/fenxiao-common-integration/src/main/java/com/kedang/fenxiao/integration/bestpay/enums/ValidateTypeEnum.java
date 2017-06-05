package com.kedang.fenxiao.integration.bestpay.enums;

import com.kedang.fenxiao.integration.bestpay.enums.ValidateTypeEnum;

/**
 * 验证类型
 * @author Ocean
 *
 */
public enum ValidateTypeEnum
{
	WKF("01", "无扣费验证"), KYFQ("02", "扣一分钱验证"), W("03", "无验证");
	private final String code;
	private final String msg;

	ValidateTypeEnum(String code, String msg)
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

	public static boolean isValidateType(String code)
	{
		for (ValidateTypeEnum s : ValidateTypeEnum.values())
		{
			if (s.getCode().equalsIgnoreCase(code))
			{
				return true;
			}
		}
		return false;
	}
}
