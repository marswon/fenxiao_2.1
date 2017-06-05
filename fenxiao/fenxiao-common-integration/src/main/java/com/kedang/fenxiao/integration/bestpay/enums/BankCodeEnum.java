package com.kedang.fenxiao.integration.bestpay.enums;

public enum BankCodeEnum
{
	ZGRM("860000", "中国人民银行"), ZGYZ("866000", "中国邮政储蓄银行"), ZG("866100", "中国银行"), ZGGS("866200", " 中国工商银行"), ZGNY(
			"866300", "中国农业银行"), ZGJS("866500", "中国建设银行"), GF("866800", "广发银行"), JT("866400", "交通银行"), ZGMS("866600",
			"中国民生银行"), ZS("866900", "招商银行"), GZ("867000", "广州银行"), SHPF("867100", "上海浦发银行"), GD("867200", "光大银行"), ZX(
			"867400", "中信银行"), XY("867600", "兴业银行"), GZNS("866700", "广州农商"), BJ("865900", "北京银行"), DGNX("867319",
			"东莞农信"), HX("865800", "华夏银行"), SZFZ("865600", "深圳发展银行"), PA("865700", "平安银行"), DGNS("867800", "东莞农商"), DG(
			"867900", "东莞银行"), GDNX("867300", "广东农信");
	private final String code;
	private final String msg;

	BankCodeEnum(String code, String msg)
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

	public static boolean isBankCode(String code)
	{
		for (BankCodeEnum s : BankCodeEnum.values())
		{
			if (s.getCode().equalsIgnoreCase(code))
			{
				return true;
			}
		}
		return false;
	}
}
