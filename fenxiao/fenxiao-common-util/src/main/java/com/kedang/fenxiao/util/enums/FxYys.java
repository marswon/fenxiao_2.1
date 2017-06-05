package com.kedang.fenxiao.util.enums;

public enum FxYys {
	dx(1, "电信"),
	yd(2, "移动"),
	lt(3, "联通");

	private FxYys(int type, String des) {
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
