package com.kedang.fenxiao.util.enums;


/**
 * 密码类型
 */
public enum PasswordType {
	dl(1,"登录密码"),
	zf(2,"支付密码");
	private PasswordType(int type, String des) {
		this.type = type;
		this.des = des;
	}
	private int type;
	private String des;

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
