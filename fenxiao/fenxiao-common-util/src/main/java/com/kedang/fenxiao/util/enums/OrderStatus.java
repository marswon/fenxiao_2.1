package com.kedang.fenxiao.util.enums;


/**
 * 订单状态
 */
public enum OrderStatus {
	xdd(0, "新订单"),
	cg(1, "成功"),
	sb(2, "失败");
	private OrderStatus(int type, String des) {
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
