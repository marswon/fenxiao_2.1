package com.kedang.fenxiao.util.enums;


/**
 * 订单类型
 */
public enum OrderType {
	xx(1, "线下消费"),
	df(2, "网购代付"),
	tx(3, "用户提现"),
	qe(4, "全额还款"),
	fq(5, "分期还款"),
	qbtx(6, "钱包提现"),
	ppjl(7, "拼拼奖励"),
	/**
	 * modify by ocean
	 * 用户学籍认证审核通过后:给推荐人和被推荐人(如果被推荐人也审核通过的话)都添加一笔推荐人奖励订单
	 * 增加订单类型  8:推荐人奖励
	 */
	tjrjl(8, "推荐人奖励"),
	zdyq(9,"账单延期");
	private OrderType(int type, String des) {
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
