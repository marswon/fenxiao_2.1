package com.kedang.fenxiao.integration.bestpay.enums;

import com.kedang.fenxiao.integration.bestpay.enums.CertTypeEnum;

/**
 * 证件类型枚举
 * @author Ocean
 *
 */
public enum CertTypeEnum {
    SFZ("00", "身份证"),
    HZ("01", "护照"),
    JRZ("02", "军人证"),
    HKB("03", "户口簿"),
    WJZ("04", "武警证"),
    YYZZ("05", "法人营业执照"),
    GATXZ("06", "港澳通行证"),
    TWTXZ("07", "台湾通行证"),
    XSZ("08", "学生证"), 
    GZZ("09", "工作证"),
    GSZZ("10", "工商执照"),
    JGZ("11", "警官证"),
    SYDWBM("12", "事业单位编码"),
    FCZ("13", "房产证"),
    ZZJGBM("51", "组织机构编码"),
    QTZ("99", "其它证件");
    private final String code;
    private final String msg;

    CertTypeEnum(String code, String msg)
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
    public static boolean isCertType(String code)
    {
        for (CertTypeEnum s : CertTypeEnum.values())
        {
            if (s.getCode().equalsIgnoreCase(code))
            {
                return true;
            }
        }
        return false;
    }
}
