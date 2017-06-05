package com.kedang.fenxiao.util.enums;

/**
 * 运营商类型
 */
public enum YysType {
	dx("1", "电信"),
	yd("2", "移动"),
	lt("3","联通");

	private YysType(String type, String des) {
		this.type = type;
		this.des = des;
	}

	private String type;
	private String des;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDes() {
		return des;
	}

	public void setDes(String des) {
		this.des = des;
	}

}
