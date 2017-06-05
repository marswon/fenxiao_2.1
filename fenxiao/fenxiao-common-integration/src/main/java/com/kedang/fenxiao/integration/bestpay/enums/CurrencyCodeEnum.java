package com.kedang.fenxiao.integration.bestpay.enums;

import com.kedang.fenxiao.integration.bestpay.enums.CurrencyCodeEnum;

/**
 * 币种编码枚举
 * @author Ocean
 *
 */
public enum CurrencyCodeEnum {
    RMB("RMB", "人民币"),
    USD("USD", "美元"),
    HKD("HKD", "港元");
    private final String code;
    private final String msg;

    CurrencyCodeEnum(String code, String msg)
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
    public static boolean isCurrencyCode(String code)
    {
        for (CurrencyCodeEnum s : CurrencyCodeEnum.values())
        {
            if (s.getCode().equals(code))
            {
                return true;
            }
        }
        return false;
    }
}
