package com.kedang.fenxiao.util.enums;

/**
 * 费用处理类型
 * @author gegongxian
 *
 */
public enum FeeType {
	ORDER_CONSUME(0, "订单消费"), 
	ORDER_CALLBACK(1,"订单退款"),
	ENTERPRISE_ADD_PRICE(2, "企业加款");

	private int type;
	private String msg;

	private FeeType(int type, String msg) {
		this.msg = msg;
		this.type = type;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

}
