package com.kedang.fenxiao.util.enums;

/**
 * 系统日志类型枚举类
 * @author Admin
 *
 */
public enum SystemLogType
{
	bdyhk((short)1, "绑定银行卡"),
	jbyhk((short)2, "解绑银行卡"),
	edtx((short)3, "额度提现"),
	qbtx((short)4, "钱包提现"),
	qehk((short)5, "全额还款"),
	fqhk((short)6, "分期还款");
	private SystemLogType(short type, String des) {
		this.type = type;
		this.des = des;
	}
	private short type;
	private String des;
	public short getType()
	{
		return type;
	}
	public void setType(short type)
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
