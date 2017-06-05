package com.kedang.fenxiao.util.enums;

public enum FxFlowType {
	roam(0, "漫游"),
	local(1, "本地");
	private FxFlowType(int type, String des) {
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
